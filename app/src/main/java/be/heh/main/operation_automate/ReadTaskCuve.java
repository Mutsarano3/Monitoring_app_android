package be.heh.main.operation_automate;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import be.heh.main.SimaticS7.S7;
import be.heh.main.SimaticS7.S7Client;
import be.heh.main.SimaticS7.S7OrderCode;

public class ReadTaskCuve {
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private View vi_main_ui;
    private TextView tv_main_plc;
    private TextView tv_valve1;
    private TextView tv_valve2;
    private TextView tv_valve3;
    private TextView tv_valve4;
    private TextView tv_q1;
    private TextView tv_q2;
    private TextView tv_q3;
    private TextView tv_q4;
    private AutomateS7 plcS7;
    private Thread readThread;
    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];
    private byte[] datasSortie = new byte[512];
    private byte[] datasAfficheurs = new byte[512];
    private TextView afficheurs;
    private int db;

    public ReadTaskCuve(String dblock,View v, TextView t,TextView valve1,TextView valv2, TextView valv3,TextView valv4,TextView q1, TextView q2,TextView q3, TextView q4,TextView afficheurs_p) {
        db = Integer.parseInt(dblock);
        vi_main_ui = v;
        tv_main_plc = t;
        tv_valve1 = valve1;
        tv_valve2 = valv2;
        tv_valve3 = valv3;
        tv_valve4 = valv4;
        afficheurs = afficheurs_p;
        tv_q1 = q1;
        tv_q2 = q2;
        tv_q3 = q3;
        tv_q4 = q4;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    public void Stop() {
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }

    public void Start(String a, String r, String s) {
        if (!readThread.isAlive()) {
            param[0] = a;
            param[1] = r;
            param[2] = s;
            readThread.start();
            isRunning.set(true);
        }
    }

    private void downloadOnPreExecute(int t) {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est démarré !" + "\n"
                , Toast.LENGTH_SHORT).show();

    }

    private void downloadOnProgressUpdate(int progress) {
    }

    private void downloadOnPostExecute() {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est terminé !"
                , Toast.LENGTH_LONG).show();

    }

    private Handler monHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PRE_EXECUTE:
                    downloadOnPreExecute(msg.arg1);
                    break;
                case MESSAGE_PROGRESS_UPDATE:
                    downloadOnProgressUpdate(msg.arg1);
                    break;
                case MESSAGE_POST_EXECUTE:
                    downloadOnPostExecute();
                    break;
                default:
                    break;
            }
        }
    };

    private class AutomateS7 implements Runnable {
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res =
                        comS7.ConnectTo(param[0], Integer.valueOf(param[1]), Integer.valueOf(param[2]));
                S7OrderCode orderCode = new S7OrderCode();
                Integer result = comS7.GetOrderCode(orderCode);
                int numCPU = -1;
                if (res.equals(0) && result.equals(0)) {
//Quelques exemples :
// WinAC : 6ES7 611-4SB00-0YB7
// S7-315 2DPP?N : 6ES7 315-4EH13-0AB0
// S7-1214C : 6ES7 214-1BG40-0XB0
// Récupérer le code CPU  611 OU 315 OU 214
                    numCPU = Integer.valueOf(orderCode.Code().toString().substring(5, 8));
                } else numCPU = 0000;
                sendPreExecuteMessage(numCPU);
                while (isRunning.get()) {
                    if (res.equals(0)) {
                        int retInfo = comS7.ReadArea(S7.S7AreaDB, 5, 0, 1, datasPLC);
                        int retSortie = comS7.ReadArea(S7.S7AreaDB, 5, 1, 1, datasSortie);
                        int retAfficheurs = comS7.ReadArea(S7.S7AreaDB, 5, 16, 2, datasAfficheurs);
                        int data = 0;
//int dataB=0;
                        if (retInfo == 0) {
                            boolean commutateur_manu_auto = S7.GetBitAt(datasPLC,0,5);
                            boolean commutateur_valve1 = S7.GetBitAt(datasPLC,0,1);
                            boolean commutateur_valve2 = S7.GetBitAt(datasPLC,0,2);
                            boolean commutateur_valve3 = S7.GetBitAt(datasPLC,0,3);
                            boolean commutateur_valve4 = S7.GetBitAt(datasPLC,0,4);
                            if(commutateur_valve4)
                            {
                                tv_valve4.setText("Commutateur valve4 : ON");
                                tv_valve4.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_valve4.setText("Commutateur valve4 : OFF");
                                tv_valve4.setTextColor(Color.RED);
                            }
                            if(commutateur_valve3)
                            {
                                tv_valve3.setText("Commutateur valve3 : ON");
                                tv_valve3.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_valve3.setText("Commutateur valve3 : OFF");
                                tv_valve3.setTextColor(Color.RED);
                            }
                            if(commutateur_valve2)
                            {
                                tv_valve2.setText("Commutateur valve2 : ON");
                                tv_valve2.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_valve2.setText("Commutateur valve2 : OFF");
                                tv_valve2.setTextColor(Color.RED);
                            }
                            if(commutateur_valve1)
                            {
                                tv_valve1.setText("Commutateur valve1 : ON");
                                tv_valve1.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_valve1.setText("Commutateur valve1 : OFF");
                                tv_valve1.setTextColor(Color.RED);
                            }

                            if(commutateur_manu_auto)
                            {
                                tv_main_plc.setText("Commutateur Manuelle/Auto : AUTO");
                                tv_main_plc.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_main_plc.setText("Commutateur Manuelle/Auto : MANUEL");
                                tv_main_plc.setTextColor(Color.RED);
                            }
                            sendProgressMessage(data);
                        }

                        if(retSortie == 0)
                        {
                            boolean Sortie1 = S7.GetBitAt(datasSortie,0,1);
                            boolean Sortie2 = S7.GetBitAt(datasSortie,0,2);
                            boolean Sortie3 = S7.GetBitAt(datasSortie,0,3);
                            boolean Sortie4 = S7.GetBitAt(datasSortie,0,4);

                            if(Sortie1)
                            {
                                tv_q1.setText("Piston1 : ON");
                                tv_q1.setTextColor(Color.GREEN);
                            } else{
                                tv_q1.setText("Piston1 : OFF");
                                tv_q1.setTextColor(Color.RED);
                            }

                            if(Sortie2)
                            {
                                tv_q2.setText("Piston2 : ON");
                                tv_q2.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_q2.setText("Piston2 : OFF");
                                tv_q2.setTextColor(Color.RED);
                            }

                            if(Sortie3)
                            {
                                tv_q3.setText("Piston 3 : ON");
                                tv_q3.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_q3.setText("Piston 3 : OFF");
                                tv_q3.setTextColor(Color.RED);
                            }

                            if(Sortie4)
                            {
                                tv_q4.setText("Piston 4 : ON");
                                tv_q4.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_q4.setText("Piston 4 : OFF");
                                tv_q4.setTextColor(Color.RED);
                            }



                        }

                        if(retAfficheurs == 0)
                        {
                            afficheurs.setText("Niveau de l'eau : "+S7.GetWordAt(datasAfficheurs,0));

                        }
                        Log.i("Variable A.P.I. -> ", String.valueOf(data));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendPostExecuteMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void sendPostExecuteMessage() {
        Message postExecuteMsg = new Message();
        postExecuteMsg.what = MESSAGE_POST_EXECUTE;
        monHandler.sendMessage(postExecuteMsg);
    }
    private void sendPreExecuteMessage(int v) {
        Message preExecuteMsg = new Message();
        preExecuteMsg.what = MESSAGE_PRE_EXECUTE;
        preExecuteMsg.arg1 = v;
        monHandler.sendMessage(preExecuteMsg);
    }
    private void sendProgressMessage(int i) {
        Message progressMsg = new Message();
        progressMsg.what = MESSAGE_PROGRESS_UPDATE;
        progressMsg.arg1 = i;
        monHandler.sendMessage(progressMsg);
    }
}

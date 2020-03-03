package be.heh.main.operation_automate;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import be.heh.main.SimaticS7.S7;
import be.heh.main.SimaticS7.S7Client;
import be.heh.main.SimaticS7.S7OrderCode;

public class ReadTaskMISMES {

    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private View vi_main_ui;
    private TextView tv_main_plc;
    private TextView tv_comprime5;
    private TextView tv_comprime10;
    private TextView tv_comprime15;
    private  TextView tv_sous_poste;
    private TextView tv_moteur_poste;
    private TextView tv_afficheurs_produite;
    private AutomateS7 plcS7;
    private Thread readThread;
    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];
    private  byte[] dataPLCSortie = new byte[512];
    private  byte[] dataPLCSAfficheurs = new byte[512];
    private int datablock;

    public ReadTaskMISMES(String db,View v, TextView t,TextView comp5,TextView comp10,TextView comp15,TextView tv_poste,TextView moteur_bande,TextView tv_bout_produite) {
        datablock = Integer.parseInt(db);
        vi_main_ui = v;
        tv_afficheurs_produite = tv_bout_produite;
        tv_moteur_poste = moteur_bande;
        tv_sous_poste = tv_poste;
        tv_main_plc = t;
        tv_comprime5 = comp5;
        tv_comprime10 = comp10;
        tv_comprime15 = comp15;
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
                        int retInfo = comS7.ReadArea(S7.S7AreaDB, datablock, 0, 1, datasPLC);
                        int retInfosSortie = comS7.ReadArea(S7.S7AreaDB, datablock, 4, 1, dataPLCSortie);
                        int retInfosAfficheurs = comS7.ReadArea(S7.S7AreaDB, datablock, 16, 2, dataPLCSAfficheurs);
                        int data = 0;
//int dataB=0;
                        if (retInfo == 0) {
                            boolean commutateur_service = S7.GetBitAt(datasPLC,0,0);
                            boolean bouteille_sous_poste = S7.GetBitAt(datasPLC,0,5);

                            if(commutateur_service)
                            {
                              tv_main_plc.setText("Commutateur de mise en service : ON");
                              tv_main_plc.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_main_plc.setText("Commutateur de mise en service : OFF");
                                tv_main_plc.setTextColor(Color.RED);
                            }
                            if(bouteille_sous_poste)
                            {
                                tv_sous_poste.setText("Presence fiole sous poste : ON");
                                tv_sous_poste.setTextColor(Color.GREEN);
                            }
                            else{
                                tv_sous_poste.setText("Presence fiole sous poste : OFF");
                                tv_sous_poste.setTextColor(Color.RED);
                            }



                            sendProgressMessage(data);
                        }
                        if(retInfosSortie == 0)
                        {
                            boolean moteur = S7.GetBitAt(dataPLCSortie,0,1);
                            boolean bp5 = S7.GetBitAt(dataPLCSortie,0,3);
                            boolean bp10 = S7.GetBitAt(dataPLCSortie,0,4);
                            boolean bp15 = S7.GetBitAt(dataPLCSortie,0,5);
                            if(bp5)
                            {
                                tv_comprime5.setTextColor(Color.GREEN);
                                tv_comprime5.setText("Demande de 5 comprimès : ON");
                            }
                            else{
                                tv_comprime5.setTextColor(Color.RED);
                                tv_comprime5.setText("Demande de 5 comprimès : OFF");
                            }
                            if(bp10)
                            {
                                tv_comprime10.setTextColor(Color.GREEN);
                                tv_comprime10.setText("Demande de 10 comprimès : ON");
                            }
                            else{
                                tv_comprime10.setTextColor(Color.RED);
                                tv_comprime10.setText("Demande de 10 comprimès : OFF");
                            }
                            if(bp15)
                            {
                                tv_comprime15.setTextColor(Color.GREEN);
                                tv_comprime15.setText("Demande de 15 comprimès : ON");
                            }
                            else{
                                tv_comprime15.setTextColor(Color.RED);
                                tv_comprime15.setText("Demande de 15 comprimès : OFF");
                            }

                            if(moteur)
                            {
                                tv_moteur_poste.setTextColor(Color.GREEN);
                                tv_moteur_poste.setText("Etat du moteur : ON");
                            }
                            else{
                                tv_moteur_poste.setTextColor(Color.RED);
                                tv_moteur_poste.setText("Etat du moteur : OFF");
                            }


                        }
                        if(retInfosAfficheurs == 0)
                        {

                            tv_afficheurs_produite.setText("Bouteilles remplies : "+S7.GetWordAt(dataPLCSAfficheurs,0));

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



package be.heh.main.operation_automate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import be.heh.main.R;
import be.heh.main.SimaticS7.IntByRef;
import be.heh.main.SimaticS7.S7;
import be.heh.main.SimaticS7.S7Client;
import be.heh.main.SimaticS7.S7CpuInfo;
import be.heh.main.SimaticS7.S7OrderCode;
import be.heh.main.info_automate.InfoAutomate;

public class ReadTaskS7 {

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Button bt_main_ConnexS7;
    private View vi_main_ui;
    private TextView tv_main_plc;
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private AutomateS7 plcS7;
    private Thread readThread;
    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];
    private TextView moduletypename;
    private TextView modulename;
    private TextView state;
    private int db;
    String order;

    public ReadTaskS7(View v, Button b, TextView t,String databloc){
        vi_main_ui = v;

        bt_main_ConnexS7 = b;
        tv_main_plc = t;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
        db = Integer.parseInt(databloc);
    }

    public ReadTaskS7(View v,TextView t,TextView module,TextView module_name,TextView sta, String databloc)
    {
        state = sta;
        modulename = module_name;
        vi_main_ui = v;
        tv_main_plc = t;
        moduletypename = module;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
        db = Integer.parseInt(databloc);
    }


    public void Stop(){
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }
    public void Start(String a, String r, String s){
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
        //tv_main_plc.setText("Numéro de référence de CPU : " + String.valueOf(t));
    }
    private void downloadOnProgressUpdate(int progress) {

    }
    private void downloadOnPostExecute() {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est terminé !"
                ,Toast.LENGTH_LONG).show();
        //tv_main_plc.setText("PLC : /!\\");
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

    private class AutomateS7  implements  Runnable  {


        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(param[0],Integer.valueOf(param[1]),Integer.valueOf(param[2]));
                S7OrderCode orderCode = new S7OrderCode();
                S7CpuInfo cpuInfo = new S7CpuInfo();
                Integer result1 = comS7.GetCpuInfo(cpuInfo);
                moduletypename.setText("Nom du type de module: "+cpuInfo.ModuleTypeName());
                modulename.setText("Nom du module : "+cpuInfo.ModuleName());
                IntByRef intByRef = new IntByRef();
                Integer result2 = comS7.GetPlcStatus(intByRef);

                if(intByRef.Value == 8)
                {
                    state.setText("Etat : RUN");
                    state.setBackgroundColor(Color.GREEN);
                    state.setTextColor(Color.WHITE);
                }
                else if(intByRef.Value == 4)
                {
                    state.setText("Etat : STOP");
                    state.setBackgroundColor(Color.RED);
                    state.setTextColor(Color.WHITE);
                }
                else{
                    state.setText("Etat : Unknown");
                    state.setBackgroundColor(Color.BLACK);
                    state.setTextColor(Color.WHITE);
                }
                Integer result = comS7.GetOrderCode(orderCode);
                tv_main_plc.setText("Numéro de référence : "+orderCode.Code().toString());
                int numCPU=-1;
                if (res.equals(0) && result.equals(0)){
//Quelques exemples :
// WinAC : 6ES7 611-4SB00-0YB7
// S7-315 2DPP?N : 6ES7 315-4EH13-0AB0
// S7-1214C : 6ES7 214-1BG40-0XB0
// Récupérer le code CPU  611 OU 315 OU 214
                    numCPU = Integer.valueOf(orderCode.Code().toString().substring(5, 8));
                }
                else numCPU=0000;
                sendPreExecuteMessage(numCPU);
                while(isRunning.get()){
                    if (res.equals(0)){
                        int retInfo = comS7.ReadArea(S7.S7AreaDB,db,9,2,datasPLC);
                        int data=0;
//int dataB=0;
                        if (retInfo ==0) {
                            data = S7.GetWordAt(datasPLC, 0);
                            sendProgressMessage(data);
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
            }
            catch (Exception e){
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

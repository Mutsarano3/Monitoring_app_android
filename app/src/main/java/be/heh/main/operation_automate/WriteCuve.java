package be.heh.main.operation_automate;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import be.heh.main.SimaticS7.S7;
import be.heh.main.SimaticS7.S7Client;

public class WriteCuve {

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread writeThread;
    private WriteCuve.AutomateS7 plcS7;
    private S7Client comS7;
    private String[] parConnexion = new String[10];
    private byte[] motCommande = new byte[10];
    private byte[] motbyte2 = new byte[10];
    private byte[] afficheurs1 = new byte[10];
    private byte[] afficheurs2 = new byte[10];
    private byte[] afficheurs3 = new byte[10];
    private byte[] afficheurs4 = new byte[10];
    int var1;
    int var2;
    int var3;
    int var4;
    int var5;
    int var6;
    int db;
    public WriteCuve(String var1_s, String var3_s,String var2_s,String var4_s,String var5_s,String var_b2,String db_s){
        var1 = Integer.parseInt(var1_s);
        var2 = Integer.parseInt(var3_s);
        var3 = Integer.parseInt(var2_s);
        var4 = Integer.parseInt(var4_s);
        var5 = Integer.parseInt(var5_s);
        var6= Integer.parseInt(var_b2);
        db = Integer.parseInt(db_s);
        comS7 = new S7Client();
        plcS7 = new WriteCuve.AutomateS7();
        writeThread = new Thread(plcS7);
    }

    public void Stop(){
        isRunning.set(false);
        comS7.Disconnect();
        writeThread.interrupt();
    }

    public void Start(String a, String r, String s){
        if (!writeThread.isAlive()) {
            parConnexion[0] = a;
            parConnexion[1] = r;
            parConnexion[2] = s;
            writeThread.start();
            isRunning.set(true);
        }
    }

    private class AutomateS7 implements Runnable{
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(parConnexion[0],
                        Integer.valueOf(parConnexion[1]),Integer.valueOf(parConnexion[2]));
                while(isRunning.get() && (res.equals(0))){
                    Integer writePLC = comS7.WriteArea(S7.S7AreaDB,db,var1,1,motCommande);
                    Integer writePLC6 = comS7.WriteArea(S7.S7AreaDB,db,var6,1,motbyte2);
                    Integer writePLC1 = comS7.WriteArea(S7.S7AreaDB,db,var2,2,afficheurs1);
                    Integer writePLC2 = comS7.WriteArea(S7.S7AreaDB,db,var3,2,afficheurs2);
                    Integer writePLC3 = comS7.WriteArea(S7.S7AreaDB,db,var4,2,afficheurs3);
                    Integer writePLC4 = comS7.WriteArea(S7.S7AreaDB,db,var5,2,afficheurs4);

                    if(writePLC.equals(0)) {
                        Log.i("ret WRITE : ", String.valueOf(res) + "****" + String.valueOf(writePLC));
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setWriteBool(int b, int v){
        //Masquage
        if(v==1) motCommande[0] = (byte)(b | motCommande[0]);
        else motCommande[0] = (byte)(~b & motCommande[0]);
    }
    public void setWriteBool2(int b, int v){
        //Masquage
        if(v==1) motbyte2[0] = (byte)(b | motbyte2[0]);
        else motbyte2[0] = (byte)(~b & motbyte2[0]);
    }

    public void setWriteInt1(int num)
    {
        S7.SetWordAt(afficheurs1,0,num);
    }
    public void setWriteInt2(int num)
    {
        S7.SetWordAt(afficheurs2,0,num);
    }
    public void setWriteInt3(int num)
    {
        S7.SetWordAt(afficheurs3,0,num);
    }

    public void setWriteInt4(int num)
    {
        S7.SetWordAt(afficheurs4,0,num);
    }
}

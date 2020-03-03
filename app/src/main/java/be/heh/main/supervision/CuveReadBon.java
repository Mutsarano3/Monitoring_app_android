package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.heh.main.R;
import be.heh.main.operation_automate.ReadTaskCuve;

public class CuveReadBon extends AppCompatActivity {
    private Bundle extrat;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private LinearLayout l;
    private ReadTaskCuve readtaskS7;
    private TextView commu_manu_auto;
    private TextView v1;
    private  TextView v2;
    private  TextView v3;
    private TextView v4;
    private TextView q1;
    private TextView q2;
    private  TextView q3;
    private  TextView q4;
    private TextView afficheurs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve_read_bon);
        extrat = this.getIntent().getExtras();
        connexStatus=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        commu_manu_auto = (TextView) findViewById(R.id.commutateur_manu_auto);
        network = connexStatus.getActiveNetworkInfo();
        l = (LinearLayout) findViewById(R.id.Cuve_bon_read);
        v1 = (TextView) findViewById(R.id.valve1);
        v2 = (TextView) findViewById(R.id.valve2);
        v3 = (TextView) findViewById(R.id.valve3);
        v4 = (TextView) findViewById(R.id.valve4);
        q1 = (TextView) findViewById(R.id.Q1);
        q2 = (TextView) findViewById(R.id.Q2);
        q3 = (TextView) findViewById(R.id.Q3);
        q4 = (TextView) findViewById(R.id.Q4);
        afficheurs = (TextView) findViewById(R.id.afficheurs_niveau);
        readtaskS7 = new ReadTaskCuve(extrat.getString("db"),l,commu_manu_auto,v1,v2,v3,v4,q1,q2,q3,q4,afficheurs);
        readtaskS7.Start(extrat.getString("ip"),extrat.getString("rack"),extrat.getString("slot"));


    }
}

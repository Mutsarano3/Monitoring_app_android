package be.heh.main.info_automate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.operation_automate.ReadTaskS7;

public class InfoAutomate extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_state;
    private TextView txt_cpu;
    private TextView txt_rack;
    private TextView txt_slot;
    private TextView txt_ip;
    private TextView txt_module_type_name;
    private TextView txt_module_name;
    private ReadTaskS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private Bundle extrat;
    private LinearLayout l;
    private TextView txt_db;
    private Button btn_serveur_web;
    private Button btn_signal_analogique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_automate);
        btn_signal_analogique = (Button) findViewById(R.id.signal_analogique);
        btn_signal_analogique.setOnClickListener(this);
        btn_serveur_web = (Button) findViewById(R.id.serveur_web);
        btn_serveur_web.setOnClickListener(this);
        l = (LinearLayout) findViewById(R.id.view_info);
        extrat = this.getIntent().getExtras();
        txt_state = (TextView) findViewById(R.id.txt_state);
        txt_db = (TextView) findViewById(R.id.txt_db);
        txt_ip = (TextView) findViewById(R.id.txt_ip_address);
        txt_rack = (TextView) findViewById(R.id.txt_Rack);
        txt_slot = (TextView) findViewById(R.id.txt_slot);
        txt_cpu = (TextView) findViewById(R.id.txt_info_cpu);
        txt_module_type_name = (TextView) findViewById(R.id.txt_module_type_name);
        txt_module_name = (TextView) findViewById(R.id.txt_name_module);
        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        txt_db.setText("DB : "+ extrat.getString("db"));
        txt_ip.setText("IP adresse : "+extrat.getString("ip"));
        txt_rack.setText("Rack : "+extrat.getString("rack"));
        txt_slot.setText("Slot : "+extrat.getString("slot"));
        readS7 = new ReadTaskS7(l, txt_cpu,txt_module_type_name,txt_module_name,txt_state, extrat.getString("db"));
        readS7.Start(extrat.getString("ip"), extrat.getString("rack"), extrat.getString("slot"));
        network = connexStatus.getActiveNetworkInfo();
        Toast.makeText(this, network.getTypeName(), Toast.LENGTH_SHORT).show();

        if (network != null && network.isConnectedOrConnecting()) {

            //readS7.Stop();
        }

    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.serveur_web:
                String url = "http://"+extrat.getString("ip");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.signal_analogique:
                break;
        }

    }
}

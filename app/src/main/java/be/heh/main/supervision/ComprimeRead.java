package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.operation_automate.ReadTaskMISMES;

public class ComprimeRead extends AppCompatActivity {

    private TextView commutateur_service;
    private TextView bp5;
    private  TextView bp10;
    private TextView bp15;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private LinearLayout l;
    private ReadTaskMISMES readTask7;
    private TextView presence_tube;
    private Bundle extrat;
    private  TextView moteur_bande;
    private TextView afficheurs_bout_produite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprime_read);
        afficheurs_bout_produite = (TextView) findViewById(R.id.affichage_2);
        moteur_bande = (TextView) findViewById(R.id.moteur_bande);
        presence_tube = (TextView) findViewById(R.id.presence_tube_remplissage);
        extrat = this.getIntent().getExtras();
       connexStatus=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
        l = (LinearLayout) findViewById(R.id.linecuveread);
        commutateur_service = (TextView) findViewById(R.id.commutateur_service);
        bp5 = (TextView) findViewById(R.id.demande_5_comprimés);
        bp10 = (TextView) findViewById(R.id.demande_10_comprimés);
        bp15 = (TextView) findViewById(R.id.demande_15_comprimés);
        if(network != null && network.isConnectedOrConnecting())
        {
            Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
            readTask7 = new ReadTaskMISMES(extrat.getString("db"),l,commutateur_service,bp5,bp10,bp15,presence_tube,moteur_bande,afficheurs_bout_produite);
            readTask7.Start(extrat.getString("ip"),extrat.getString("rack"),extrat.getString("slot"));
        }
    }
}

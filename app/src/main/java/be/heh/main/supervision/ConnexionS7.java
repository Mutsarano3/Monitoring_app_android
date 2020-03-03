package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.info_automate.InfoAutomate;
import be.heh.main.operation_automate.ReadTaskS7;

public class ConnexionS7 extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_ref;
    private Button btn_connexion;
    private Button back_conf;
    private Button info_automate;
    private Button comprime;
    private Button cuve;
    private Bundle extrat;
    private ReadTaskS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private Button comprime_w;
    private Button cuve_w;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_s7);
        extrat = this.getIntent().getExtras();
        btn_connexion = (Button) findViewById(R.id.connexion_automate);
        info_automate = (Button) findViewById(R.id.info_automate);
        comprime = (Button) findViewById(R.id.Comprime);
        cuve = (Button) findViewById(R.id.Cuve);
        this.btn_connexion.setOnClickListener(this);
        this.info_automate.setOnClickListener(this);
        this.comprime.setOnClickListener(this);
        this.cuve.setOnClickListener(this);
        comprime_w = (Button) findViewById(R.id.Comprime_w);
        cuve_w = (Button) findViewById(R.id.Cuve_w);
        comprime_w.setOnClickListener(this);
        cuve_w.setOnClickListener(this);
        comprime_w.setVisibility(View.GONE);
        cuve_w.setVisibility(View.GONE);
        connexStatus = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {


            case R.id.connexion_automate:
                System.out.println(extrat.getString("ip"));

                if(network != null && network.isConnectedOrConnecting())
                {
                    if (btn_connexion.getText().equals("Connexion")){
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        btn_connexion.setText("Déconnexion");
                        info_automate.setVisibility(View.VISIBLE);
                        cuve.setVisibility(View.VISIBLE);
                        comprime.setVisibility(View.VISIBLE);
                        if(extrat.getString("role").equals("USER"))
                        {
                            comprime_w.setVisibility(View.GONE);
                            cuve_w.setVisibility(View.GONE);

                        }

                        if(extrat.getString("role").equals("SuperAdmin"))
                        {
                            cuve_w.setVisibility(View.VISIBLE);
                            comprime_w.setVisibility(View.VISIBLE);

                        }

                        if(extrat.getString("role").equals("ADMIN"))
                        {
                            cuve_w.setVisibility(View.VISIBLE);
                            comprime_w.setVisibility(View.VISIBLE);
                        }



                    }
                    else{

                        btn_connexion.setText("Connexion");
                        info_automate.setVisibility(View.GONE);
                        cuve.setVisibility(View.GONE);
                        comprime.setVisibility(View.GONE);
                        comprime_w.setVisibility(View.GONE);
                        cuve_w.setVisibility(View.GONE);

                        Toast.makeText(getApplication(), "Traitement interrompu par l'utilisateur !!! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    System.out.println("ok");
                    Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.info_automate:
                Intent intent = new Intent(this, InfoAutomate.class);
                intent.putExtra("ip",extrat.getString("ip"));
                intent.putExtra("rack",extrat.getString("rack"));
                intent.putExtra("slot",extrat.getString("slot"));
                intent.putExtra("db",extrat.getString("db"));
                startActivity(intent);

                break;

            case R.id.Comprime:

                    Intent intent2 = new Intent(this, ComprimeRead.class);
                    intent2.putExtra("ip",extrat.getString("ip"));
                    intent2.putExtra("rack",extrat.getString("rack"));
                    intent2.putExtra("slot",extrat.getString("slot"));
                    intent2.putExtra("db",extrat.getString("db"));
                    startActivity(intent2);


                break;
            case R.id.Cuve:
                Toast.makeText(this,"Appuyer" + extrat.getString("role"),Toast.LENGTH_SHORT).show();

                    Intent intent3 = new Intent(this,CuveReadBon.class);
                    intent3.putExtra("ip",extrat.getString("ip"));
                    intent3.putExtra("rack",extrat.getString("rack"));
                    intent3.putExtra("slot",extrat.getString("slot"));
                    intent3.putExtra("db",extrat.getString("db"));
                    startActivity(intent3);
                break;

            case R.id.Comprime_w:
                Intent intent6 = new Intent(this,ConfigWriteComprime.class);
                intent6.putExtra("ip",extrat.getString("ip"));
                intent6.putExtra("rack",extrat.getString("rack"));
                intent6.putExtra("slot",extrat.getString("slot"));
                intent6.putExtra("db",extrat.getString("db"));
                startActivity(intent6);
                break;

            case R.id.Cuve_w:
                Intent intent7 = new Intent(this,ConfigWriteCuve.class);
                intent7.putExtra("ip",extrat.getString("ip"));
                intent7.putExtra("rack",extrat.getString("rack"));
                intent7.putExtra("slot",extrat.getString("slot"));
                intent7.putExtra("db",extrat.getString("db"));
                startActivity(intent7);
                break;
        }

    }
}

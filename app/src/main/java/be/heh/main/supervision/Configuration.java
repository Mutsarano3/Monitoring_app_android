package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;

public class Configuration extends AppCompatActivity implements View.OnClickListener {

    private EditText addresse_ip;
    private EditText rack;
    private EditText slot;
    private Button btn_valide;
    private TextView txt_error;
    private EditText db;
    private Bundle extrat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        extrat = this.getIntent().getExtras();
        db = (EditText) findViewById(R.id.db);
        addresse_ip = (EditText) findViewById(R.id.adresse_ip);
        rack = (EditText) findViewById(R.id.rack);
        slot = (EditText) findViewById(R.id.slot);
        btn_valide =  (Button) findViewById(R.id.config);
        txt_error = (TextView) findViewById(R.id.error_config);
        btn_valide.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.config:
                if(addresse_ip.getText().toString().length() ==0 || rack.getText().toString().length() == 0 || slot.getText().toString().length() == 0 || db.getText().toString().length() == 0)
                {
                    txt_error.setVisibility(View.VISIBLE);
                }
                else {
                    txt_error.setVisibility(View.GONE);
                    Toast.makeText(this,extrat.getString("role"),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,ConnexionS7.class);
                    intent.putExtra("ip",addresse_ip.getText().toString());
                    intent.putExtra("rack",rack.getText().toString());
                    intent.putExtra("slot",slot.getText().toString());
                    intent.putExtra("db",db.getText().toString());
                    intent.putExtra("role",extrat.getString("role"));
                    startActivity(intent);
                    finish();

                }

                break;
        }

    }
}

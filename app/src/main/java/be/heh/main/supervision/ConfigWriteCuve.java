package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.heh.main.R;

public class ConfigWriteCuve extends AppCompatActivity implements View.OnClickListener {

    private Button btn_confirmer;
    private Bundle extrat;
    private EditText var1;
    private EditText var1bol;
    private EditText var2;
    private EditText var3;
    private EditText var4;
    private EditText var5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_write_cuve);
        extrat = this.getIntent().getExtras();
        var1 = (EditText) findViewById(R.id.var1_write_cuve);
        var2 = (EditText) findViewById(R.id.var2_write_cuve);
        var3 = (EditText) findViewById(R.id.var3_write_cuve);
        var4 = (EditText) findViewById(R.id.var4_write_cuve);
        var5 = (EditText) findViewById(R.id.var5_write_cuve);
        var1bol  = (EditText) findViewById(R.id.var1bol_write_cuve);
        btn_confirmer = (Button) findViewById(R.id.confirm_cuve);
        this.btn_confirmer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.confirm_cuve:
                if(var1.getText().toString().length() == 0 || var2.getText().toString().length() == 0 || var3.getText().toString().length() == 0 || var4.getText().toString().length() == 0 || var5.getText().toString().length() == 0 || var1bol.getText().toString().length() == 0 )
                {
                    Toast.makeText(this,"Erreur un champ est vide",Toast.LENGTH_SHORT).show();
                }

                else{

                    Intent intent = new Intent(this,CuveWrite.class);
                    intent.putExtra("ip",extrat.getString("ip"));
                    intent.putExtra("rack",extrat.getString("rack"));
                    intent.putExtra("slot",extrat.getString("slot"));
                    intent.putExtra("db",extrat.getString("db"));
                    intent.putExtra("var1",var1.getText().toString());
                    intent.putExtra("var2",var2.getText().toString());
                    intent.putExtra("var3",var3.getText().toString());
                    intent.putExtra("var4",var4.getText().toString());
                    intent.putExtra("var5",var5.getText().toString());
                    intent.putExtra("var6",var1bol.getText().toString());
                    startActivity(intent);

                }
                break;
        }


    }
}

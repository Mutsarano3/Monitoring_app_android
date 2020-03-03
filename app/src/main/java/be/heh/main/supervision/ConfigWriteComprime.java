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

public class ConfigWriteComprime extends AppCompatActivity implements View.OnClickListener {
    private Bundle extrat;
    private EditText var1;
    private EditText var2;
    private EditText var3;
    private Button btn_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_write_comprime);
        extrat = this.getIntent().getExtras();
        var1 = (EditText)findViewById(R.id.var1);
        var3 = (EditText) findViewById(R.id.var3);

        btn_var = (Button) findViewById(R.id.confirm_var_comprime);
        btn_var.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.confirm_var_comprime:
                if(this.var1.getText().toString().length() != 0 &&  this.var3.getText().toString().length() != 0)
                {

                    Intent intent = new Intent(this,ComprimeWrite.class);
                    intent.putExtra("var1",this.var1.getText().toString());
                    intent.putExtra("var3",this.var3.getText().toString());
                    intent.putExtra("ip",extrat.getString("ip"));
                    intent.putExtra("rack",extrat.getString("rack"));
                    intent.putExtra("slot",extrat.getString("slot"));
                    intent.putExtra("db",extrat.getString("db"));
                    startActivity(intent);

                }
                else{

                    Toast.makeText(this,"Errreur un des champs est vide",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}

package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import be.heh.main.R;
import be.heh.main.operation_automate.WriteComprime;

public class ComprimeWrite extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Switch commutateur;
    private CheckBox check1;
    private CheckBox check2;
    private CheckBox check3;
    private  CheckBox check4;
    private  CheckBox check5;
    private  CheckBox check6;
    private  CheckBox check7;
    private EditText et_afficheurs;

    private Bundle extrat;
    private WriteComprime writeComprime;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprime_write);
        extrat = this.getIntent().getExtras();
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeComprime = new WriteComprime(extrat.getString("var1"),extrat.getString("var3"),extrat.getString("db"));
        writeComprime.Start(extrat.getString("ip"), extrat.getString("rack"), extrat.getString("slot"));

        commutateur = (Switch) findViewById(R.id.commutateur_service_w);
        check1 = (CheckBox) findViewById(R.id.btn_5);
        check2 = (CheckBox) findViewById(R.id.btn_10);
        check3 = (CheckBox) findViewById(R.id.btn_15);
        check4 = (CheckBox) findViewById(R.id.btn_30);
        check5 = (CheckBox) findViewById(R.id.btn_35);
        check6 = (CheckBox) findViewById(R.id.btn_40);
        check7 = (CheckBox) findViewById(R.id.btn_45);

        commutateur.setOnClickListener(this);
        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
        check3.setOnClickListener(this);
        check4.setOnClickListener(this);
        check5.setOnClickListener(this);
        check6.setOnClickListener(this);
        check7.setOnClickListener(this);
        et_afficheurs = (EditText) findViewById(R.id.et_afficheurs);
        et_afficheurs.addTextChangedListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.commutateur_service_w:
                a++;
                if(a % 2 == 0)
                {
                    writeComprime.setWriteBool(1, 0);
                }
                else {
                    writeComprime.setWriteBool(1, 1);

                }
                break;
            case R.id.btn_5:
                writeComprime.setWriteBool(2, check1.isChecked() ? 1 : 0);
                break;
            case R.id.btn_10:
                writeComprime.setWriteBool(4, check2.isChecked() ? 1 : 0);
                break;

            case R.id.btn_15:
                writeComprime.setWriteBool(8, check3.isChecked() ? 1 : 0);
            break;
            case R.id.btn_30:
                writeComprime.setWriteBool(16, check4.isChecked() ? 1 : 0);
                break;
            case R.id.btn_35:
                writeComprime.setWriteBool(32, check5.isChecked() ? 1 : 0);
                break;
            case R.id.btn_40:
                writeComprime.setWriteBool(64, check6.isChecked() ? 1 : 0);
                break;
            case R.id.btn_45:
                writeComprime.setWriteBool(128, check7.isChecked() ? 1 : 0);
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(et_afficheurs.getText().toString().equals(""))
        {
            writeComprime.setWriteInt(0);
        }
        else{
            writeComprime.setWriteInt(Integer.parseInt(et_afficheurs.getText().toString()));
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

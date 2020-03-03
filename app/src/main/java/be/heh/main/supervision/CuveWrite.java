package be.heh.main.supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import be.heh.main.R;
import be.heh.main.operation_automate.WriteCuve;

public class CuveWrite extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText var1;
    private EditText var2;
    private EditText var3;
    private EditText var4;
    private Switch i1;
    private Switch i2;
    private Switch i3;
    private Switch i4;
    private Switch i5;
    private Switch i6;
    private Switch i7;
    private Switch i8;
    private Switch y1;
    private Switch y2;
    private Switch y3;
    private Switch y4;
    private Switch y5;
    private Switch y6;
    private Switch y7;
    private Switch y8;
    private Bundle extrat;
    int a1;
    int a2;
    int a3;
    int a4;
    int a5;
    int a6;
    int a7;
    int a8;
    int b1;
    int b2;
    int b3;
    int b4;
    int b5;
    int b6;
    int b7;
    int b8;

    private WriteCuve writeCuve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve_write);
        extrat = this.getIntent().getExtras();
        i1 = (Switch) findViewById(R.id.var1_switch_cuve);
        i2 = (Switch) findViewById(R.id.var2_switch_cuve);
        i3 = (Switch) findViewById(R.id.var3_switch_cuve);
        i4 = (Switch) findViewById(R.id.var4_switch_cuve);
        i5 = (Switch) findViewById(R.id.var5_switch_cuve);
        i6 = (Switch) findViewById(R.id.var6_switch_cuve);
        i7 = (Switch) findViewById(R.id.var7_switch_cuve);
        i8 = (Switch) findViewById(R.id.var8_switch_cuve);
        //***********************************************
        y1 = (Switch) findViewById(R.id.var20_switch_cuve);
        y2 = (Switch) findViewById(R.id.var21_switch_cuve);
        y3 = (Switch) findViewById(R.id.var23_switch_cuve);
        y4 = (Switch) findViewById(R.id.var24_switch_cuve);
        y5 = (Switch) findViewById(R.id.var25_switch_cuve);
        y6 = (Switch) findViewById(R.id.var26_switch_cuve);
        y7 = (Switch) findViewById(R.id.var27_switch_cuve);
        y8 = (Switch) findViewById(R.id.var28_switch_cuve);

        var1 = (EditText) findViewById(R.id.var_cuve_1);
        var2 = (EditText) findViewById(R.id.var_cuve_2);
        var3 = (EditText) findViewById(R.id.var_cuve_3);
        var4 = (EditText) findViewById(R.id.var_cuve_4);
        var1.addTextChangedListener(this);
        var2.addTextChangedListener(this);
        var3.addTextChangedListener(this);
        var4.addTextChangedListener(this);
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        i5.setOnClickListener(this);
        i6.setOnClickListener(this);
        i7.setOnClickListener(this);
        i8.setOnClickListener(this);
        y1.setOnClickListener(this);
        y2.setOnClickListener(this);
        y3.setOnClickListener(this);
        y4.setOnClickListener(this);
        y5.setOnClickListener(this);
        y6.setOnClickListener(this);
        y7.setOnClickListener(this);
        y8.setOnClickListener(this);
        writeCuve = new WriteCuve(extrat.getString("var1"),extrat.getString("var2"),extrat.getString("var3"),extrat.getString("var4"),extrat.getString("var5"),extrat.getString("var6"),extrat.getString("db"));
        writeCuve.Start(extrat.getString("ip"), extrat.getString("rack"), extrat.getString("slot"));

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    if(var1.getText().toString().equals(""))
    {
        writeCuve.setWriteInt1(0);
    }
    else {
        writeCuve.setWriteInt1(Integer.parseInt(var1.getText().toString()));
    }

        if(var2.getText().toString().equals(""))
        {
            writeCuve.setWriteInt2(0);
        }
        else {
            writeCuve.setWriteInt2(Integer.parseInt(var2.getText().toString()));
        }

        if(var3.getText().toString().equals(""))
        {
            writeCuve.setWriteInt3(0);
        }
        else {
            writeCuve.setWriteInt3(Integer.parseInt(var3.getText().toString()));
        }

        if(var4.getText().toString().equals(""))
        {
            writeCuve.setWriteInt4(0);
        }
        else {
            writeCuve.setWriteInt4(Integer.parseInt(var4.getText().toString()));
        }





    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.var1_switch_cuve:
                a1++;
                if(a1 % 2 == 0)
                {
                    writeCuve.setWriteBool(1, 0);
                }
                else {
                    writeCuve.setWriteBool(1, 1);
                }
                break;
            case R.id.var2_switch_cuve:
                a2++;
                if(a2 % 2 == 0)
                {
                    writeCuve.setWriteBool(2, 0);
                }
                else {
                    writeCuve.setWriteBool(2, 1);
                }
                break;
            case R.id.var3_switch_cuve:
                a3++;
                if(a3 % 2 == 0)
                {
                    writeCuve.setWriteBool(4, 0);
                }
                else {
                    writeCuve.setWriteBool(4, 1);
                }
                break;
            case R.id.var4_switch_cuve:
                a4++;
                if(a4 % 2 == 0)
                {
                    writeCuve.setWriteBool(8, 0);
                }
                else {
                    writeCuve.setWriteBool(8, 1);
                }
                break;
            case R.id.var5_switch_cuve:
                a5++;
                if(a5 % 2 == 0)
                {
                    writeCuve.setWriteBool(16, 0);
                }
                else {
                    writeCuve.setWriteBool(16, 1);
                }
                break;
            case R.id.var6_switch_cuve:
                a6++;
                if(a6 % 2 == 0)
                {
                    writeCuve.setWriteBool(32, 0);
                }
                else {
                    writeCuve.setWriteBool(32, 1);
                }
                break;
            case R.id.var7_switch_cuve:
                a7++;
                if(a7 % 2 == 0)
                {
                    writeCuve.setWriteBool(64, 0);
                }
                else {
                    writeCuve.setWriteBool(64, 1);
                }
                break;
            case R.id.var8_switch_cuve:
                a8++;
                if(a8 % 2 == 0)
                {
                    writeCuve.setWriteBool(128, 0);
                }
                else {
                    writeCuve.setWriteBool(128, 1);
                }
                break;

            case R.id.var20_switch_cuve:
                b1++;
                if(b1 % 2 == 0)
                {
                    writeCuve.setWriteBool2(1, 0);
                }
                else {
                    writeCuve.setWriteBool2(1, 1);
                }
                break;
            case R.id.var21_switch_cuve:
                b2++;
                if(b2 % 2 == 0)
                {
                    writeCuve.setWriteBool2(2, 0);
                }
                else {
                    writeCuve.setWriteBool2(2, 1);
                }
                break;
            case R.id.var23_switch_cuve:
                b3++;
                if(b3 % 2 == 0)
                {
                    writeCuve.setWriteBool2(4, 0);
                }
                else {
                    writeCuve.setWriteBool2(4, 1);
                }
                break;
            case R.id.var24_switch_cuve:
                b4++;
                if(b4 % 2 == 0)
                {
                    writeCuve.setWriteBool2(8, 0);
                }
                else {
                    writeCuve.setWriteBool2(8, 1);
                }
                break;
            case R.id.var25_switch_cuve:
                b5++;
                if(b5 % 2 == 0)
                {
                    writeCuve.setWriteBool2(16, 0);
                }
                else {
                    writeCuve.setWriteBool2(16, 1);
                }

                break;
            case R.id.var26_switch_cuve:
                b6++;
                if(b6 % 2 == 0)
                {
                    writeCuve.setWriteBool2(32, 0);
                }
                else {
                    writeCuve.setWriteBool2(32, 1);
                }
                break;
            case R.id.var27_switch_cuve:
                b7++;
                if(b7 % 2 == 0)
                {
                    writeCuve.setWriteBool2(64, 0);
                }
                else {
                    writeCuve.setWriteBool2(64, 1);
                }

                break;
            case R.id.var28_switch_cuve:
                b8++;
                if(b8 % 2 == 0)
                {
                    writeCuve.setWriteBool2(128, 0);
                }
                else {
                    writeCuve.setWriteBool2(128, 1);
                }

                break;



        }

    }
}

package be.heh.main.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.menu.Menu;

public class FormUsers extends AppCompatActivity implements View.OnClickListener {

    private EditText login;
    private EditText email;
    private EditText password;
    private Button btn_register;
    private TextView errorlogin;
    private TextView erroremail;
    private TextView errorpassword;
    private boolean logine;
    private boolean emaile;
    private boolean passworde;
    private boolean emailexist = false;
    private EditText confirm_password;
    private UserAccessDB userAccessDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_users);
        userAccessDB = new UserAccessDB(this);
        this.login = (EditText) findViewById(R.id.loginusers);
        this.email = (EditText) findViewById(R.id.mailusers);
        this.password = (EditText) findViewById(R.id.passwordusers);
        this.btn_register = (Button) findViewById(R.id.btn_registerusers);
        this.erroremail = (TextView) findViewById(R.id.erroremailusers);
        this.errorlogin = (TextView) findViewById(R.id.errorloginusers);
        this.errorpassword = (TextView) findViewById(R.id.errorpasswordusers);
        this.confirm_password = (EditText) findViewById(R.id.confirm_passwordusers);
        this.btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.btn_registerusers:
                userAccessDB.openForRead();
                SuperAdministrateur users =userAccessDB.getSuperAdmin(this.email.getText().toString());
                userAccessDB.Close();


                String d = this.confirm_password.getText().toString();
                if(this.login.getText().toString().length() == 0)
                {
                    this.errorlogin.setText("Le Champ est vide");
                    this.errorlogin.setVisibility(View.VISIBLE);
                }
                else
                {
                    this.logine = true;
                    this.errorlogin.setVisibility(View.INVISIBLE);
                }

                if(this.email.getText().toString().length() == 0 || this.email.getText().toString().indexOf("@") == -1 || users != null)
                {
                    this.erroremail.setText("Le champ est vide on ne ressemble pas à un email");
                    this.erroremail.setVisibility(View.VISIBLE);
                    System.out.println(emailexist);

                }
                else {
                    this.erroremail.setVisibility(View.INVISIBLE);
                    this.emaile = true;
                    emailexist = false;

                }
                if(this.password.getText().toString().length() == 0 || this.password.getText().toString().length() < 4 || !(this.password.getText().toString().equals(confirm_password.getText().toString())))
                {
                    this.errorpassword.setText("Le mot de passe est vide ou a moins de 4 caractère ou les mdp ne correspondent pas");
                    this.errorpassword.setVisibility(View.VISIBLE);
                }

                else {
                    this.errorpassword.setVisibility(View.INVISIBLE);
                    this.passworde = true;
                }

                if(this.logine == true && this.emaile == true && this.passworde == true && users == null)
                {

                  String sha256 = Hashing.sha256().hashString(this.password.getText().toString(), StandardCharsets.UTF_8).toString();

                    SuperAdministrateur superAdministrateur = new SuperAdministrateur(this.login.getText().toString(),sha256,this.email.getText().toString(), "USER");
                    userAccessDB.openForWrite();
                    userAccessDB.insertSuperAdmin(superAdministrateur);
                    userAccessDB.Close();
                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                    finish();

                }

                break;
        }
    }
}

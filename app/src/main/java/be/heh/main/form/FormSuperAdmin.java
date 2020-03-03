package be.heh.main.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.menu.Menu;

public class FormSuperAdmin extends AppCompatActivity implements View.OnClickListener {

    private EditText login;
    private EditText email;
    private EditText password;
    private Button btn_register;
    private TextView errorlogin;
    private TextView erroremail;
    private TextView errorpassword;
    private UserAccessDB userAccessDB;
    private boolean logine;
    private boolean emaile;
    private boolean passworde;
    private EditText confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_super_admin);
        userAccessDB = new UserAccessDB(this);
        this.login = (EditText) findViewById(R.id.login);
        this.email = (EditText) findViewById(R.id.mail);
        this.password = (EditText) findViewById(R.id.password);
        this.btn_register = (Button) findViewById(R.id.btn_register);
        this.erroremail = (TextView) findViewById(R.id.erroremail);
        this.errorlogin = (TextView) findViewById(R.id.errorlogin);
        this.errorpassword = (TextView) findViewById(R.id.errorpassword);
        this.confirm_password = (EditText) findViewById(R.id.confirm_password);
        this.btn_register.setOnClickListener(this);




    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_register:

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

                if(this.email.getText().toString().length() == 0 || this.email.getText().toString().indexOf("@") == -1)
                {
                    this.erroremail.setText("Le champ est vide on ne ressemble pas à un email");
                    this.erroremail.setVisibility(View.VISIBLE);

                }
                else {
                    this.erroremail.setVisibility(View.INVISIBLE);
                    this.emaile = true;
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

                if(this.logine == true && this.emaile == true && this.passworde == true)
                {


                    String sha256 = Hashing.sha256().hashString(this.password.getText().toString(), StandardCharsets.UTF_8).toString();
                    System.out.println("ok");
                    //userAccessDB.removeTableSuperUser();
                    SuperAdministrateur superAdministrateur = new SuperAdministrateur(this.login.getText().toString(),sha256,this.email.getText().toString(), "SuperAdmin");
                    userAccessDB.openForWrite();
                    userAccessDB.insertSuperAdmin(superAdministrateur);
                    userAccessDB.Close();
                    userAccessDB.openForRead();
                    ArrayList<SuperAdministrateur> e = userAccessDB.getAllSuperAdmin();
                    userAccessDB.Close();

                    if(e.size() > 0)
                    {
                        Intent intentmenu = new Intent(this, Menu.class);
                        startActivity(intentmenu);
                        finish();
                    }




                }

                break;
        }
    }
}

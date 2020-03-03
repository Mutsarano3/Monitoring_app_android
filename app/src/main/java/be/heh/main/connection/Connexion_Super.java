package be.heh.main.connection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

import be.heh.main.Accueil.AccueilSuper;
import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;

public class Connexion_Super extends AppCompatActivity implements View.OnClickListener {

    private Button btn_valide;
    private EditText et_email;
    private EditText et_password;
    private TextView txt_error;
    private UserAccessDB userAccessDB;
    SharedPreferences prefs_datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion__super);
        userAccessDB = new UserAccessDB(this);
        this.btn_valide = (Button) findViewById(R.id.btn_valide_super);
        this.et_email = (EditText) findViewById(R.id.connection_email);
        this.et_password = (EditText) findViewById(R.id.connection_password);
        this.txt_error = (TextView) findViewById(R.id.erreur_connexion);
        prefs_datas = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.btn_valide.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_valide_super:
                userAccessDB.openForRead();
                String sha256 = Hashing.sha256().hashString(this.et_password.getText().toString(), StandardCharsets.UTF_8).toString();
                SuperAdministrateur superemail = userAccessDB.getSuperAdmin(et_email.getText().toString());
                //SuperAdministrateur superpassword = userAccessDB.getSuperAdminPassword(et_password.getText().toString());
                SuperAdministrateur admin = userAccessDB.getSuperAdminMailAndPassword(et_email.getText().toString(),sha256);
                System.out.println(userAccessDB.getAllSuperAdmin().size());
                userAccessDB.Close();


                if(admin == null || admin.getId() != 1)
                {

                    this.txt_error.setVisibility(View.VISIBLE);
                    this.txt_error.setText("Votre email ou mot de passe ne sont pas valide");
                }
                else if(admin !=null)
                {

                    SharedPreferences.Editor editeur_datas = prefs_datas.edit();
                    editeur_datas.putString("mail", superemail.getMail());
                    editeur_datas.commit();
                    this.txt_error.setVisibility(View.INVISIBLE);
                    System.out.println("OK" + superemail.getId());
                    Intent intent = new Intent(this, AccueilSuper.class);
                    startActivity(intent);
                    finish();

                }

                break;
        }
    }
}

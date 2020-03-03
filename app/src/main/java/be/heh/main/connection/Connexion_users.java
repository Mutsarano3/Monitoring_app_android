package be.heh.main.connection;

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

import be.heh.main.Accueil.AccueilUsers;
import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;

public class Connexion_users extends AppCompatActivity implements View.OnClickListener {

    private EditText et_connxion_mail;
    private EditText et_connxion_password;
    private Button btn_valide;
    private TextView txt_error;
    private UserAccessDB userAccessDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_users);
        userAccessDB = new UserAccessDB(this);
        this.txt_error = (TextView) findViewById(R.id.txt_error_users);
        this.et_connxion_mail = (EditText) findViewById(R.id.connexion_users_mail);
        this.et_connxion_password = (EditText) findViewById(R.id.connexion_users_password);
        this.btn_valide = (Button) findViewById(R.id.valide_users);
        this.btn_valide.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.valide_users:
                String sha256 = Hashing.sha256().hashString(this.et_connxion_password.getText().toString(), StandardCharsets.UTF_8).toString();
                userAccessDB.openForRead();
                SuperAdministrateur superemail = userAccessDB.getSuperAdmin(et_connxion_mail.getText().toString());
                //SuperAdministrateur superpassword = userAccessDB.getSuperAdminPassword(et_connxion_password.getText().toString());
                SuperAdministrateur admin = userAccessDB.getSuperAdminMailAndPassword(et_connxion_mail.getText().toString(),sha256);

                userAccessDB.Close();
                if(admin == null|| superemail.getRole().equals("SuperAdmin"))
                {
                    this.txt_error.setVisibility(View.VISIBLE);
                    this.txt_error.setText("Votre email ou mot de passe ne sont pas valide");

                }
                else if(admin != null)
                {

                    this.txt_error.setVisibility(View.INVISIBLE);
                    System.out.println("OK" + superemail.getId());
                    Intent intent = new Intent(this, AccueilUsers.class);
                    intent.putExtra("mail",superemail.getMail());
                    startActivity(intent);
                    finish();
                }

                else
                {
                    this.txt_error.setVisibility(View.VISIBLE);
                    this.txt_error.setText("Votre email ou mot de passe ne sont pas valide");
                }
                break;
        }
    }
}

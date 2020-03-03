package be.heh.main.changesuper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.connection.Connexion_Super;
import be.heh.main.connection.Connexion_users;
import be.heh.main.entity.SuperAdministrateur;

public class ChangePasswordUsers extends AppCompatActivity implements View.OnClickListener {

    Bundle extrat;
    private EditText newpassword;
    private EditText confirmpassword;
    private TextView error;
    private Button valide;
    private UserAccessDB userAccessDB;
    private SuperAdministrateur superAdministrateur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extrat=this.getIntent().getExtras();
        setContentView(R.layout.activity_change_password_users);
        userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        superAdministrateur = userAccessDB.getSuperAdmin(extrat.getString("mail"));
        userAccessDB.Close();
        this.newpassword = (EditText) findViewById(R.id.new_password_users);
        this.confirmpassword = (EditText) findViewById(R.id.new_password_confirm_users);
        this.error = (TextView) findViewById(R.id.error_change_password_users);
        this.valide = (Button) findViewById(R.id.change_valide_password_users);
        this.valide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.change_valide_password_users:
                if(!(newpassword.getText().toString().equals(confirmpassword.getText().toString())))
                {
                    error.setText("Erreur de changement de mot de passe");
                    error.setVisibility(View.VISIBLE);
                }

                else if(newpassword.getText().toString().length() <4 || newpassword.getText().toString().length() == 0)
                {
                    error.setText("Mot de passe vide ou ne depasse pas 4 caractères");
                    error.setVisibility(View.VISIBLE);
                }

                else {
                    String sha256 = Hashing.sha256().hashString(this.newpassword.getText().toString(), StandardCharsets.UTF_8).toString();
                    superAdministrateur.setPassword(sha256);

                    userAccessDB.openForWrite();
                    userAccessDB.updateSuperAdmin(superAdministrateur.getId(),superAdministrateur);
                    userAccessDB.Close();
                    Intent intent1 = new Intent(this, Connexion_users.class);
                    startActivity(intent1);
                    finish();

                }
                break;
        }

    }
}

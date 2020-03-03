package be.heh.main.changesuper;

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
import be.heh.main.connection.Connexion_Super;
import be.heh.main.entity.SuperAdministrateur;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    private EditText newpassword;
    private EditText confirmpassword;
    private TextView error;
    private Button valide;
    private UserAccessDB userAccessDB;
    private SuperAdministrateur superAdministrateur;
    SharedPreferences prefs_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        prefs_datas = PreferenceManager.getDefaultSharedPreferences(getApplication());
        userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        superAdministrateur = userAccessDB.getSuperAdmin(prefs_datas.getString("mail","NULL"));
        userAccessDB.Close();
        newpassword = (EditText) findViewById(R.id.new_password);
        confirmpassword = (EditText) findViewById(R.id.new_password_confirm);
        error = (TextView) findViewById(R.id.error_change_password);
        valide = (Button) findViewById(R.id.change_valide_password);
        valide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.change_valide_password:

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
                    Intent intent1 = new Intent(this, Connexion_Super.class);
                    startActivity(intent1);
                    finish();

                }
                break;
        }
    }
}

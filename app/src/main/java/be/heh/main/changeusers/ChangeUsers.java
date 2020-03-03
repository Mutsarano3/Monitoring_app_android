package be.heh.main.changeusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.connection.Connexion_users;
import be.heh.main.entity.SuperAdministrateur;

public class ChangeUsers extends AppCompatActivity implements View.OnClickListener {

    private EditText et_login;
    private EditText et_email;
    private Bundle extrat;
    private Button btn;
    private UserAccessDB userAccessDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_users);
        extrat = this.getIntent().getExtras();
        et_login = (EditText) findViewById(R.id.login_change);
        btn = (Button) findViewById(R.id.change_users_confirm);
        userAccessDB = new UserAccessDB(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.change_users_confirm:
                userAccessDB.openForRead();
                SuperAdministrateur o = null;
                o =  userAccessDB.getSuperAdmin(extrat.getString("mail"));
                userAccessDB.Close();
                if((et_login.getText().toString().length() == 0))
                {
                    Toast.makeText(this,"Erreur les champs sont vides ou l'email existe",Toast.LENGTH_SHORT).show();
                }
                else   {
                    o.setLogin(et_login.getText().toString());
                    userAccessDB.openForWrite();
                    userAccessDB.updateSuperAdmin(o.getId(),o);
                    userAccessDB.Close();

                    Intent intent = new Intent(this, Connexion_users.class);
                    startActivity(intent);
                }
                break;
        }
    }
}

package be.heh.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.heh.main.R;
import be.heh.main.connection.Connexion_Super;
import be.heh.main.connection.Connexion_users;
import be.heh.main.form.FormUsers;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button btn_connection_super;
    private Button btn_form_users;
    private Button btn_connection_users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.btn_connection_super = (Button) findViewById(R.id.superadminconnect);
        this.btn_form_users = (Button) findViewById(R.id.inscriptionutilisateur);
        this.btn_connection_users = (Button) findViewById(R.id.connexionusers);
        this.btn_connection_super.setOnClickListener(this);
        btn_connection_users.setOnClickListener(this);
        btn_form_users.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.superadminconnect:
                Intent  connexion_super = new Intent(this, Connexion_Super.class);
                startActivity(connexion_super);

                break;
            case R.id.inscriptionutilisateur:
                Intent form_users = new Intent(this, FormUsers.class);
                startActivity(form_users);

                break;

            case R.id.connexionusers:
                Intent connexionusers = new Intent(this, Connexion_users.class);
                startActivity(connexionusers);

                break;
        }
    }
}

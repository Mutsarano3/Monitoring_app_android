package be.heh.main.Accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.changesuper.ChangePassword;
import be.heh.main.changesuper.ChangePasswordUsers;
import be.heh.main.changeusers.ChangeUsers;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.menu.Menu;
import be.heh.main.supervision.Configuration;

public class AccueilUsers extends AppCompatActivity implements View.OnClickListener {

    private TextView login;
    private TextView email;
    private TextView role;
    private Button chngmdp;
    private Button backmenu;
    private Button supervision;
    private UserAccessDB userAccessDB;
    private Button change;
    Bundle extrat;
    private String role_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_users);
        userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        extrat=this.getIntent().getExtras();
        SuperAdministrateur users = userAccessDB.getSuperAdmin(extrat.getString("mail"));
        userAccessDB.Close();
        this.login = (TextView) findViewById(R.id.login_txt_users);
        this.role = (TextView) findViewById(R.id.role_users);
        this.email = (TextView) findViewById(R.id.mail_txt_users);
        role_users = users.getRole();
        this.login.setText("Nom d'affichage: "+users.getLogin());
        this.role.setText("Role : "+users.getRole());
        this.email.setText("Email : " + users.getMail());
        this.backmenu = (Button) findViewById(R.id.back_menu_users);
        this.backmenu.setOnClickListener(this);
        this.chngmdp = (Button) findViewById(R.id.btn_change_mdp_users);
        this.chngmdp.setOnClickListener(this);
        this.supervision = (Button) findViewById(R.id.supervision_users);
        this.change = (Button) findViewById(R.id.btn_change_users);
        this.supervision.setOnClickListener(this);
        this.change.setOnClickListener(this);
        if(users.getRole().equals("USER"))
        {
            this.supervision.setText("Accéder a la supervision Read");
        }

        else {
            this.supervision.setText("Accéder a la supervision R/W");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.back_menu_users:
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_change_mdp_users:
                Intent intent1 = new Intent(this, ChangePasswordUsers.class);
                intent1.putExtra("mail",extrat.getString("mail"));
                startActivity(intent1);
                finish();
                break;
            case R.id.supervision_users:
                Intent intent2 = new Intent(this, Configuration.class);
                intent2.putExtra("role",role_users);
                startActivity(intent2);
                break;

            case R.id.btn_change_users:
                Intent intent3 = new Intent(this, ChangeUsers.class);
                intent3.putExtra("mail",extrat.getString("mail"));
                startActivity(intent3);
                break;
        }
    }
}

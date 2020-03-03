package be.heh.main.Accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.changesuper.ChangePassword;
import be.heh.main.delete_users.Delete;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.gestion_users.Gestion_users;
import be.heh.main.menu.Menu;
import be.heh.main.supervision.Configuration;

public class AccueilSuper extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_login;
    private TextView txt_email;
    private TextView txt_role;
    private UserAccessDB userAccessDB;
    private Button change_password;
    private Button gestion_users;
    private Button backmenusuper;
    private EditText saissir;
    private Button btn_admin;
    private Button btn_users;
    private Button btn_supervision;
    private String role;
    private Button delete;
    SharedPreferences prefs_datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_super_user);
        this.delete = (Button) findViewById(R.id.btn_delete_user);
        this.txt_login = (TextView) findViewById(R.id.login_txt);
        this.txt_email = (TextView) findViewById(R.id.mail_txt);
        this.txt_role = (TextView) findViewById(R.id.role);
        change_password = (Button) findViewById(R.id.btn_change_mdp);
        gestion_users = (Button) findViewById(R.id.gestion_users);
        backmenusuper = (Button) findViewById(R.id.back_menu_super);
        this.saissir = (EditText) findViewById(R.id.change_role);
        this.btn_admin = (Button) findViewById(R.id.admin);
        this.btn_users = (Button) findViewById(R.id.user);
        prefs_datas = PreferenceManager.getDefaultSharedPreferences(getApplication());
        userAccessDB = new UserAccessDB(this);
        this.btn_supervision = (Button) findViewById(R.id.supervision_superadmin);
        this.btn_supervision.setOnClickListener(this);
        this.gestion_users.setOnClickListener(this);
        this.change_password.setOnClickListener(this);
        backmenusuper.setOnClickListener(this);
        this.btn_users.setOnClickListener(this);
        this.btn_admin.setOnClickListener(this);
        this.delete.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAccessDB.openForRead();
        SuperAdministrateur superAdministrateur = userAccessDB.getSuperAdmin(prefs_datas.getString("mail","NULL"));
        userAccessDB.Close();
        role = superAdministrateur.getRole();
        this.txt_login.setText("Nom d'affichage: "+superAdministrateur.getLogin());
        this.txt_email.setText("Email : "+superAdministrateur.getMail());
        this.txt_role.setText("Role : "+superAdministrateur.getRole());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_change_mdp:
                Intent intent1 = new Intent(this, ChangePassword.class);
                startActivity(intent1);
                finish();
                break;


            case R.id.gestion_users:
                Intent intent3 = new Intent(this, Gestion_users.class);
                startActivity(intent3);
                break;

            case R.id.back_menu_super:
                Intent intent2 = new Intent(this, Menu.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.admin:
                SuperAdministrateur superAdministrateur = null;
                if(saissir.getText().toString().length() == 0 || saissir.getText().toString().equals("1"))
                {
                    Toast.makeText(this,
                            "Erreur lors du changement veuillez ressayer"
                            ,Toast.LENGTH_LONG).show();
                }
                else
                {
                    userAccessDB.openForRead();
                     superAdministrateur = userAccessDB.getSuperAdminId(saissir.getText().toString());
                    userAccessDB.Close();

                }

                if(superAdministrateur == null)
                {
                    Toast.makeText(this,
                            "Erreur lors du changement veuillez ressayer"
                            ,Toast.LENGTH_LONG).show();
                }
                else{

                    userAccessDB.openForWrite();
                    superAdministrateur.setRole("ADMIN");
                    userAccessDB.updateSuperAdmin(superAdministrateur.getId(),superAdministrateur);
                    userAccessDB.Close();

                }
                break;
            case R.id.user:
                SuperAdministrateur superAdministrateur1 = null;
                if(saissir.getText().toString().length() == 0 ||  saissir.getText().toString().equals("1"))
                {
                    Toast.makeText(this,
                            "Erreur lors du changement veuillez ressayer"
                            ,Toast.LENGTH_LONG).show();
                }
                else {
                    userAccessDB.openForRead();
                     superAdministrateur1 = userAccessDB.getSuperAdminId(saissir.getText().toString());
                    userAccessDB.Close();

                }


                if(superAdministrateur1 == null)
                {
                    Toast.makeText(this,
                            "Erreur lors du changement veuillez ressayer"
                            ,Toast.LENGTH_LONG).show();
                }
                else{

                    userAccessDB.openForWrite();
                    superAdministrateur1.setRole("USER");
                    userAccessDB.updateSuperAdmin(superAdministrateur1.getId(),superAdministrateur1);
                    userAccessDB.Close();

                }
                break;
            case R.id.supervision_superadmin:
                Intent intentsupervision = new Intent(this, Configuration.class);
                intentsupervision.putExtra("role",role);
                startActivity(intentsupervision);
                break;

            case R.id.btn_delete_user:

                Intent intentdelete = new Intent(this, Delete.class);
                startActivity(intentdelete);
                break;

        }
    }
}

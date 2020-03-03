package be.heh.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.form.FormSuperAdmin;
import be.heh.main.menu.Menu;

public class MainActivity extends AppCompatActivity {

    private UserAccessDB superadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    protected void onStart() {
        super.onStart();
        superadmin = new UserAccessDB(this);
        superadmin.openForRead();
        ArrayList<SuperAdministrateur> e = superadmin.getAllSuperAdmin();
        superadmin.Close();
        System.out.println("Nombre de personne : => "+e.size());

        if(e.size() ==  0)
        {

            Intent intentsuperadmin = new Intent(this, FormSuperAdmin.class);
            startActivity(intentsuperadmin);
            finish();

        }

        else
        {
            Intent intentmenu = new Intent(this, Menu.class);
            startActivity(intentmenu);
            finish();

        }


    }
}

package be.heh.main.gestion_users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;

public class Gestion_users extends AppCompatActivity {

    private ListView liste;
    private UserAccessDB userAccessDB;
    private ArrayList<SuperAdministrateur> superAdministrateurs;
    private ArrayList<SuperAdministrateur> superAdministrateursbon = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_users);
        userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        superAdministrateurs = userAccessDB.getAllSuperAdmin();
        userAccessDB.Close();
        for(SuperAdministrateur u : superAdministrateurs)
        {
            if(u.getId() != 1)
            {
                superAdministrateursbon.add(u);

            }
        }

        for(SuperAdministrateur u : superAdministrateursbon)
        {
            strings.add("ID "+u.getId()+" |"+u.getMail()+ "| ROLE:"+u.getRole());
        }
        liste = (ListView) findViewById(R.id.list_users);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Gestion_users.this,
                android.R.layout.simple_list_item_1,strings);
        liste.setAdapter(adapter);

    }
}

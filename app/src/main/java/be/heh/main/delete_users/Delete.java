package be.heh.main.delete_users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.main.R;
import be.heh.main.accessbd.UserAccessDB;
import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.gestion_users.Gestion_users;

public class Delete extends AppCompatActivity implements View.OnClickListener {

    private Button btn_users;
    private Button delete;
    private EditText et_id;
    private TextView error;
    private UserAccessDB userAccessDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        userAccessDB = new UserAccessDB(this);
        this.btn_users = (Button) findViewById(R.id.back_to_users);
        this.delete = (Button) findViewById(R.id.delete_users);
        this.et_id = (EditText) findViewById(R.id.id_delete);
        this.error = (TextView) findViewById(R.id.error_id);
        btn_users.setOnClickListener(this);
        delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_to_users:
                Intent intent = new Intent(this, Gestion_users.class);
                startActivity(intent);
                break;
            case R.id.delete_users:
                SuperAdministrateur u = null;
                if(et_id.getText().toString().length() == 0 || et_id.getText().toString().equals("1"))
                {
                    Toast.makeText(this,"La suppression de l'utilisateur a échoué cause du champ vide",Toast.LENGTH_SHORT).show();
                }
                else{
                    userAccessDB.openForRead();
                   u = userAccessDB.getSuperAdminId(et_id.getText().toString());
                    userAccessDB.Close();
                }

                if(u == null)
                {
                    error.setVisibility(View.VISIBLE);
                }
                else{

                    userAccessDB.openForWrite();
                    userAccessDB.removeUser(et_id.getText().toString());
                    userAccessDB.Close();
                    Toast.makeText(this,"La suppression de l'utilisateur a été effectué avec succès",Toast.LENGTH_SHORT).show();
                    error.setVisibility(View.GONE);
                }
                break;

        }
    }
}

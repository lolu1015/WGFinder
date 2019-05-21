package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import hs.karlsruhe.wgfinder.Entity.Login;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton erstelleAccountButton, loginButton;
    LoginRoomDatabase dbLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("LOGIN WG-Finder");

        erstelleAccountButton = findViewById(R.id.am_b_AccountErstellen);
        loginButton = findViewById(R.id.am_b_Login);

        dbLogin = LoginRoomDatabase.getDatabase(this);


        // Button wird zum onClickListener hinzugefuegt
        erstelleAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_beenden) {

            // Hier eine Funktion aufrufen, die beim Klick auf "Beenden" ausgeführt werden soll.

            return true;
        }

        if (id == R.id.action_info) {

            // Hier eine Funktion aufrufen, die beim Klick auf "Informationen" ausgeführt werden soll.

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.am_b_AccountErstellen:
                sendMessage(v);
                break;
            case R.id.am_b_Login:
                onLoginPressed();

                break;

        }
    }

    private void onLoginPressed() {

        final String email = ((EditText) findViewById(R.id.am_et_Email)).getText().toString();
        final String passwort = ((EditText) findViewById(R.id.am_et_Passwort)).getText().toString();
        if(email.equals("") || passwort.equals("")) {
            Toast.makeText(getApplicationContext(), "Bitte Email oder Passwort eingeben!", Toast.LENGTH_LONG).show();
        }else {
            try {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Login loginOfDb = dbLogin.loginDAO().findLogin(email);
                        if (loginOfDb != null) {
                            if (loginOfDb.getPasswort().equals(passwort)) {
                                //hier Activity ändern
                                Toast.makeText(getApplicationContext(), "Anmeldung erfolgt!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Passwort falsch!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email nicht vergeben!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            catch(Exception e) {
                e.printStackTrace();
                Log.e("Exception","exceptions"+e);
            }
        }

    }



    public void sendMessage(View view)
    {
        Intent intent = new Intent(this,Account_erstellen.class);
        startActivity(intent);
    }

}

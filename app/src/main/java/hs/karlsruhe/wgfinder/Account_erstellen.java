package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import hs.karlsruhe.wgfinder.Entity.Benutzer;
import hs.karlsruhe.wgfinder.Entity.Login;

public class Account_erstellen extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton erstelleAccountButton, abbrechenButton;
    WGFinderRoomDatabase db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_erstellen);

        setTitle("Account erstellen");

        erstelleAccountButton = findViewById(R.id.ace_b_AccountErstellen);
        abbrechenButton = findViewById(R.id.ace_b_Abbrechen);

        db = WGFinderRoomDatabase.getDatabase(this);

        erstelleAccountButton.setOnClickListener(this);
        abbrechenButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ace_b_Abbrechen:
                sendMessage(v);
                break;

            case R.id.ace_b_AccountErstellen:
                onAccountErstellenPressed(v);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onAccountErstellenPressed(View v){

        final Benutzer bn = new Benutzer();
        String vorname  = ((EditText) findViewById(R.id.ace_et_Vorname)).getText().toString();
        String nachname  = ((EditText) findViewById(R.id.ace_et_Nachname)).getText().toString();
        final String email  = ((EditText) findViewById(R.id.ace_et_Email)).getText().toString();
        String passwort  = ((EditText) findViewById(R.id.ace_et_Passwort)).getText().toString();
        String passwortWdh  = ((EditText) findViewById(R.id.ace_et_PasswortWdh)).getText().toString();
        final Login login = new Login();


        bn.setVorname(vorname);
        bn.setNachname(nachname);
        bn.setEmail(email);
        login.setEmail(email);
        if(passwort.equals(passwortWdh)) {
            login.setPasswort(passwort);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    if(db.loginDAO().findLogin(email) != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Account_erstellen.this, "Email vorhanden!", Toast.LENGTH_LONG).show();
                            }
                        });


                    } else {
                        db.benutzerDAO().insertBenutzer(bn);
                        db.loginDAO().insertLogin(login);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Account_erstellen.this, "Account erstellt!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });

        } else {

            Toast.makeText(getApplicationContext(), "Passwort nicht gleich!", Toast.LENGTH_LONG).show();
        }

    }
}

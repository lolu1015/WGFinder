package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton erstelleAccountButton, loginButton, twitterButton;
    private AppCompatEditText email, passwort;
    WGFinderRoomDatabase db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("LOGIN WG-Finder");

        erstelleAccountButton = findViewById(R.id.am_b_AccountErstellen);
        loginButton = findViewById(R.id.am_b_Login);
        email = findViewById(R.id.am_et_Email);
        passwort = findViewById(R.id.am_et_Passwort);


        db = WGFinderRoomDatabase.getDatabase(this);


        // Button wird zum onClickListener hinzugefuegt
        erstelleAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    //Mit dem Twitter Button Profil aufrufen

        twitterButton = findViewById(R.id.am_b_LoginFacebook);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openprofilAnsehenActivity();
            }
        });
    }

    //Zu Profil Activity wechseln

    public void openprofilAnsehenActivity() {
        Intent intent = new Intent(this, ProfilAnsehenActivity.class);
        startActivity(intent);
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
        if(validateEmail() & validatePasswort()) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (db.loginDAO().findLogin(email) != null) {
                            if (db.loginDAO().findLogin(email).getPasswort().equals(passwort)) {
                                //hier Activity ändern
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Anmeldung erfolgt!", Toast.LENGTH_LONG).show();
                                    }
                                });
                                if (db.benutzerDAO().findBenutzer(email).getRolle() != null) {
                                    if (db.benutzerDAO().findBenutzer(email).getRolle().equals(1)) {
                                        // Activity wechseln auf Rolle Sucher
                                    } else {
                                        //nicht implementiert
                                    }
                                } else {
                                    wechseleZuRolleAussuchen();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Passwort falsch!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Email nicht vergeben!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        }

    }

    private void wechseleZuRolleAussuchen() {
        Intent intent = new Intent(this,RolleAussuchen.class);
        startActivity(intent);
    }


    public void sendMessage(View view)
    {
        Intent intent = new Intent(this,Account_erstellen.class);
        startActivity(intent);
    }

    public boolean validateEmail () {

        String emailInput = email.getText().toString();

        if(emailInput.isEmpty()) {
            email.setError("Feld muss ausgefüllt werden!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email.setError("Bitte geben Sie eine gültige Email-Adresse ein!");
            return false;
        }else {return true;}
    }
    public boolean validatePasswort () {

        String passwortInput = passwort.getText().toString();

        if(passwortInput.isEmpty()) {
            passwort.setError("Feld muss ausgefüllt werden!");
            return false;
        } else {return true;}
    }

}

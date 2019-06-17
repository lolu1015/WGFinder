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
import hs.karlsruhe.wgfinder.Entity.Temp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton erstelleAccountButton, loginButton;
    private AppCompatEditText email, passwort;
    WGFinderRoomDatabase db;
    private String emailTemp;

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

        final Temp temp = new Temp();
        final String email = ((EditText) findViewById(R.id.am_et_Email)).getText().toString();
        final String passwort = ((EditText) findViewById(R.id.am_et_Passwort)).getText().toString();
        final String passwortHashed = md5(passwort);
        if(validateEmail() & validatePasswort()) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (db.loginDAO().findLogin(email) != null) {
                            if (db.loginDAO().findLogin(email).getPasswort().equals(passwortHashed)) {
                                //hier Login + Activity ändern

                                //temp Token setzen
                                //########Hier wird die neue Spalte nicht gesetzt#######
                                temp.setEmail(email);
                                if(db.tempDAO().anzahlSpalten() > 0) {
                                    db.tempDAO().updateTemp(email);
                                }else {
                                    temp.setId(1);
                                    db.tempDAO().insertTemp(temp);
                                }

                                emailTemp = db.tempDAO().findTemp();



                                //Anmerkung Anmeldung erfolgt
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Anmeldung erfolgt!" +emailTemp, Toast.LENGTH_LONG).show();
                                    }
                                });

                                rolleAussuchen();


                            } else {
                                //falsches Passwort
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Passwort falsch!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            //Email falsch
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

    private void rolleAussuchen() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if (db.benutzerDAO().findBenutzer(emailTemp).getRolle() != null) {
                    if (db.benutzerDAO().findBenutzer(emailTemp).getRolle().equals(1)) {
                        // Activity wechseln auf Rolle Sucher
                        wechseleZuProfilBearbeiten();
                    } else {
                        //nicht implementiert
                    }
                } else {
                    // Activity wechseln auf Rolle aussuchen
                    wechseleZuRolleAussuchen();
                }

            }
        });
    }

    private void wechseleZuRolleAussuchen() {
        Intent intent = new Intent(this,RolleAussuchen.class);
        startActivity(intent);
    }
    private void wechseleZuProfilBearbeiten() {
        //hier muss es wieder auf ProfilBearbeiten geändert werden
        Intent intent = new Intent(this,ProfilAnsehenActivity.class);
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
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

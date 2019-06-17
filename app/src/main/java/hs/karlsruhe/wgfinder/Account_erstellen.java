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
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import hs.karlsruhe.wgfinder.Entity.Benutzer;
import hs.karlsruhe.wgfinder.Entity.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Account_erstellen extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton erstelleAccountButton, abbrechenButton;
    private AppCompatEditText email, passwort, passwortWdh, vorname, nachname;
    private AppCompatCheckBox datenschutz;
    WGFinderRoomDatabase db;
    private static final Pattern PASSWORT_PATTERN = Pattern.compile(
            "^(?=.*[0-9)])(?=.*[a-z)])(?=.*[A-Z)]).{6,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^(?=.*[a-z)])(?=.*[A-Z)]).{2,}$");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_erstellen);

        setTitle("Account erstellen");

        erstelleAccountButton = findViewById(R.id.ace_b_AccountErstellen);
        abbrechenButton = findViewById(R.id.ace_b_Abbrechen);
        email = findViewById(R.id.ace_et_Email);
        passwort = findViewById(R.id.ace_et_Passwort);
        passwortWdh = findViewById(R.id.ace_et_PasswortWdh);
        vorname = findViewById(R.id.ace_et_Vorname);
        nachname= findViewById(R.id.ace_et_Nachname);
        datenschutz = findViewById(R.id.ace_cb_Datenschutz);


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
    public void sendMessage()
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
        final Login login = new Login();


        if(validateEmail() & validatePasswort() & validatePasswortEquals()
        & validateNachname() & validateVorname() & validateDatenschutz()) {

            final String passwortHashed = md5(passwort);
            bn.setVorname(vorname);
            bn.setNachname(nachname);
            bn.setEmail(email);
            login.setEmail(email);
            login.setPasswort(passwortHashed);

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
                        sendMessage();
                    }
                }
            });

        }

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
        } else if(!PASSWORT_PATTERN.matcher(passwortInput).matches()){
            passwort.setError("ein Groß-, ein Kleinbuchstaben, eine Zahl, mind. 6 Zeichen");
            return false;
        }else {return true;}
    }
    public boolean validatePasswortEquals () {

        String passwortWdhInput = passwortWdh.getText().toString();
        String passwortInput = passwort.getText().toString();

        if(passwortWdhInput.isEmpty()) {
            passwortWdh.setError("Feld muss ausgefüllt werden!");
            return false;
        } else if(!(passwortWdhInput.equals(passwortInput))){
            passwortWdh.setError("Passwort stimmt nicht überein!");
            return false;
        }else {return true;}
    }

    public boolean validateVorname () {

        String vornameInput = vorname.getText().toString();

        if(vornameInput.isEmpty()) {
            vorname.setError("Feld muss ausgefüllt werden!");
            return false;
        } else {return true;}
    }
    public boolean validateNachname () {

        String nachnameInput = nachname.getText().toString();

        if(nachnameInput.isEmpty()) {
            nachname.setError("Feld muss ausgefüllt werden!");
            return false;
        } else {return true;}
    }
    public boolean validateDatenschutz () {
        if (!(datenschutz.isChecked())) {
            datenschutz.setError("Datenschutzbestimmungen müssen zugestimmt werden!");
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

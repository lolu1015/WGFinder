package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilAnsehenActivity extends AppCompatActivity {

    WGFinderRoomDatabase db;
    private TextView beschreibungTextView, vornameTextView, nachnameTextView, preisTextView, wohflaecheTextView, alterTextView;
    private Button bearbeitenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGFinderRoomDatabase.getDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ansehen);
        setTitle("Dein Profil");

        beschreibungTextView = findViewById(R.id.apa_beschreibung);
        vornameTextView = findViewById(R.id.apa_vorname);
        alterTextView = findViewById(R.id.apa_alter);
        nachnameTextView = findViewById(R.id.apa_nachname);
        preisTextView = findViewById(R.id.apa_preis);
        wohflaecheTextView = findViewById(R.id.apa_wohnflaeche);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //zuletzt gespeicherten Namen aus Datenbank holen
                final Benutzer oldName = db.benutzerDAO().getLastName();

                //Lade den Namen in die TextView
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfilAnsehenActivity.this, "Aktuelle Daten werden geladen", Toast.LENGTH_SHORT).show();
                        beschreibungTextView.setText(oldName.getEmail());
                        vornameTextView.setText(oldName.getVorname());
                        nachnameTextView.setText(oldName.getNachname());
                        if(oldName.getPreis() != null)
                            preisTextView.setText(oldName.getPreis().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis noch nicht da", Toast.LENGTH_SHORT).show();
                        if(oldName.getWohnflaeche() != null)
                            wohflaecheTextView.setText(oldName.getWohnflaeche().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis noch nicht da", Toast.LENGTH_SHORT).show();
                        if(oldName.getRolle() != null)
                        alterTextView.setText(oldName.getRolle());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        bearbeitenButton = findViewById(R.id.apa_bearbeiten_button);
        bearbeitenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfilBearbeitenActivity();
            }
        });


    }

    public void openProfilBearbeitenActivity() {
        Intent intent = new Intent(this, ProfilBearbeitenActivity.class);
        startActivity(intent);
    }


}

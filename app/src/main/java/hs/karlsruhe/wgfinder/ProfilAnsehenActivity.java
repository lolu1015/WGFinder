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
    private TextView beschreibungTextView, pwTextView, preisTextView;
    private Button bearbeitenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGFinderRoomDatabase.getDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ansehen);
        setTitle("Dein Profil");

        beschreibungTextView = findViewById(R.id.apa_beschreibung);
        pwTextView = findViewById(R.id.apa_ort);
        preisTextView = findViewById(R.id.apa_alter);

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
                        pwTextView.setText(oldName.getVorname());
                        if(oldName.getRolle() != null)
                        preisTextView.setText(oldName.getRolle());
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

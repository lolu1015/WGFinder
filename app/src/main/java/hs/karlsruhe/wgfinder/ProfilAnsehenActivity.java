package hs.karlsruhe.wgfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilAnsehenActivity extends AppCompatActivity {

    WGFinderRoomDatabase db;
    private TextView beschreibungTextView, vornameTextView, nachnameTextView, preisTextView,
            wohflaecheTextView, mitbewohnerTextView, alterTextView, hobbysTextView,
            raucherTextView, haustierTextView, ortTextView, geschlechtTextView;
    private AppCompatButton zurSucheButton;
    private ImageView profilBildView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profil_ansehen, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_LogOut:
                logOut();
                return true;
            case R.id.action_ProfilBearbeiten:
                openProfilBearbeiten();
                return true;


                default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openProfilBearbeiten() {
        Intent intent = new Intent(this,ProfilBearbeitenActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.tempDAO().deleteTemps();
                changeToMain();
            }
        });
    }


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
        mitbewohnerTextView = findViewById(R.id.apa_anz_mitbewohner);
        hobbysTextView = findViewById(R.id.apa_hobbys);
        raucherTextView = findViewById(R.id.apa_raucherwg);
        haustierTextView = findViewById(R.id.apa_haustiere);
        ortTextView = findViewById(R.id.apa_ort);
        geschlechtTextView = findViewById(R.id.apa_geschlecht);

        profilBildView = findViewById(R.id.profilBildView);
        profilBildView.setImageResource(R.drawable.profilbild);


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
                        if (oldName.getEmail() != null)
                        beschreibungTextView.setText(oldName.getEmail());
                        if (oldName.getVorname() != null)
                        vornameTextView.setText(oldName.getVorname());
                        if (oldName.getNachname() != null)
                        nachnameTextView.setText(oldName.getNachname());
                        if(oldName.getPreis() != null)
                            preisTextView.setText(oldName.getPreis().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis noch nicht da", Toast.LENGTH_SHORT).show();
                        if(oldName.getWohnflaeche() != null)
                            wohflaecheTextView.setText(oldName.getWohnflaeche().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis noch nicht da", Toast.LENGTH_SHORT).show();
                        if(oldName.getAlter() != null)
                        alterTextView.setText(oldName.getAlter().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();
                        if(oldName.getMitbewohner() != null)
                            mitbewohnerTextView.setText(oldName.getMitbewohner().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();
                        if(oldName.getHobbys() != null)
                            hobbysTextView.setText(oldName.getHobbys());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();
                        if(oldName.getRaucher() != null){
                            if(oldName.getRaucher() == true)
                            raucherTextView.setText("Ja");
                        else raucherTextView.setText("Nein");}

                        if(oldName.getHaustiere() != null)
                            if(oldName.getHaustiere() == true)
                            haustierTextView.setText("Ja");
                        else haustierTextView.setText("Nein");

                        if(oldName.getOrt() != null)
                            ortTextView.setText(oldName.getOrt().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();
                        if(oldName.getGeschlecht() != null)
                            geschlechtTextView.setText(oldName.getGeschlecht().toString());
                        else
                            Toast.makeText(ProfilAnsehenActivity.this, "Preis ist noch null", Toast.LENGTH_SHORT).show();



                    }
                });
            }
        });

        zurSucheButton = findViewById(R.id.apa_zurSuche_button);
        zurSucheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeViewActivity();
            }
        });


    }

    public void openHomeViewActivity() {
        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);
    }
    public void changeToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}

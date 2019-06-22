package hs.karlsruhe.wgfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import java.util.HashSet;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilAnsehenActivity extends AppCompatActivity {

    WGFinderRoomDatabase db;
    private TextView beschreibungTextView, vornameTextView, nachnameTextView, preisTextView,
            wohflaecheTextView, mitbewohnerTextView, alterTextView, hobbysTextView,
            raucherTextView, haustierTextView, ortTextView, geschlechtTextView;
    private AppCompatButton zurSucheButton;
    private ImageView profilBildView;
    SharedPreferences sp;

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
        Intent intent = new Intent(this, ProfilBearbeitenActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.tempDAO().deleteTemps();
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("ID",new HashSet<String>());
                editor.commit();
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
        sp = getSharedPreferences("Matches", 0);

        beschreibungTextView = findViewById(R.id.apa_beschreibung);
        vornameTextView = findViewById(R.id.apa_vorname);
        alterTextView = findViewById(R.id.apa_alter);
        nachnameTextView = findViewById(R.id.apa_nachname);
        preisTextView = findViewById(R.id.ahv2_tv_preis);
        wohflaecheTextView = findViewById(R.id.ahv2_tv_wohnflaeche);
        mitbewohnerTextView = findViewById(R.id.ahv2_tv_mitbewohner);
        hobbysTextView = findViewById(R.id.apa_hobbys);
        raucherTextView = findViewById(R.id.ahv2_tv_raucher);
        haustierTextView = findViewById(R.id.ahv2_tv_tiere);
        ortTextView = findViewById(R.id.ahv2_tv_ort);
        geschlechtTextView = findViewById(R.id.apa_geschlecht);

        profilBildView = findViewById(R.id.ahv2_iv_bildwg);
        profilBildView.setImageResource(R.drawable.profilbild);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //zuletzt gespeicherten Namen aus Datenbank holen
                final String aktuellerUser = db.tempDAO().findTemp();
                final Benutzer oldName = db.benutzerDAO().findBenutzer(aktuellerUser);


                //Lade den Namen in die TextView
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (oldName.getEmail() != null)
                            beschreibungTextView.setText(oldName.getEmail());
                        if (oldName.getVorname() != null)
                            vornameTextView.setText(oldName.getVorname());
                        if (oldName.getNachname() != null)
                            nachnameTextView.setText(oldName.getNachname());
                        if (oldName.getPreis() != null)
                            preisTextView.setText(oldName.getPreis().toString());

                        if (oldName.getWohnflaeche() != null)
                            wohflaecheTextView.setText(oldName.getWohnflaeche().toString());
                        if (oldName.getAlter() != null)
                            alterTextView.setText(oldName.getAlter().toString());
                        if (oldName.getMitbewohner() != null)
                            mitbewohnerTextView.setText(oldName.getMitbewohner().toString());
                        if (oldName.getHobbys() != null)
                            hobbysTextView.setText(oldName.getHobbys());
                        if (oldName.getRaucher() != null) {
                            if (oldName.getRaucher() == true)
                                raucherTextView.setText("Ja");
                            else raucherTextView.setText("Nein");
                        }

                        if (oldName.getHaustiere() != null)
                            if (oldName.getHaustiere() == true)
                                haustierTextView.setText("Ja");
                            else haustierTextView.setText("Nein");

                        if (oldName.getOrt() != null)
                            ortTextView.setText(oldName.getOrt().toString());
                        if (oldName.getGeschlecht() != null)
                            geschlechtTextView.setText(oldName.getGeschlecht().toString());

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

    public void changeToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

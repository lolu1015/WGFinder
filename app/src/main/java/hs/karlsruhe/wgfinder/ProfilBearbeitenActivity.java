package hs.karlsruhe.wgfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilBearbeitenActivity extends AppCompatActivity {
    WGFinderRoomDatabase db;
    private Button speichernButton;
    private Button zurückButton;
    private EditText preisEditText,wohnflaecheEditText, mitbewohnerEditText, hobbysEditText, alterEditText, raucherEditText, haustiereEditText, ortEditText, geschlechtEditText;
    private SwitchCompat raucherEditSwitch, haustiereEditSwitch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_bearbeiten);
        db = WGFinderRoomDatabase.getDatabase(this);
        setTitle("Dein Profil bearbeiten");



        preisEditText = findViewById(R.id.apb_preis);
        preisEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "Eingabe getätigt", Toast.LENGTH_SHORT).show();
            }
        });

        wohnflaecheEditText = findViewById(R.id.apb_wohnflaeche);
        wohnflaecheEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        mitbewohnerEditText = findViewById(R.id.apb_mitbewohner);
        mitbewohnerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        hobbysEditText = findViewById(R.id.apb_hobbys);
        hobbysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        alterEditText = findViewById(R.id.apb_alter);
        alterEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        raucherEditText = findViewById(R.id.apb_raucher);
        raucherEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });



        haustiereEditText = findViewById(R.id.apb_haustiere);
        haustiereEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        ortEditText = findViewById(R.id.apb_ort);
        ortEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        geschlechtEditText = findViewById(R.id.apb_geschlecht);
        geschlechtEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilBearbeitenActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

        //Hier wird Switch angesteuert
        raucherEditSwitch = findViewById(R.id.apb_raucher_switch);
        haustiereEditSwitch = findViewById(R.id.apb_haustiere_switch);

        zurückButton = findViewById(R.id.apb_zurück_button);
        zurückButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfilAnsehenActivity();
            }
        });


        speichernButton = findViewById(R.id.apb_speichern);
        speichernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonPressed();
            }
        });


 /*  // @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apb_speichern:
                onSaveButtonPressed();
                break;
            case R.id.apb_zurück_button:
           //     onZurueckButtonPressed();
                break;

        }
    }

   */

        //Werte, die schon in der DB sind, werden im Feld voher ausgefüllt.

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Benutzer oldName = db.benutzerDAO().getLastName();
                if (oldName.getPreis() != null)
                    preisEditText.setText(oldName.getPreis().toString());


                if (oldName.getRaucher())
                   raucherEditSwitch.setChecked(true);
                if (oldName.getHaustiere())
                    haustiereEditSwitch.setChecked(true);


            }
        });
    }



   public void onSaveButtonPressed(){


       AsyncTask.execute((new Runnable() {
           @Override
           public void run() {
               final Benutzer oldName = db.benutzerDAO().getLastName();

               if(TextUtils.isEmpty(preisEditText.getText()))   // Wenn in das Textfeld nichts eingetragen wurde ...
               oldName.setPreis(Integer.parseInt(oldName.getPreis().toString())); //dann wird der aktuelle Wert angenommen (TODO; auf Null prüfen)
               else oldName.setPreis(Integer.parseInt(preisEditText.getText().toString())); //wenn etwas eingetragen ist wird dies übernommen


               if(TextUtils.isEmpty(wohnflaecheEditText.getText()))
               oldName.setWohnflaeche(Integer.parseInt(oldName.getWohnflaeche().toString()));
               else oldName.setWohnflaeche(Integer.parseInt(wohnflaecheEditText.getText().toString()));


               if(TextUtils.isEmpty(mitbewohnerEditText.getText()))
               oldName.setMitbewohner(Integer.parseInt((oldName.getMitbewohner().toString())));
               else oldName.setMitbewohner(Integer.parseInt(mitbewohnerEditText.getText().toString()));


               if(TextUtils.isEmpty(hobbysEditText.getText()))
               oldName.setHobbys(oldName.getHobbys());
               else oldName.setHobbys(hobbysEditText.getText().toString());

               if(TextUtils.isEmpty(alterEditText.getText()))
               oldName.setAlter(Integer.parseInt(oldName.getAlter().toString()));
               else oldName.setAlter(Integer.parseInt(alterEditText.getText().toString()));



              // if(TextUtils.isEmpty(raucherEditText.getText()))
              //     oldName.setRaucher(oldName.getRaucher());
              // else oldName.setRaucher(raucherEditText.getText().toString());


                if (raucherEditSwitch.isChecked())
                    oldName.setRaucher(true);
                else oldName.setRaucher(false);

               if (haustiereEditSwitch.isChecked())
                   oldName.setHaustiere(true);
               else oldName.setHaustiere(false);

              // if(TextUtils.isEmpty(haustiereEditText.getText()))
              //     oldName.setHaustiere(oldName.getHaustiere());
              // else oldName.setHaustiere(haustiereEditText.getText().toString());



               if(TextUtils.isEmpty(ortEditText.getText()))
                   oldName.setOrt(oldName.getOrt());
               else oldName.setOrt(ortEditText.getText().toString());



               if(TextUtils.isEmpty(geschlechtEditText.getText()))
                   oldName.setGeschlecht(oldName.getGeschlecht());
               else oldName.setGeschlecht(geschlechtEditText.getText().toString());


               AsyncTask.execute(new Runnable() {
                   @Override
                   public void run() {
                       db.benutzerDAO().updateBenutzer(oldName);
                   }
               });
           }



       }));

       openProfilAnsehenActivity();
   }

    public void openProfilAnsehenActivity() {
        Intent intent = new Intent(this, ProfilAnsehenActivity.class);
        startActivity(intent);
    }
}

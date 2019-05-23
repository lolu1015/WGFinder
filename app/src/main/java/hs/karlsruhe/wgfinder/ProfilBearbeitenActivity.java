package hs.karlsruhe.wgfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilBearbeitenActivity extends AppCompatActivity {
    WGFinderRoomDatabase db;
    private Button speichernButton;
    private Button zurückButton;
    private EditText preisEditText,wohnflaecheEditText;

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
    }

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

   public void onSaveButtonPressed(){


       AsyncTask.execute((new Runnable() {
           @Override
           public void run() {
               final Benutzer oldName = db.benutzerDAO().getLastName();
               oldName.setPreis(Integer.parseInt(preisEditText.getText().toString()));
               oldName.setWohnflaeche(Integer.parseInt(wohnflaecheEditText.getText().toString()));
               AsyncTask.execute(new Runnable() {
                   @Override
                   public void run() {
                       db.benutzerDAO().updateBenutzer(oldName);
                   }
               });
           }



       }));
   }







       //Neuen Namen erstellen
     //  final Benutzer newName = db.benutzerDAO().getLastName();
       //DB Objekt mit Daten füttern
     //  newName.setVorname(alterEditText.getText().toString());

       //Namen mit Hilfe der entsprechenden DAO Schnittstelle in DB einfügen.
       //Alle Interaktionen mit der Datenbank müssen asynchron in einem eigenen
       //Thread ausgeführt werden, da sie sonst die Usablity stark beinträchtigen.
       //Room bestraft uns mit einem Crash, falls wir gegen diese Regel verstoßen.
     //  AsyncTask.execute(new Runnable() {
      //     @Override
      //     public void run() {
      //         db.benutzerDAO().updateBenutzer(newName);
      //     }
      // });
       //Neuen Namen erstellen
       //final Benutzer oldName = db.benutzerDAO().getLastName();
        //DB Objekt mit Daten füttern
        //oldName.setRolle(alterEditText.getText());   //hier toInt() machen
        //oldName.setVorname("wallah");
        //oldName.setNachname("wwallaaaa");
       //Toast.makeText(this, "Komme ich auf onSavedButton??", Toast.LENGTH_SHORT).show();
        //Namen mit Hilfe der entsprechenden DAO Schnittstelle in DB einfügen.
        //Alle Interaktionen mit der Datenbank müssen asynchron in einem eigenen
        //Thread ausgeführt werden, da sie sonst die Usablity stark beinträchtigen.
        //Room bestraft uns mit einem Crash, falls wir gegen diese Regel verstoßen.
      //   AsyncTask.execute(new Runnable() {

      //  @Override
      //   public void run() {
       //     Toast.makeText(ProfilBearbeitenActivity.this, "Jetzt sollte die Rolle geuppt werden", Toast.LENGTH_SHORT).show();
       //    db.benutzerDAO().insertBenutzer(oldName);
       //     }
       // });



    public void openProfilAnsehenActivity() {
        Intent intent = new Intent(this, ProfilAnsehenActivity.class);
        startActivity(intent);
    }
}

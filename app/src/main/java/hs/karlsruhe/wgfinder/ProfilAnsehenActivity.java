package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
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
    private TextView nameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGFinderRoomDatabase.getDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ansehen);
        setTitle("Dein Profil");

        nameTextView = findViewById(R.id.apa_beschreibung);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //zuletzt gespeicherten Namen aus Datenbank holen
                final Benutzer oldName = db.benutzerDAO().getLastName();

                //Lade den Namen in die TextView
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nameTextView.setText(oldName.getEmail());
                    }
                });
            }
        });



    }
}

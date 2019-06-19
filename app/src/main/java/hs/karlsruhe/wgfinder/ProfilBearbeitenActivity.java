package hs.karlsruhe.wgfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

public class ProfilBearbeitenActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    WGFinderRoomDatabase db;
    private Button speichernButton;
    private Button zurückButton;
    private EditText preisEditText, wohnflaecheEditText, mitbewohnerEditText, alterEditText, raucherEditText, haustiereEditText, ortEditText;
    private SwitchCompat raucherEditSwitch, haustiereEditSwitch;
    private String geschlecht,hobby;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profil_bearbeiten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_LogOut:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private TextView hobbysEditText, geschlechtEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_bearbeiten);
        db = WGFinderRoomDatabase.getDatabase(this);
        setTitle("Dein Profil bearbeiten");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            hobby = (String) bundle.get("hobby");
        }

        preisEditText = findViewById(R.id.apb_preis);
        preisEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        wohnflaecheEditText = findViewById(R.id.apb_wohnflaeche);
        wohnflaecheEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mitbewohnerEditText = findViewById(R.id.apb_mitbewohner);
        mitbewohnerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        hobbysEditText = findViewById(R.id.apb_hobbys);
        hobbysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHobbyCheckboxActivity();
            }
        });
     /*   hobbysEditText = findViewById(R.id.apb_hobbys);
        hobbysEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                openHobbyCheckboxActivity();
                closeKeyboard();
                return true;
            }
        }); */


        alterEditText = findViewById(R.id.apb_alter);
        alterEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*raucherEditText = findViewById(R.id.apb_raucher);
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
        */
        ortEditText = findViewById(R.id.apb_ort);
        ortEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Optionen für das Geschlecht als PopUp Context Menu
        geschlechtEditText = findViewById(R.id.apb_geschlecht);
        geschlechtEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(geschlechtEditText);
            }
        });
        //Optionen für das Geschlecht als PopUp Context Menu
     /*   geschlechtEditText = findViewById(R.id.apb_geschlecht);
        geschlechtEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showPopup(geschlechtEditText);
                closeKeyboard();
                return true;

            }
        }); */

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
                if (oldName.getWohnflaeche() != null)
                    wohnflaecheEditText.setText(oldName.getWohnflaeche().toString());
                if (oldName.getMitbewohner() != null)
                    mitbewohnerEditText.setText(oldName.getMitbewohner().toString());
                if (oldName.getAlter() != null)
                    alterEditText.setText(oldName.getAlter().toString());
                if (oldName.getRaucher() != null)
                    if (oldName.getRaucher())
                        raucherEditSwitch.setChecked(true);
                if (oldName.getHaustiere() != null)
                    if (oldName.getHaustiere())
                        haustiereEditSwitch.setChecked(true);
                if (oldName.getOrt() != null)
                    ortEditText.setText(oldName.getOrt().toString());
                if (oldName.getGeschlecht() != null)
                    geschlechtEditText.setText(oldName.getGeschlecht().toString());
                if (oldName.getHobbys() != null)
                    hobbysEditText.setText(oldName.getHobbys().toString());


            }
        });


        if (hobby != null)
            hobbysEditText.setText(hobby);

    }


 /*   @Override
    public void onResume() {
        if (hobby != null)
            hobbysEditText.setText(hobby);
        super.onResume();
    }  */

    //Damit die Tastatur nicht dauernd offen bleibt.
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    //Hier wird das PopUp angezeigt

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_geschlecht);
        popup.show();
    }

    //Hier wird geprüft auf welches Feld geklickt wird vom Menü
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:

                geschlecht = "M";
                geschlechtEditText.setText("M");

                return true;
            case R.id.option_2:

                geschlecht = "W";
                geschlechtEditText.setText("W");
                return true;
            default:
                return false;
        }
    }

    //Hier das Menü für Geschlecht createn

    // @Override
    // public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    //     super.onCreateContextMenu(menu, v, menuInfo);
    //     menu.setHeaderTitle("Geschlecht auswählen");
    //     getMenuInflater().inflate(R.menu.menu_geschlecht, menu);
    // }


    // Auf Feld Hobbys klicken um auf die View zu kommen mit den Checkboxen.

    public void openHobbyCheckboxActivity() {
        Intent intent = new Intent(this, ProfilHobbysActivity.class);
        startActivity(intent);
    }


    public void onSaveButtonPressed() {


        AsyncTask.execute((new Runnable() {
            @Override
            public void run() {
                final Benutzer oldName = db.benutzerDAO().getLastName();

                if (oldName.getPreis() != null) {
                    if (TextUtils.isEmpty(preisEditText.getText()))   // Wenn in das Textfeld nichts eingetragen wurde ...
                        oldName.setPreis(Integer.parseInt(oldName.getPreis().toString())); //dann wird der aktuelle Wert angenommen (TODO; auf Null prüfen)
                    else
                        oldName.setPreis(Integer.parseInt(preisEditText.getText().toString())); //wenn etwas eingetragen ist wird dies übernommen
                }
                if (oldName.getWohnflaeche() != null) {
                    if (TextUtils.isEmpty(wohnflaecheEditText.getText()))
                        oldName.setWohnflaeche(Integer.parseInt(oldName.getWohnflaeche().toString()));
                    else
                        oldName.setWohnflaeche(Integer.parseInt(wohnflaecheEditText.getText().toString()));
                }
                if (oldName.getMitbewohner() != null) {
                    if (TextUtils.isEmpty(mitbewohnerEditText.getText()))
                        oldName.setMitbewohner(Integer.parseInt((oldName.getMitbewohner().toString())));
                    else
                        oldName.setMitbewohner(Integer.parseInt(mitbewohnerEditText.getText().toString()));
                }

                if (oldName.getHobbys() != null) {
                    if (TextUtils.isEmpty(hobbysEditText.getText()))
                        oldName.setHobbys(oldName.getHobbys());
                    else oldName.setHobbys(hobbysEditText.getText().toString());
                }
                if (oldName.getAlter() != null) {
                    if (TextUtils.isEmpty(alterEditText.getText()))
                        oldName.setAlter(Integer.parseInt(oldName.getAlter().toString()));
                    else oldName.setAlter(Integer.parseInt(alterEditText.getText().toString()));
                }

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

                if (oldName.getOrt() != null) {
                    if (TextUtils.isEmpty(ortEditText.getText()))
                        oldName.setOrt(oldName.getOrt());
                    else oldName.setOrt(ortEditText.getText().toString());
                }


                if (geschlecht != null)
                        oldName.setGeschlecht(geschlecht);


                if (hobby != null)
                    oldName.setHobbys(hobby);

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

    public void changeToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

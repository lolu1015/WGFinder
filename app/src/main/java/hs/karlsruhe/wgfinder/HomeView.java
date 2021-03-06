package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.*;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import hs.karlsruhe.wgfinder.Entity.Wohnungen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeView extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {
    WGFinderRoomDatabase db;
    private AppCompatImageView anbieterBild;
    private AppCompatTextView preis, wohnflaeche, mitbewohner, ort, raucher, tiere, hobbies;
    private List<Wohnungen> wohnungen = null;
    GestureDetector gestureDetector;
    private Integer wohnungscounter;
    SharedPreferences sp;
    List<String> uebergabecounter = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view2);
        gestureDetector = new GestureDetector(HomeView.this, HomeView.this);
        sp = getSharedPreferences("Matches", 0);

        setTitle("Suche");
        db = WGFinderRoomDatabase.getDatabase(this);
        wohnungscounter = 0;

        anbieterBild = findViewById(R.id.ahv2_iv_bildwg);
        preis = findViewById(R.id.ahv2_tv_preis);
        wohnflaeche = findViewById(R.id.ahv2_tv_wohnflaeche);
        mitbewohner = findViewById(R.id.ahv2_tv_mitbewohner);
        ort = findViewById(R.id.ahv2_tv_ort);
        raucher = findViewById(R.id.ahv2_tv_raucher);
        tiere = findViewById(R.id.ahv2_tv_tiere);
        hobbies = findViewById(R.id.ahv2_tv_hobbies);


        initializeWohnungen();

        sp = getSharedPreferences("Matches",0);

    }

    private void showFirstWohnung() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                wohnungen = db.wohnungenDAO().findWohnung();
                System.out.println("Trollolololololo"+wohnungen.get(0).getOrt());
                nextWohnung(0);
            }
        });
    }

    private void nextWohnung(Integer counter) {
        System.out.println("Trollolololololo nextWohnung");
        //hier fehlt noch das bild
        if(!(wohnungen == null) && !(wohnungen.size() <= counter)) {
            if(counter == 0) {
                anbieterBild.setImageResource(R.drawable.wohnung1);
            }
            if(counter == 1) {
                anbieterBild.setImageResource(R.drawable.wohnung2);
            }
            if(counter == 2) {
                anbieterBild.setImageResource(R.drawable.wohnung3);
            }
        preis.setText(wohnungen.get(counter).getPreis().toString());
        wohnflaeche.setText(wohnungen.get(counter).getWohnflaeche().toString());
        mitbewohner.setText(wohnungen.get(counter).getMitbewohnerAnzahl().toString());
        ort.setText(wohnungen.get(counter).getOrt());
        if(wohnungen.get(counter).getRaucher()) {
            raucher.setText("Ja");
        } else {raucher.setText("Nein");}
        if(wohnungen.get(counter).getTiere()) {
            tiere.setText("Ja");
        } else {tiere.setText("Nein");}
        hobbies.setText(wohnungen.get(counter).getHobbies());

        } else {
            //Toast.makeText(HomeView.this, "keine Wohnungen", Toast.LENGTH_LONG).show();
            preis.setText("leer");
            wohnflaeche.setText("leer");
            mitbewohner.setText("leer");
            ort.setText("leer");
            raucher.setText("leer");
            tiere.setText("leer");
            hobbies.setText("leer");
            anbieterBild.setImageResource(R.drawable.haus);
        }

    }

    private void initializeWohnungen() {

        final Wohnungen wohnung1 = new Wohnungen();
        wohnung1.setId(1);
        wohnung1.setPreis(30.00);
        wohnung1.setWohnflaeche(12.33);
        wohnung1.setTiere(true);
        wohnung1.setRaucher(true);
        wohnung1.setOrt("Karlsruhe");
        wohnung1.setMitbewohnerAnzahl(2);
        wohnung1.setHobbies("Feiern");
        wohnung1.setBeschreibung("Wir als feierfreudige WG suchen einen neuen Mitbewohner, der gerne feiert.");
        final Wohnungen wohnung2 = new Wohnungen();
        wohnung2.setId(2);
        wohnung2.setPreis(345.00);
        wohnung2.setWohnflaeche(32.23);
        wohnung2.setTiere(false);
        wohnung2.setRaucher(true);
        wohnung2.setOrt("Karlsruhe");
        wohnung2.setMitbewohnerAnzahl(5);
        wohnung2.setHobbies("Tauchen");
        wohnung2.setBeschreibung("Wir sind Studenten am KIT und sind alle in der Tauchergruppe und suchen einen neuen Mitbewohner, der dieses Hobby mit uns teilt");
        final Wohnungen wohnung3 = new Wohnungen();
        wohnung3.setId(3);
        wohnung3.setPreis(3000.00);
        wohnung3.setWohnflaeche(140.79);
        wohnung3.setTiere(true);
        wohnung3.setRaucher(false);
        wohnung3.setOrt("Karlsruhe");
        wohnung3.setMitbewohnerAnzahl(1);
        wohnung3.setHobbies("Sport");
        wohnung3.setBeschreibung("Wir sind eine Luxus-WG und suchen einen Mitbewohner, der gerne Sport treibt und sich gerne zu einem teuern Whisky am Abend zu uns gesellt. ");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.wohnungenDAO().deleteWohnungen();
                db.wohnungenDAO().insertWohnung(wohnung1);
                db.wohnungenDAO().insertWohnung(wohnung2);
                db.wohnungenDAO().insertWohnung(wohnung3);
                //hier kommt die Wohnung in die DB ist getestet
                showFirstWohnung();
            }
        });
    }

    //#######################
    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {


        if(motionEvent1.getX() - motionEvent2.getX() > 50){
            Toast.makeText(HomeView.this , " Gefällt mir " , Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sp.edit();
            Set<String> set = new HashSet<String>();
            Integer übergabe = wohnungscounter + 1;
            if (!uebergabecounter.contains(übergabe)) {
                uebergabecounter.add(übergabe.toString());
            }
            set.addAll(uebergabecounter);
            editor.putStringSet("ID",set);
            editor.commit();
            //setList(wohnung.getcounter); Hier seette ich die Wohnung, die in der View angezeigt werden soll.
            wohnungscounter += 1;
            nextWohnung(wohnungscounter);


            return true;
        }

        if(motionEvent2.getX() - motionEvent1.getX() > 50) {

            Toast.makeText(HomeView.this, " Gefällt mir nicht ", Toast.LENGTH_LONG).show();
            Integer übergabe = wohnungscounter + 1;
            if (uebergabecounter.contains(übergabe)) {
                Set<String> set = new HashSet<String>();
                SharedPreferences.Editor editor = sp.edit();
                uebergabecounter.remove(übergabe.toString());
                set.addAll(uebergabecounter);
                editor.putStringSet("ID",set);
                editor.commit();
            }
            wohnungscounter += 1;
            nextWohnung(wohnungscounter);

            return true;
        }
        else {

            return true ;
        }
    }

    @Override
    public void onLongPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }



//###########################
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_LogOut:
                logOut();
                return true;
            case R.id.action_ProfilAnsehen:
                changeViewProfilBearbeiten();
                return true;
            case R.id.action_matches:
                changeViewMatchesActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeViewMatchesActivity() {
        Intent intent = new Intent(this,MatchesActivity.class);
        startActivity(intent);
    }

    private void changeViewProfilBearbeiten() {
        Intent intent = new Intent(this,ProfilAnsehenActivity.class);
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
    public void changeToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.ace_b_Abbrechen:

                break;

            case R.id.ace_b_AccountErstellen:

                break;

        }*/
    }
}

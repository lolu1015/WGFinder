package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import hs.karlsruhe.wgfinder.Entity.Wohnungen;

import java.util.List;

public class HomeView extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {
    WGFinderRoomDatabase db;
    private AppCompatImageView anbieterBild;
    private AppCompatTextView preis, wohnflaeche, mitbewohner, ort, raucher, tiere;
    private List<Wohnungen> wohnungen = null;
    GestureDetector gestureDetector;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        gestureDetector = new GestureDetector(HomeView.this, HomeView.this);

        setTitle("Suche");
        db = WGFinderRoomDatabase.getDatabase(this);

        anbieterBild = findViewById(R.id.ahv_iv_bildwg);
        preis = findViewById(R.id.ahv_tv_preis);
        wohnflaeche = findViewById(R.id.ahv_tv_wohnflaeche);
        mitbewohner = findViewById(R.id.ahv_tv_mitbewohner);
        ort = findViewById(R.id.ahv_tv_ort);
        raucher = findViewById(R.id.ahv_tv_raucher);
        tiere = findViewById(R.id.ahv_tv_raucher);


        anbieterBild.setImageResource(R.drawable.wohnung1);

        initializeWohnungen();

    }

    private void showFirstWohnung() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                wohnungen = db.wohnungenDAO().findWohnung();
                System.out.println("Trollolololololo"+wohnungen.get(0).getOrt());
            }
        });
        nextWohnung(0);



    }

    private void nextWohnung(Integer counter) {
        System.out.println("Trollolololololo nextWohnung");
        //hier fehlt noch das bild
        if(wohnungen.size() > 0 ) {
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

        } else {
            Toast.makeText(HomeView.this, "keine Wohnungen", Toast.LENGTH_LONG).show();
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.wohnungenDAO().deleteWohnungen();
                db.wohnungenDAO().insertWohnung(wohnung1);
                //hier kommt die Wohnung in die DB ist getestet
            }
        });
        showFirstWohnung();
    }

    //#######################
    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if(motionEvent1.getY() - motionEvent2.getY() > 50){

            Toast.makeText(HomeView .this , " Swipe Up " , Toast.LENGTH_LONG).show();

            return true;
        }

        if(motionEvent2.getY() - motionEvent1.getY() > 50){

            Toast.makeText(HomeView.this , " Swipe Down " , Toast.LENGTH_LONG).show();

            return true;
        }

        if(motionEvent1.getX() - motionEvent2.getX() > 50){

            Toast.makeText(HomeView.this , " Gefällt mir nicht " , Toast.LENGTH_LONG).show();


            return true;
        }

        if(motionEvent2.getX() - motionEvent1.getX() > 50) {

            Toast.makeText(HomeView.this, " Gefällt mir ", Toast.LENGTH_LONG).show();

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
            default:
                return super.onOptionsItemSelected(item);
        }
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

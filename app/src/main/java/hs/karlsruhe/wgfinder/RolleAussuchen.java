package hs.karlsruhe.wgfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;

public class RolleAussuchen extends AppCompatActivity implements View.OnClickListener{
    private AppCompatButton anbieterButton, sucherButton;
    WGFinderRoomDatabase db;
    private String emailTemp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolle_aussuchen);
        setTitle("Rolle aussuchen");

        anbieterButton = findViewById(R.id.ara_b_Anbieter);
        sucherButton = findViewById(R.id.ara_b_Sucher);


        anbieterButton.setOnClickListener(this);
        sucherButton.setOnClickListener(this);

        db = WGFinderRoomDatabase.getDatabase(this);
    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ara_b_Anbieter:
                setRolleAnbieter(v);
                break;
            case R.id.ara_b_Sucher:
                setRolleSucher(v);
                break;

        }
    }
    public void setRolleAnbieter(View v) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                emailTemp = db.tempDAO().findTemp();
                    db.benutzerDAO().updateRole(0, emailTemp);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RolleAussuchen.this, "Anbieter!", Toast.LENGTH_LONG).show();
                        }
                    });
                sendMessage();
                }
        });
    }
    public void setRolleSucher(View v) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RolleAussuchen.this, "SucherFunction!", Toast.LENGTH_LONG).show();
                    }
                });
                emailTemp = db.tempDAO().findTemp();
                db.benutzerDAO().updateRole(1, emailTemp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RolleAussuchen.this, "Sucher!" + emailTemp, Toast.LENGTH_LONG).show();
                    }
                });
                sendMessage();
            }
    });
    }

    public void sendMessage()
    {
        Intent intent = new Intent(this,ProfilAnsehenActivity.class);
        startActivity(intent);
    }
    public void changeToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

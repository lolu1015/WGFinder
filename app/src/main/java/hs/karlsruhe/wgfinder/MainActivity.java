package hs.karlsruhe.wgfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("LOGIN WG-Finder");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_beenden) {

            // Hier eine Funktion aufrufen, die beim Klick auf "Beenden" ausgeführt werden soll.

            return true;
        }

        if (id == R.id.action_info) {

            // Hier eine Funktion aufrufen, die beim Klick auf "Informationen" ausgeführt werden soll.

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this,Account_erstellen.class);
        startActivity(intent);
    }

}

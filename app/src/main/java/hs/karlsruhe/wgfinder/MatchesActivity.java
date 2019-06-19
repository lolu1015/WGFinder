package hs.karlsruhe.wgfinder;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import hs.karlsruhe.wgfinder.DAOs.WohnungenDAO;
import hs.karlsruhe.wgfinder.Entity.Wohnungen;

public class MatchesActivity extends AppCompatActivity {
    WGFinderRoomDatabase db;
    ListView matchesListView;
    SharedPreferences sp;
    List<String> title = new ArrayList<>();
    List<String> description = new ArrayList<>();
    ArrayList<Integer> pictures = new ArrayList<>();

    String[] from = {"image","title","description"};
    int[] to = {R.id.lim_image,R.id.lim_title,R.id.lim_description};

    List<HashMap<String,String>> matchList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_activity);
        setTitle("Deine Matches");
        db = WGFinderRoomDatabase.getDatabase(this);
        sp = getSharedPreferences("Matches",0);
        final Set<String> set = sp.getStringSet("ID",null);

        if (set.iterator() != null) {
           for(final String s: set) {

               AsyncTask.execute(new Runnable() {
                   @Override
                   public void run() {
                       Wohnungen wohnung = db.wohnungenDAO().findWohnungById(Integer.parseInt(s)); //Integer.parseInt(set.iterator().next())
                       title.add(wohnung.getOrt());
                       description.add(wohnung.getPreis().toString());
                       pictures.add(R.drawable.wohnung1);
                   }
               });




            }
        }






        for(int i =0;i<title.size();i++) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("image",Integer.toString(pictures.get(i)));
            hashMap.put("title",title.get(i));
            hashMap.put("description",description.get(i));
            matchList.add(hashMap);
        }



        matchesListView = (ListView)findViewById(R.id.ama_matches_liste);
        SimpleAdapter simpleAdapter = new SimpleAdapter(MatchesActivity.this, matchList, R.layout.list_item_matches,from,to);
        matchesListView.setAdapter(simpleAdapter);
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
                changeToMain();
            }
        });
    }
    public void changeToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}

package hs.karlsruhe.wgfinder;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilHobbysActivity extends AppCompatActivity {

    private CheckBox feiern, tauchen, sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_hobbys);

        feiern = findViewById(R.id.checkbox_feiern);
        tauchen = findViewById(R.id.checkbox_tauchen);
        sport = findViewById(R.id.checkbox_sport);



    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_feiern:
                if (checked)

                changeToBearbeiten(feiern.getText().toString());
            else

                    Toast.makeText(this, "Es ist nicht gecheked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_tauchen:
                if (checked)
                    changeToBearbeiten(tauchen.getText().toString());
            else

                    Toast.makeText(this, "Es ist nicht gecheked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_sport:
                if (checked)
                    changeToBearbeiten(sport.getText().toString());
                else

                    Toast.makeText(this, "Es ist nicht gecheked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changeToBearbeiten(String text) {
        Intent intent = new Intent(this, ProfilBearbeitenActivity.class);
        intent.putExtra("hobby", text);
        startActivity(intent);
    }
}

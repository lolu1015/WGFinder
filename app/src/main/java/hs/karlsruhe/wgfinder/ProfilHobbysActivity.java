package hs.karlsruhe.wgfinder;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilHobbysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_hobbys);




    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_meat:
                if (checked)
                // Put some meat on the sandwich
                    Toast.makeText(this, "Es ist gecheked", Toast.LENGTH_SHORT).show();
            else
                // Remove the meat
                    Toast.makeText(this, "Es ist nicht gecheked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_cheese:
                if (checked)
                    Toast.makeText(this, "Es ist gecheked", Toast.LENGTH_SHORT).show();
            else
                // I'm lactose intolerant
                    Toast.makeText(this, "Es ist nicht gecheked", Toast.LENGTH_SHORT).show();
                break;
            // TODO: Veggie sandwich
        }
    }
}

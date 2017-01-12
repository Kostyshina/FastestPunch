package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainSettingsActivity extends AppCompatActivity {

    ImageButton mTopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

       /* gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainSettingsActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

       /* mTopButton = (ImageButton) findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               *//* if(mDrawer.getDrawerState() == 1) {
                    mDrawer.closeMenu();
                } else {
                    mDrawer.openMenu();
                    startActivity(MainSettingsActivity.class);
                }*//*
            }
        });*/
    }
}

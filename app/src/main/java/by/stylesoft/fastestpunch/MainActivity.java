package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerMenu;
    private ImageButton mainSettingsButton;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter mAdapter;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //содержимое Activity из layout-файла

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mDrawerMenu = (ListView) findViewById(R.id.mainSettings);
       // mDrawerMenu.setAdapter(new ImageAdapter(this));
        /*mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        mainSettingsButton = (ImageButton) findViewById(R.id.mainSettingsButton);
        //final TextView punchType = (TextView) findViewById(R.id.punchType);

        mainSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                //startActivity(v);
            }
        });

    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();

    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            startLicenseActivity();
        }
        super.onResume();
    }

    public void startActivity(View v){
        Intent intent = new Intent(this, MainSettingsActivity.class);
        startActivity(intent);
    }

    public void startLicenseActivity(){
        Intent intent = new Intent(this, LicenseActivity.class);
        startActivity(intent);
    }
}

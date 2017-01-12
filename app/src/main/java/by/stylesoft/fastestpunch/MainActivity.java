package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

public class MainActivity extends AppCompatActivity {

    //private ListView mDrawerMenu;
    private ImageButton mainSettingsButton;
    private ImageButton mTopButton;
    private BottomSheetBehavior bottomSheetBehavior;
    //private DrawerLayout mDrawerLayout;
    private MenuDrawer mDrawer;
    private ArrayAdapter mAdapter;
    private CharSequence mTitle;
    private class MainSettingsActivity{
        private ImageButton mTopButton;

        public MainSettingsActivity(){


        }
        public void closeMenuDrawer(){
            mDrawer.closeMenu();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//содержимое Activity из layout-файла

        RelativeLayout bottomSheetLayout = (RelativeLayout) findViewById(R.id.activity_main_settings);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        mainSettingsButton = (ImageButton) findViewById(R.id.mainSettingsButton);
        mainSettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //mDrawer.openMenu();
            }
        });

        mTopButton = (ImageButton) findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //final MainSettingsActivity mSettingsActivity = new MainSettingsActivity();
        /*mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.TOP);
        mDrawer.setContentView(R.layout.activity_main);

        mainSettingsButton = (ImageButton) findViewById(R.id.mainSettingsButton);
        mainSettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mDrawer.openMenu();
            }
        });

        setTheme(R.style.MainSettingsTheme);
        mDrawer.setMenuView(R.layout.activity_main_settings);
        mDrawer.setMenuSize(1000);
        GridView gridview = (GridView) findViewById(R.id.gridMenuImages);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }

        });
        mTopButton = (ImageButton) findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mSettingsActivity.closeMenuDrawer();
            }
        });

        mDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);*/
        /*
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerMenu = (ListView) findViewById(R.id.mainSettings);
        */
       // mDrawerMenu.setAdapter(new ImageAdapter(this));
        /*mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
/*
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
*/
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            startActivity(LicenseActivity.class);
        }
        super.onStart();

    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}

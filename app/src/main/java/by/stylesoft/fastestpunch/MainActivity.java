package by.stylesoft.fastestpunch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.copyright_menu, menu);
        return true;
    }
    public void onComposeAction(MenuItem mi) {
        // handle click here
        startActivity(LicenseActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//содержимое Activity из layout-файла

        /*ActionBar actionBar = getSupportActionBar();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View copyrightView = layoutInflater.inflate(R.layout.copyright_view, null);
        TextView title = (TextView)copyrightView.findViewById(R.id.actionBarTitleView);
        title.setText(getResources().getString(R.string.app_name));

        actionBar.setCustomView(copyrightView);*/

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

        int i;
        int j;
        TableLayout imageTable = (TableLayout) findViewById(R.id.imageTable);
        int countRow = imageTable.getChildCount();
        for(i = 0; i < countRow; i++) {
            View v = imageTable.getChildAt(i);
            if (v instanceof TableRow) {
                TableRow row = (TableRow) v;
                int countCell = row.getChildCount();
                for (j = 0; j < countCell; j++) {
                    View v2 = row.getChildAt(j);
                    if (v2 instanceof ToggleButton) {
                        ToggleButton b = (ToggleButton) v2;
                        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                /*if(getResources().getDrawable(R.drawable.main_settings_item_inactive).getConstantState().equals(
                                        v.getBackground().getConstantState())) {
                                    v.setSelected(true);
                                }else {
                                    v.setSelected(false);
                                }*/
                                /*int states[] = b.getDrawableState();
                                for(int state : states) {

                                    if (state == android.R.attr.state_selected){

                                    }
                                }*/
                            }
                        });
                    }
                }
            }
        }

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

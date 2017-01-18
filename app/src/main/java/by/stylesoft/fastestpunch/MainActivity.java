package by.stylesoft.fastestpunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

public class MainActivity extends AppCompatActivity {
    private String hand;
    private String gloves;
    private String moves;
    private String glovesWeight;
    private String punchType;

    //private ListView mDrawerMenu;
    //private View contentViewParam;
    //private Dialog mBottomSheetDialog;
    private MainSettings ms;
    private Button punchButton;
    private ImageButton mainSettingsButton;
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
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_main_settings), Context.MODE_PRIVATE);
        //prefs.edit().clear().apply();
        hand = prefs.getString(getString(R.string.hand_item), "ic_right_hand_black");
        boolean handLeft = prefs.getBoolean(getString(R.string.hand_left_check_item), false);
        boolean handRight = prefs.getBoolean(getString(R.string.hand_right_check_item), true);
        gloves = prefs.getString(getString(R.string.gloves_item), "ic_gloves_off_black");
        boolean glovesOn = prefs.getBoolean(getString(R.string.gloves_on_check_item), false);
        boolean glovesOff = prefs.getBoolean(getString(R.string.gloves_off_check_item), true);
        moves = prefs.getString(getString(R.string.moves_item), "ic_punch_with_space_black");
        boolean onStep = prefs.getBoolean(getString(R.string.on_step_check_item), false);
        boolean withSpace = prefs.getBoolean(getString(R.string.with_space_check_item), true);
        glovesWeight = prefs.getString(getString(R.string.gloves_weight_item), "");
        punchType = prefs.getString(getString(R.string.punch_type_item), getResources().getStringArray(R.array.punch_type_array)[0]);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(getString(R.string.hand_item), hand);
        edit.putBoolean(getString(R.string.hand_left_check_item), handLeft);
        edit.putBoolean(getString(R.string.hand_right_check_item), handRight);
        edit.putString(getString(R.string.gloves_item), gloves);
        edit.putBoolean(getString(R.string.gloves_on_check_item), glovesOn);
        edit.putBoolean(getString(R.string.gloves_off_check_item), glovesOff);
        edit.putString(getString(R.string.moves_item), moves);
        edit.putBoolean(getString(R.string.on_step_check_item), onStep);
        edit.putBoolean(getString(R.string.with_space_check_item), withSpace);
        edit.putString(getString(R.string.gloves_weight_item), glovesWeight);
        edit.putString(getString(R.string.punch_type_item), punchType);
        edit.apply();

        init();

        View contentView = getLayoutInflater().inflate(R.layout.activity_main_settings, null);

        ms = new MainSettings(this, contentView, getString(R.string.pref_main_settings));

        //initBottomSheet();

        /*ActionBar actionBar = getSupportActionBar();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View copyrightView = layoutInflater.inflate(R.layout.copyright_view, null);
        TextView title = (TextView)copyrightView.findViewById(R.id.actionBarTitleView);
        title.setText(getResources().getString(R.string.app_name));

        actionBar.setCustomView(copyrightView);*/

        //RelativeLayout bottomSheetLayout = (RelativeLayout) findViewById(R.id.activity_main_settings);

 /*       bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);*/



        mainSettingsButton = (ImageButton) findViewById(R.id.mainSettingsButton);
        mainSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.initBottomSheet();
                ms.bottomSheetBehavior();
                ms.show();
                //bottomSheetBehavior();
                //mBottomSheetDialog.show();
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //mDrawer.openMenu();
            }
        });

        punchButton = (Button) findViewById(R.id.punchButton);
        punchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call sensor and record results
                float punchSpeed = (float) Math.random()*getResources().getInteger(R.integer.punch_speed_max)/100;
                float reactionSpeed = (float) Math.random()*getResources().getInteger(R.integer.reaction_speed_max)/100;
                float acceleration = (float) Math.random()*getResources().getInteger(R.integer.acceleration_max)/100;
                SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_persistent_storage), Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putFloat(getString(R.string.punch_speed_result), punchSpeed);
                edit.putFloat(getString(R.string.reaction_speed_result), reactionSpeed);
                edit.putFloat(getString(R.string.acceleration_result), acceleration);
                edit.apply();
                startActivity(PunchButtonActivity.class);
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

        mTopButton = (ImageButton) findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mSettingsActivity.closeMenuDrawer();
            }
        });


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

    private void init(){
        ((TextView) findViewById(R.id.punchTypeView)).setText(punchType);
        ((TextView) findViewById(R.id.handView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(MainActivity.this,
                        getResources().getIdentifier(hand, "drawable", getPackageName())), null, null);
        ((TextView) findViewById(R.id.glovesView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(MainActivity.this,
                        getResources().getIdentifier(gloves, "drawable", getPackageName())), null, null);
        ((TextView) findViewById(R.id.glovesView)).setText(glovesWeight);
        ((TextView) findViewById(R.id.movesView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(MainActivity.this,
                        getResources().getIdentifier(moves, "drawable", getPackageName())), null, null);
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            startActivity(LicenseActivity.class);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.apply();
        }
        super.onStart();
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    public void startActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void onToggle(View view) {
        ms.onToggle(view);
    }


    private Bitmap changeBitmapColor(int id){
        Drawable sourceDrawable = getResources().getDrawable(id);
        Bitmap sourceBitmap = DrawableBitmap.drawableToBitmap(sourceDrawable);
        return DrawableBitmap.changeImageColor(sourceBitmap,
                getResources().getColor(R.color.colorGreyDark));
    }
}

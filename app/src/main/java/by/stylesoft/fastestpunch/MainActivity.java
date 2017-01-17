package by.stylesoft.fastestpunch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

public class MainActivity extends AppCompatActivity {

    //private ListView mDrawerMenu;
    private View contentViewParam;
    private Dialog mBottomSheetDialog;

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
        contentViewParam = getLayoutInflater().inflate(R.layout.activity_main_settings, null);
        initBottomSheet();

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
                bottomSheetBehavior();
                //mBottomSheetDialog.show();
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //mDrawer.openMenu();
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

    public void initBottomSheet(){
        ImageButton mTopButton = (ImageButton) contentViewParam.findViewById(R.id.menuTopButton);
        mBottomSheetDialog = new Dialog(MainActivity.this, R.style.MainSettingsTheme);
        mBottomSheetDialog.setContentView(contentViewParam);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);

        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        Button[] buttons = new Button[3];
        buttons[0] = (Button) contentViewParam.findViewById(R.id.leftHandButton);
        buttons[1] = (Button) contentViewParam.findViewById(R.id.glovesOnButton);
        buttons[2] = (Button) contentViewParam.findViewById(R.id.punchOnStepButton);

        for(int j = 0; j < 3; j++) {
            Drawable drawable = buttons[j].getCompoundDrawables()[1];
            int color = getResources().getColor(R.color.colorGreyDark);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            drawable = DrawableCompat.wrap(drawable);
            buttons[j].setCompoundDrawables(null, drawable, null, null);
        }
    }

    public void bottomSheetBehavior(){
        View contentView = contentViewParam;
        //ImageButton mTopButton = (ImageButton) contentView.findViewById(R.id.menuTopButton);
        final Spinner spinner = (Spinner) contentView.findViewById(R.id.punchTypeSpinner);
        //Button[] buttons = new Button[3];

        mBottomSheetDialog.show();
        //contentViewParam = contentView;

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.punch_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) findViewById(R.id.punchTypeView);
                textView.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText editText = (EditText) contentViewParam.findViewById(R.id.glovesWeight);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (contentViewParam.findViewById(R.id.glovesWeightForm).getVisibility() == View.VISIBLE) {
                    TextView textView = (TextView) findViewById(R.id.glovesView);
                    textView.setText(s);
                }
            }
        });


        final TableLayout imageTable = (TableLayout) contentView.findViewById(R.id.imageTable);
        int countRow = imageTable.getChildCount();
        for (int i = 0; i < countRow; i++) {
            View v = imageTable.getChildAt(i);
            if (v instanceof RadioGroup) {
                RadioGroup group = (RadioGroup) v;
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int index = imageTable.indexOfChild(group);
                        for (int j = 0; j < group.getChildCount(); j++) {
                            final ToggleButton toggleButton = (ToggleButton) group.getChildAt(j);
                            toggleButton.setChecked(toggleButton.getId() == checkedId);
                            Drawable drawable = toggleButton.getCompoundDrawables()[1];
                            int color = getResources().getColor(R.color.colorGreyDark);
                            if(toggleButton.getId() != checkedId){
                                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                            } else {
                                drawable.clearColorFilter();
                                TextView textView = new TextView(getApplicationContext());
                                switch (index) {
                                    case 0:
                                        textView = (TextView) findViewById(R.id.handView);
                                        break;
                                    case 1:
                                        textView = (TextView) findViewById(R.id.glovesView);
                                        break;
                                    case 2:
                                        textView = (TextView) findViewById(R.id.movesView);
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(), "No view found for " + index,
                                                Toast.LENGTH_SHORT).show();
                                }
                                textView.setCompoundDrawables(null, toggleButton.getCompoundDrawables()[1], null, null);
                            }
                            /*ImageSpan imageSpan = new ImageSpan(drawable);
                            SpannableString content = new SpannableString(toggleButton.getText());
                            content.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            toggleButton.setCompoundDrawables(null,null,null,null);
                            toggleButton.setText(content);
                            toggleButton.setTextOff(content);
                            toggleButton.setTextOn(content);*/
                            toggleButton.setCompoundDrawables(null, drawable, null, null);
                        }
                    }
                });
                /*int countCell = row.getChildCount();
                for (j = 0; j < countCell; j++) {
                    View v2 = row.getChildAt(j);
                    if (v2 instanceof ToggleButton) {
                        ToggleButton b = (ToggleButton) v2;
                        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                *//*if(getResources().getDrawable(R.drawable.main_settings_item_inactive).getConstantState().equals(
                                        v.getBackground().getConstantState())) {
                                    v.setSelected(true);
                                }else {
                                    v.setSelected(false);
                                }*//*
                                *//*int states[] = b.getDrawableState();
                                for(int state : states) {

                                    if (state == android.R.attr.state_selected){

                                    }
                                }*//*
                            }
                        });
                    }
                 }*/
            }
        }
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(0);
        ((RadioGroup)view.getParent()).check(view.getId());
        LinearLayout linearLayoutGloves = (LinearLayout) contentViewParam.findViewById(R.id.glovesWeightForm);
        if(view.getId() == R.id.glovesOnButton){
            linearLayoutGloves.setVisibility(View.VISIBLE);
        } else if(view.getId() == R.id.glovesOffButton){
            ((EditText) contentViewParam.findViewById(R.id.glovesWeight)).setText("");
            linearLayoutGloves.setVisibility(View.GONE);
        }
    }


    private Bitmap changeBitmapColor(int id){
        Drawable sourceDrawable = getResources().getDrawable(id);
        Bitmap sourceBitmap = DrawableBitmap.drawableToBitmap(sourceDrawable);
        return DrawableBitmap.changeImageColor(sourceBitmap,
                getResources().getColor(R.color.colorGreyDark));
    }
}

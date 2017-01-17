package by.stylesoft.fastestpunch;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by User on 17.01.2017.
 */

public class MainSettings {
    private View contentView;
    private Dialog mBottomSheetDialog;
    private Context context;
    private SharedPreferences prefs;
    private String file;

    private String hand;
    private String gloves;
    private String moves;
    private boolean [] checked;
    private String glovesWeight;
    private String punchType;

    MainSettings(final Context context, View view, String file){
        this.context = context;
        contentView = view;
        this.file = file;
        checked = new boolean[6];
        mBottomSheetDialog = new Dialog(this.context, R.style.MainSettingsTheme);
        mBottomSheetDialog.setContentView(contentView);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                //Activity activity = mBottomSheetDialog.getOwnerActivity();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public void commitChanges(){
        prefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(context.getString(R.string.hand_item), hand);
        edit.putBoolean(context.getString(R.string.hand_left_check_item), checked[0]);
        edit.putBoolean(context.getString(R.string.hand_right_check_item), checked[1]);
        edit.putString(context.getString(R.string.gloves_item), gloves);
        edit.putBoolean(context.getString(R.string.gloves_on_check_item), checked[2]);
        edit.putBoolean(context.getString(R.string.gloves_off_check_item), checked[3]);
        edit.putString(context.getString(R.string.moves_item), moves);
        edit.putBoolean(context.getString(R.string.on_step_check_item), checked[4]);
        edit.putBoolean(context.getString(R.string.with_space_check_item), checked[5]);
        edit.putString(context.getString(R.string.gloves_weight_item), glovesWeight);
        edit.putString(context.getString(R.string.punch_type_item), punchType);
        edit.apply();
    }

    public void initBottomSheet(){
        prefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        hand = prefs.getString(context.getString(R.string.hand_item), "ic_right_hand_black");
        checked[0] = prefs.getBoolean(context.getString(R.string.hand_left_check_item), false);
        checked[1] = prefs.getBoolean(context.getString(R.string.hand_right_check_item), true);
        gloves = prefs.getString(context.getString(R.string.gloves_item), "ic_gloves_off_black");
        checked[2] = prefs.getBoolean(context.getString(R.string.gloves_on_check_item), false);
        checked[3] = prefs.getBoolean(context.getString(R.string.gloves_off_check_item), true);
        moves = prefs.getString(context.getString(R.string.moves_item), "ic_punch_with_space_black");
        checked[4] = prefs.getBoolean(context.getString(R.string.on_step_check_item), false);
        checked[5] = prefs.getBoolean(context.getString(R.string.with_space_check_item), true);
        glovesWeight = prefs.getString(context.getString(R.string.gloves_weight_item), "");
        punchType = prefs.getString(context.getString(R.string.punch_type_item), "");


        ToggleButton[] buttons = new ToggleButton[6];
        buttons[0] = (ToggleButton) contentView.findViewById(R.id.leftHandButton);
        buttons[1] = (ToggleButton) contentView.findViewById(R.id.rightHandButton);
        buttons[2] = (ToggleButton) contentView.findViewById(R.id.glovesOnButton);
        buttons[3] = (ToggleButton) contentView.findViewById(R.id.glovesOffButton);
        buttons[4] = (ToggleButton) contentView.findViewById(R.id.punchOnStepButton);
        buttons[5] = (ToggleButton) contentView.findViewById(R.id.punchWithSpaceButton);
        for(int i=0; i<6; i++){
            buttons[i].setChecked(checked[i]);
        }
        for(ToggleButton button : buttons) {
            if(!button.isChecked()){
                Drawable drawable = button.getCompoundDrawables()[1];
                int color = ContextCompat.getColor(context, R.color.colorGreyDark);
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                drawable = DrawableCompat.wrap(drawable);
                button.setCompoundDrawables(null, drawable, null, null);
            }
        }

        LinearLayout linearLayoutGloves = (LinearLayout) contentView.findViewById(R.id.glovesWeightForm);
        if(buttons[2].isChecked()){
            linearLayoutGloves.setVisibility(View.VISIBLE);
        } else {
            ((EditText) contentView.findViewById(R.id.glovesWeight)).setText("");
            linearLayoutGloves.setVisibility(View.GONE);
        }
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(0);
        ((RadioGroup)view.getParent()).check(view.getId());
        LinearLayout linearLayoutGloves = (LinearLayout) contentView.findViewById(R.id.glovesWeightForm);
        if(view.getId() == R.id.glovesOnButton){
            linearLayoutGloves.setVisibility(View.VISIBLE);
        } else if(view.getId() == R.id.glovesOffButton){
            ((EditText) contentView.findViewById(R.id.glovesWeight)).setText("");
            linearLayoutGloves.setVisibility(View.GONE);
        }
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0; i<spinner.getCount();  i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }


    public void bottomSheetBehavior(){
        ImageButton mTopButton = (ImageButton) contentView.findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitChanges();
                mBottomSheetDialog.dismiss();
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        Spinner spinner = (Spinner) contentView.findViewById(R.id.punchTypeSpinner);
        spinner.setSelection(getIndex(spinner, punchType));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                punchType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText editText = (EditText) contentView.findViewById(R.id.glovesWeight);
        editText.setText(glovesWeight);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (contentView.findViewById(R.id.glovesWeightForm).getVisibility() == View.VISIBLE) {
                    glovesWeight = s.toString();
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
                            int color = ContextCompat.getColor(context, R.color.colorGreyDark);
                            if(toggleButton.getId() != checkedId){
                                checked[index*2+j] = false;
                                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                            } else {
                                checked[index*2+j] = true;
                                drawable.clearColorFilter();
                                switch (index) {
                                    case 0:
                                        hand = (j == 0) ? "ic_left_hand_black" : "ic_right_hand_black";
                                        break;
                                    case 1:
                                        gloves = (j == 0) ? "ic_gloves_on_black" : "ic_gloves_off_black";
                                        break;
                                    case 2:
                                        moves = (j == 0) ? "ic_punch_on_step_black" : "ic_punch_with_space_black";
                                        break;
                                    default:
                                        Toast.makeText(context, "No view found for " + index,
                                                Toast.LENGTH_SHORT).show();
                                }
                            }
                            toggleButton.setCompoundDrawables(null, drawable, null, null);
                        }
                    }
                });}
        }
    }

    public void show(){
        mBottomSheetDialog.show();
    }

    public View getContentView() {
        return contentView;
    }

    public Dialog getmBottomSheetDialog() {
        return mBottomSheetDialog;
    }
}

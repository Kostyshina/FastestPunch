package by.stylesoft.fastestpunch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PunchButtonActivity extends AppCompatActivity {
    private LinearLayout [] linearLayouts;
    private ResultSeekBar punchSpeedSeekBar;
    private ResultSeekBar reactionSpeedSeekBar;
    private ResultSeekBar accelerationSeekBar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_button);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_main_settings), Context.MODE_PRIVATE);
        String hand = prefs.getString(getString(R.string.hand_item), "ic_right_hand_black");
        String gloves = prefs.getString(getString(R.string.gloves_item), "ic_gloves_off_black");
        String moves = prefs.getString(getString(R.string.moves_item), "ic_punch_with_space_black");
        String glovesWeight = prefs.getString(getString(R.string.gloves_weight_item), "");
        String punchType = prefs.getString(getString(R.string.punch_type_item), getResources().getStringArray(R.array.punch_type_array)[0]);

        init(punchType, hand, gloves, glovesWeight, moves);

        prefs = getSharedPreferences(getString(R.string.pref_persistent_storage), Context.MODE_PRIVATE);
        float progressPunchSpeed = prefs.getFloat(getString(R.string.punch_speed_result), 0f);
        float progressReactionSpeed  = prefs.getFloat(getString(R.string.reaction_speed_result), 0f);
        float progressAcceleration = prefs.getFloat(getString(R.string.acceleration_result), 0f);

        Button mButtonOk = (Button) findViewById(R.id.okButton);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add save to history
                finish();
            }
        });
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = (LinearLayout) findViewById(R.id.punchSpeedLayout);
        punchSpeedSeekBar = (ResultSeekBar) linearLayouts[0].getChildAt(2);
        punchSpeedSeekBar.setMax(getResources().getInteger(R.integer.punch_speed_max));
        punchSpeedSeekBar.setProgress((int) (progressPunchSpeed * 100));
        linearLayouts[1] = (LinearLayout) findViewById(R.id.reactionSpeedLayout);
        reactionSpeedSeekBar = (ResultSeekBar) linearLayouts[1].getChildAt(2);
        reactionSpeedSeekBar.setMax(getResources().getInteger(R.integer.reaction_speed_max));
        reactionSpeedSeekBar.setProgress((int) (progressReactionSpeed * 100));
        linearLayouts[2] = (LinearLayout) findViewById(R.id.accelerationLayout);
        accelerationSeekBar = (ResultSeekBar) linearLayouts[2].getChildAt(2);
        accelerationSeekBar.setMax(getResources().getInteger(R.integer.acceleration_max));
        accelerationSeekBar.setProgress((int) (progressAcceleration * 100));

        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout = (LinearLayout) linearLayouts[i].getChildAt(0);
            switch (i) {
                case 0:
                    ((TextView) linearLayout.getChildAt(0)).setText(getString(R.string.punch_speed_text).toLowerCase());
                    ((TextView) linearLayout.getChildAt(1)).setText(getString(
                            R.string.punch_speed_dimen_text, progressPunchSpeed));
                    break;
                case 1:
                    ((TextView) linearLayout.getChildAt(0)).setText(getString(R.string.reaction_speed_text).toLowerCase());
                    ((TextView) linearLayout.getChildAt(1)).setText(getString(
                            R.string.reaction_speed_dimen_text, progressReactionSpeed));
                    break;
                default:
                    ((TextView) linearLayout.getChildAt(0)).setText(getString(R.string.acceleration_text).toLowerCase());
                    ((TextView) linearLayout.getChildAt(1)).setText(
                            getString(R.string.acceleration_dimen_text, progressAcceleration));
            }
        }
        initData();
        initSeekBarData(punchSpeedSeekBar);
        initSeekBarData(reactionSpeedSeekBar);
        initSeekBarData(accelerationSeekBar);
    }

    private void initData(){
        float redSpan = 0.25f;
        float greenSpan = 0f;
        float yellowSpan = 0.5f;
        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = redSpan * 100;
        mProgressItem.color = R.color.colorRedDark;
        progressItemList.add(mProgressItem);
        // yellow span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = yellowSpan * 100;
        mProgressItem.color = R.color.colorPrimaryLight;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = greenSpan * 100;
        mProgressItem.color = R.color.colorGrey;
        progressItemList.add(mProgressItem);
    }

    private void initSeekBarData(ResultSeekBar seekBar){
        seekBar.initData(progressItemList);
        seekBar.invalidate();
    }

    private void init(String punchType, String hand, String gloves, String glovesWeight, String moves){
        ((TextView) findViewById(R.id.punchTypeView)).setText(punchType);
        ((TextView) findViewById(R.id.handView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(this,
                        getResources().getIdentifier(hand, "drawable", getPackageName())), null, null);
        ((TextView) findViewById(R.id.glovesView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(this,
                        getResources().getIdentifier(gloves, "drawable", getPackageName())), null, null);
        ((TextView) findViewById(R.id.glovesView)).setText(glovesWeight);
        ((TextView) findViewById(R.id.movesView)).setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(this,
                        getResources().getIdentifier(moves, "drawable", getPackageName())), null, null);
    }
}

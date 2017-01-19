package by.stylesoft.fastestpunch;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import by.stylesoft.fastestpunch.data.FastestPunchContract.*;
import by.stylesoft.fastestpunch.data.FastestPunchDbHelper;

public class PunchButtonActivity extends AppCompatActivity {
    private LinearLayout [] linearLayouts;
    private ResultSeekBar punchSpeedSeekBar;
    private ResultSeekBar reactionSpeedSeekBar;
    private ResultSeekBar accelerationSeekBar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    private FastestPunchDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_button);

        mDbHelper = new FastestPunchDbHelper(this);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_main_settings), Context.MODE_PRIVATE);
        final String hand = prefs.getString(getString(R.string.hand_item), "ic_right_hand_black");
        final String gloves = prefs.getString(getString(R.string.gloves_item), "ic_gloves_off_black");
        final String moves = prefs.getString(getString(R.string.moves_item), "ic_punch_with_space_black");
        final String glovesWeight = prefs.getString(getString(R.string.gloves_weight_item), "");
        final int punchType = prefs.getInt(getString(R.string.punch_type_item), 0);

        init(punchType, hand, gloves, glovesWeight, moves);

        prefs = getSharedPreferences(getString(R.string.pref_persistent_storage), Context.MODE_PRIVATE);
        final float progressPunchSpeed = prefs.getFloat(getString(R.string.punch_speed_result), 0f);
        final float progressReactionSpeed  = prefs.getFloat(getString(R.string.reaction_speed_result), 0f);
        final float progressAcceleration = prefs.getFloat(getString(R.string.acceleration_result), 0f);

        Button mButtonOk = (Button) findViewById(R.id.okButton);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add save to history
                String[] values = new String[5];
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                values[0] = String.valueOf(punchType);
                values[1] = hand;
                values[2] = gloves;
                values[3] = String.valueOf("".equals(glovesWeight) ? 0 : Integer.valueOf(glovesWeight));
                values[4] = moves;

                int parameterId = insertParameters(db, values);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.ROOT);
                Calendar calendar = Calendar.getInstance();

                values[0] = String.valueOf(parameterId);
                values[1] = String.valueOf(progressPunchSpeed);
                values[2] = String.valueOf(progressReactionSpeed);
                values[3] = String.valueOf(progressAcceleration);
                values[4] = sdf.format(calendar.getTime());

                insertHistory(db, values);
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
        reactionSpeedSeekBar.setProgress((int) (reactionSpeedSeekBar.getMax() - progressReactionSpeed * 100));
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
                            fromHtml(getString(R.string.acceleration_dimen_text, progressAcceleration)));
            }
        }
        initData();
        initSeekBarData(punchSpeedSeekBar);
        initSeekBarData(reactionSpeedSeekBar);
        initSeekBarData(accelerationSeekBar);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private int insertParameters(SQLiteDatabase db, String[] values){
        String selectQuery = "SELECT " + ParametersEntry._ID + " FROM " + ParametersEntry.TABLE_NAME + " WHERE " +
                ParametersEntry.COLUMN_PUNCH_TYPE + " = ? AND " + ParametersEntry.COLUMN_HAND + " = ? AND " +
                ParametersEntry.COLUMN_GLOVES + " = ? AND " + ParametersEntry.COLUMN_GLOVES_WEIGHT+ " = ? AND "
                + ParametersEntry.COLUMN_MOVES + " = ?;";
        String insertQuery = "INSERT INTO " + ParametersEntry.TABLE_NAME + "(" +
                ParametersEntry.COLUMN_PUNCH_TYPE + ", " + ParametersEntry.COLUMN_HAND + ", " +
                ParametersEntry.COLUMN_GLOVES + ", " + ParametersEntry.COLUMN_MOVES + ")" + "VALUES(" +
                values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3]+ ");";
        Cursor raw = db.rawQuery(selectQuery, values);
        int id;
        if(raw.moveToFirst()) {
            id = raw.getInt(raw.getColumnIndex(ParametersEntry._ID));
        } else {
            ContentValues cv = new ContentValues();
            cv.put(ParametersEntry.COLUMN_PUNCH_TYPE, Integer.parseInt(values[0]));
            cv.put(ParametersEntry.COLUMN_HAND, values[1]);
            cv.put(ParametersEntry.COLUMN_GLOVES, values[2]);
            cv.put(ParametersEntry.COLUMN_GLOVES_WEIGHT, Integer.valueOf(values[3]));
            cv.put(ParametersEntry.COLUMN_MOVES, values[4]);
            id = (int) db.insert(ParametersEntry.TABLE_NAME, null, cv);
            //db.execSQL(insertQuery);
        }
        raw.close();
        return id;
    }

    private int insertHistory(SQLiteDatabase db, String[] values){
        String selectQuery = "SELECT * FROM " + HistoryEntry.TABLE_NAME + " WHERE " +
                HistoryEntry.COLUMN_PARAMETERS + " = ? , " + HistoryEntry.COLUMN_PUNCH_SPEED + " = ? , " +
                HistoryEntry.COLUMN_REACTION_SPEED + " = ? , " + HistoryEntry.COLUMN_ACCELERATION + " = ? , "
                + HistoryEntry.COLUMN_DATE + " = ? ;";

        ContentValues cv = new ContentValues();
        cv.put(HistoryEntry.COLUMN_PARAMETERS, Integer.parseInt(values[0]));
        cv.put(HistoryEntry.COLUMN_PUNCH_SPEED, Float.parseFloat(values[1]));
        cv.put(HistoryEntry.COLUMN_REACTION_SPEED, Float.parseFloat(values[2]));
        cv.put(HistoryEntry.COLUMN_ACCELERATION, Float.parseFloat(values[3]));
        cv.put(HistoryEntry.COLUMN_DATE, values[4]);
        return (int) db.insert(HistoryEntry.TABLE_NAME, null, cv);
    }

    private void initData(){
        float redSpan = 0.25f;
        float greenSpan = 0f;
        float yellowSpan = 0.5f;
        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = redSpan * 100;
        mProgressItem.color = R.color.colorSeekBarRed;
        progressItemList.add(mProgressItem);
        // yellow span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = yellowSpan * 100;
        mProgressItem.color = R.color.colorSeekBarYellow;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = greenSpan * 100;
        mProgressItem.color = R.color.colorSeekBarGreen;
        progressItemList.add(mProgressItem);
    }

    private void initSeekBarData(final ResultSeekBar seekBar){
        seekBar.post(new Runnable() {
            @Override
            public void run() {
                seekBar.setThumb(drawThumb(seekBar.getWidth()));
            }
        });
        seekBar.initData(progressItemList);
        seekBar.invalidate();
    }

    private void init(int punchType, String hand, String gloves, String glovesWeight, String moves){
        ((TextView) findViewById(R.id.punchTypeView)).setText(getResources().getStringArray(R.array.punch_type_array)[punchType]);
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

    public BitmapDrawable drawThumb(int width){
        Path path = new Path();
        path.moveTo(width / 24 / 2, 0);
        path.lineTo(width / 24, 3 * width / 24 / 4);
        path.lineTo(width / 24, 9 * width / 24 / 4);
        path.lineTo(0, 9 * width / 24 / 4);
        path.lineTo(0, 3 * width / 24 / 4);
        path.close();

        Paint p;
        Bitmap bitmap = null;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(ContextCompat.getColor(this, R.color.colorGreyDarkest));

        bitmap = Bitmap.createBitmap(width / 24, width / 12, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawPath(path, p);

        return new BitmapDrawable(getResources(), bitmap);
    }
}

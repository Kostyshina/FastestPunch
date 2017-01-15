package by.stylesoft.fastestpunch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class LicenseActivity extends AppCompatActivity {

    View lineView;
    DrawLineView drawView;
    Button buttonAccept;
    static final String STORAGE_NAME = "PersistentStorage";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        //Drawable drawable = getResources().getDrawable(R.drawable.button_background);
        BitmapDrawable parallel = drawParallelogramLine();

        View parallelLine = findViewById(R.id.imageViewLine);
        parallelLine.setBackground(parallel);
        buttonAccept = (Button)findViewById(R.id.buttonAccept);
        prefs = getApplicationContext().getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
        final boolean acceptButtonPushed = prefs.getBoolean("accept_button_pushed",false);
        if(acceptButtonPushed){
            buttonAccept.setVisibility(View.INVISIBLE);
        }
        buttonAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changePreference();
                //Toast.makeText(LicenseActivity.this, "accept_button_pushed value =" + acceptButtonPushed, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //drawView = new DrawLineView(this);

        //lineView = findViewById(R.id.lineView);
        //lineView.draw();
    }

    public void changePreference(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("accept_button_pushed", true);
        edit.apply();
    }

    public BitmapDrawable drawParallelogramLine(){
        Matrix matrix = new Matrix();
        Path path = new Path();
        path.addRect(0, 0, 90, 18, Path.Direction.CW);
        Path pathStamp = new Path();
        Paint p;
        Paint line;
        Bitmap bitmap = null;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);

        bitmap = Bitmap.createBitmap(90*2+20+20, 10+18+10, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        p.setColor(getResources().getColor(R.color.colorPrimaryLight));

        matrix.reset();
        matrix.setTranslate(0,0);
        matrix.postSkew(-1f, 0.0f, 0, 18);
        path.transform(matrix, pathStamp);
        canvas.drawPath(pathStamp, p);

        p.setColor(getResources().getColor(R.color.colorAccent));

        matrix.reset();
        matrix.setTranslate(90+20,0);
        matrix.postSkew(-1f, 0.0f, 90+20, 18);
        path.transform(matrix, pathStamp);
        canvas.drawPath(pathStamp, p);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
                bitmap);
        bitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);

        return bitmapDrawable;
    }
}

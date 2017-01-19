package by.stylesoft.fastestpunch;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LicenseActivity extends AppCompatActivity {

    private Button buttonAccept;

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
        if(!previouslyStarted && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        //Drawable drawable = getResources().getDrawable(R.drawable.button_background);

        final View parallelLine = findViewById(R.id.imageViewLine);
        parallelLine.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable parallel = drawParallelogramLine(parallelLine.getWidth());
                parallelLine.setBackground(parallel);
            }
        });

        buttonAccept = (Button)findViewById(R.id.buttonAccept);
        prefs = getApplicationContext().getSharedPreferences(getString(R.string.pref_persistent_storage),MODE_PRIVATE);
        final boolean acceptButtonPushed = prefs.getBoolean(getString(R.string.accept_button_pushed),false);
        if(acceptButtonPushed){
            buttonAccept.setVisibility(View.INVISIBLE);
        }
        buttonAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changePreference();
                finish();
            }
        });
    }

    public void changePreference(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getString(R.string.pref_persistent_storage),MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(getString(R.string.accept_button_pushed), true);
        edit.apply();
    }

    public BitmapDrawable drawParallelogramLine(int width){
        Matrix matrix = new Matrix();
        Path path = new Path();
        //width / 3 -  ; 0.2;
        //path.addRect(0, 0, (3 * width) / 22, (3 * width) / 110, Path.Direction.CW);
        path.addRect(0, 0, (4 * width) / 40, (4 * width) / 200, Path.Direction.CW);
        Path pathStamp = new Path();
        Paint p;
        Bitmap bitmap;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);

        bitmap = Bitmap.createBitmap(width / 4, width / 80 * 2 + (4 * width) / 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        p.setColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));

        matrix.reset();
        matrix.setTranslate(0,0);
        matrix.postSkew(-1f, 0.0f, 0, (4 * width) / 200);
        path.transform(matrix, pathStamp);
        canvas.drawPath(pathStamp, p);

        p.setColor(ContextCompat.getColor(this, R.color.colorAccent));

        matrix.reset();
        matrix.setTranslate(width / 8, 0);
        matrix.postSkew(-1f, 0.0f, width / 8, (4 * width) / 200);
        path.transform(matrix, pathStamp);
        canvas.drawPath(pathStamp, p);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
                bitmap);
        bitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);

        return bitmapDrawable;
    }
}

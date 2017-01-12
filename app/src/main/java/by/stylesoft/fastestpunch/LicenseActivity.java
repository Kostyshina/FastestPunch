package by.stylesoft.fastestpunch;

import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LicenseActivity extends AppCompatActivity {

    View lineView;
    DrawLineView drawView;
    Button buttonAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        //Drawable drawable = getResources().getDrawable(R.drawable.button_background);
        BitmapDrawable parallel = drawParallelogramLine();

        View parallelLine = findViewById(R.id.imageViewLine);
        parallelLine.setBackground(parallel);
        buttonAccept = (Button)findViewById(R.id.buttonAccept);

        //drawView = new DrawLineView(this);

        //lineView = findViewById(R.id.lineView);
        //lineView.draw();
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

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

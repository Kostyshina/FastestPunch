package by.stylesoft.fastestpunch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by User on 29.12.2016.
 */

public class DrawLineView extends View {
    Path path;
    Path pathStamp;
    Paint p;
    Paint line;

    public DrawLineView(Context context, AttributeSet attrs, Path path, Path pathStamp, Paint p, Paint line) {
        super(context, attrs);
        this.path = path;
        this.pathStamp = pathStamp;
        this.p = p;
        this.line = line;
    }

    public DrawLineView(Context context, AttributeSet attrs, int defStyleAttr, Path path, Path pathStamp, Paint p, Paint line) {
        super(context, attrs, defStyleAttr);
        this.path = path;
        this.pathStamp = pathStamp;
        this.p = p;
        this.line = line;
    }

    public DrawLineView(Context context) {
        super(context);
        path = new Path();
        path.rLineTo(100, 0);

        //Uri pathUri = Uri.parse("android.resource://by.stylesoft.fastestpunch/"+R.drawable.parallelogram);
        pathStamp = new Path();
        pathStamp.lineTo(20, 0);
        pathStamp.lineTo(10, 10);
        pathStamp.lineTo(-10, 10);
        pathStamp.close();

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(3);

        line = new Paint(p);
        line.setColor(Color.GREEN);
        //line.setPathEffect(new PathDashPathEffect(pathStamp, 20, 0, PathDashPathEffect.Style.MORPH));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawARGB(80, 102, 204, 255);

        canvas.translate(100, 100);
        canvas.drawPath(path, line);
    }
}

package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainSettingsActivity extends AppCompatActivity {

    ImageButton mTopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);
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
                    if (v2 instanceof Button) {
                        Button b = (Button) v2;
                        b.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                               v.setSelected(true);
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

       /* Button rightHand = (Button) findViewById(R.id.rightHandButton);
        Button glovesOn = (Button) findViewById(R.id.glovesOnButton);
        Button withSpace = (Button) findViewById(R.id.punchWithSpaceButton);*/


       /* gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainSettingsActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

       /* mTopButton = (ImageButton) findViewById(R.id.menuTopButton);
        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               *//* if(mDrawer.getDrawerState() == 1) {
                    mDrawer.closeMenu();
                } else {
                    mDrawer.openMenu();
                    startActivity(MainSettingsActivity.class);
                }*//*
            }
        });*/
    }

    private Bitmap changeBitmapColor(int id){
        Drawable sourceDrawable = getResources().getDrawable(id);
        Bitmap sourceBitmap = DrawableBitmap.drawableToBitmap(sourceDrawable);
        return DrawableBitmap.changeImageColor(sourceBitmap,
                getResources().getColor(R.color.colorGreyDark));
    }
}

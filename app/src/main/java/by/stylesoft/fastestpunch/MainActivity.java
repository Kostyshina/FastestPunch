package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //содержимое Activity из layout-файла

        ImageButton mainSettingsButton = (ImageButton) findViewById(R.id.mainSettingsButton);
        final TextView punchType = (TextView) findViewById(R.id.punchType);

        mainSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchType.setText("Нажата кнопка");
            }
        });
    }

    public void startActivity(View v){
        MainSettingsActivity mainSettingsActivity = new MainSettingsActivity();
        mainSettingsActivity.openWindow(new Intent(getBaseContext(), MainSettingsActivity.class));
    }
}

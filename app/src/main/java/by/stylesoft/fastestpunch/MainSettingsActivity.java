package by.stylesoft.fastestpunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

    }

    public void openWindow(Intent intent){
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }
}

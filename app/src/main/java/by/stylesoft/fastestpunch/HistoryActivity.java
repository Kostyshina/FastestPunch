package by.stylesoft.fastestpunch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import by.stylesoft.fastestpunch.data.FastestPunchDbHelper;

import by.stylesoft.fastestpunch.data.FastestPunchContract.*;

public class HistoryActivity extends AppCompatActivity {

    private FastestPunchDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mDbHelper = new FastestPunchDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_overflow_menu, menu);
        return true;
    }

        private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                HistoryEntry._ID,
                HistoryEntry.COLUMN_PARAMETERS,
                HistoryEntry.COLUMN_PUNCH_SPEED,
                HistoryEntry.COLUMN_REACTION_SPEED,
                HistoryEntry.COLUMN_ACCELERATION,
                HistoryEntry.COLUMN_DATE
        };

        // Делаем запрос
        Cursor cursor = db.query(
                HistoryEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        TextView displayTextView = (TextView) findViewById(R.id.tableInfoView);

        try {
            displayTextView.setText("Таблица содержит " + cursor.getCount() + " записей.\n\n");
            displayTextView.append(HistoryEntry._ID + " - " +
                    HistoryEntry.COLUMN_PARAMETERS + " - " +
                    HistoryEntry.COLUMN_PUNCH_SPEED + " - " +
                    HistoryEntry.COLUMN_REACTION_SPEED + " - " +
                    HistoryEntry.COLUMN_ACCELERATION + " - " +
                    HistoryEntry.COLUMN_DATE + "\n");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(HistoryEntry._ID);
            int parametersIdColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_PARAMETERS);
            int punchSpeedColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_PUNCH_SPEED);
            int reactionSpeedColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_REACTION_SPEED);
            int accelerationColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_ACCELERATION);
            int dateColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_DATE);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                int currentParametersId = cursor.getInt(parametersIdColumnIndex);
                float currentPunchSpeed = cursor.getFloat(punchSpeedColumnIndex);
                float currentReactionSpeed = cursor.getFloat(reactionSpeedColumnIndex);
                float currentAcceleration = cursor.getFloat(accelerationColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append(("\n" + currentID + " - " +
                        currentParametersId + " - " +
                        currentPunchSpeed + " - " +
                        currentReactionSpeed + " - " +
                        currentAcceleration + " - " +
                        currentDate));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}

package com.aleksa.matejic.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.aleksa.matejic.app.utils.DatabaseHelper;

public class StatisticsActivity extends Activity
{

    private TableLayout tlStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tlStatistics = (TableLayout) findViewById(R.id.tlStatistics);

        // Aleksa TODO: call method to populate result
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor result = db.getAllData();

        while (result.moveToNext())
        {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.statistics_table_row, null);

            TextView tv;
            // filling in cells
            tv = (TextView) tableRow.findViewById(R.id.playerNameCell);
            tv.setText(result.getString(1));

            tv = (TextView) tableRow.findViewById(R.id.devilDistanceCell);
            tv.setText(result.getString(2));

            tv = (TextView) tableRow.findViewById(R.id.avoidedBlackCloudsCell);
            tv.setText(result.getString(3));

            tv = (TextView) tableRow.findViewById(R.id.avoidedWhiteCloudsCell);
            tv.setText(result.getString(4));

            tv = (TextView) tableRow.findViewById(R.id.avoidedArrowsCell);
            tv.setText(result.getString(5));

            tv = (TextView) tableRow.findViewById(R.id.scoreCell);
            tv.setText(result.getString(6));

            // add row to the table
            tlStatistics.addView(tableRow);
        }
    }
}

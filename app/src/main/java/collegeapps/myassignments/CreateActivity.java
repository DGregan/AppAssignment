package collegeapps.myassignments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


/**
 * Created by dgreg_000 on 25/11/2015.
 *
 *  Create Activity | Handles the creation of DB Table and User input
 *
 *          openDB() - Opens the DB for usage to Create Activity
 *          onClick_InsertData - Inserts user inputted Data once a Button is clicked
 */
public class CreateActivity extends AppCompatActivity {

    DBAdapter myDB;
    // Declaring editText / Button variables for DB integration
    EditText editATitle, editSubName, editTMark, editDateD;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment);


        // Initializing EditText Variables and Validation
        editATitle = (EditText) findViewById(R.id.editTitle);
        if (editATitle.getText().toString().length() == 0)
            editATitle.setError("Title name required!");
        editSubName = (EditText) findViewById(R.id.editSubjectName);
        editTMark = (EditText) findViewById(R.id.editTotalMark);
        editDateD = (EditText) findViewById(R.id.editDateDue);
        if (editDateD.getText().toString().length() == 0)
            editDateD.setError("Date due required!!");

        openDB();

    }

    // Opens the Database
    private void openDB() {
        myDB = new DBAdapter(this);
        myDB.open(); // goes to DBAdapter open()
    }

    // When the Submit Button is clicked, run this method
    public void onClick_InsertData (View v){
        Log.i("State", "CALLING onClick_InsertData FUNCTION");
        // If editATitle / editADateDue fields are not empty...
            if (!TextUtils.isEmpty(editATitle.getText().toString()) && !TextUtils.isEmpty(editDateD.getText().toString())) {
                // runs InsertRow from DBAdapter
                Log.i("State", "CALLING insertRow(DBAdapter) FUNCTION");
                myDB.insertRow(
                        // Inserts the following details to the DB
                        editATitle.getText().toString(),
                        editSubName.getText().toString(),
                        editTMark.getText().toString(),
                        editDateD.getText().toString()
                );// end myDB.insertRow()
                Log.i("State", "CALLED insertRow(DBAdapter) FUNCTION");
                Log.i("State", "Successful Data insertion");
                Toast.makeText(CreateActivity.this, "Assignment Created", Toast.LENGTH_LONG).show();

                // Once a successful Data Insertion is made, got to ViewActivity
                Intent intentViewButton = new Intent(this, ViewActivity.class);
                Log.i("State", "Starting ViewActivity");
                startActivity(intentViewButton); // execute activity 'ViewActivity'

            }// end if
            else {
                Log.i("State", "Unsuccessful Data insertion");
                Toast.makeText(CreateActivity.this, "ERROR: Assignment not created", Toast.LENGTH_SHORT).show();
            }// end else
        Log.i("State", "CALLED onClick_InsertData FUNCTION");
    }// end onClick_insert


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}// end something

package collegeapps.myassignments;

/*
*   Created by D.Gregan
*
*   Main Activity | Handles Main Menu navigation to their respected Activites + Graceful app exit
*
*       onClickCreateButton() - Handles changing to CreateAssignment Activity
*       onClickViewButton() - Handles changing to ViewAssignment Activity
*       onClickExitButtonClickListener() - Handles with gracefully application exit
*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("State", "Entering Main Activity");
        setContentView(R.layout.activity_main); // Use this XML for display

        // Listens for click of ExitButton
        onExitButtonClickListener();


    }


    // onClick of Create Assignment button, go to CreateActivity
    public void onClickCreateButton(View v) {
        Intent intentCreateButton = new Intent(this, CreateActivity.class);
        Log.i("State", "Starting CreateActivity");
        startActivity(intentCreateButton); // execute activity 'CreateActivity'

    }
    // onClick of View Assignments button, go to ViewActivity
    public void onClickViewButton(View v) {
        Intent intentViewButton = new Intent(this, ViewActivity.class);
        Log.i("State", "Starting ViewActivity");
        startActivity(intentViewButton); // execute activity 'ViewActivity'
    }
    // onClick of Exit Button, prompt the user if they are sure to exit
    public void onExitButtonClickListener(){
        Log.i("State", "CALLING onExitButtonClickListener FUNCTION");
        // Creating button local variable
        Button exitButton = (Button) findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder exitDialog = new AlertDialog.Builder(MainActivity.this);
                        //Setting Dialog message + Yes / No attributes
                        exitDialog.setMessage("Do you wish to close this app?")
                                .setCancelable(false) // Doesn't show cancel button
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish(); // Closing app gracefully
                                    }// end OnClick Positive Button

                                })// end Positive Button attributes
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel(); // closes dialog window

                                    }
                                });
                        // Dialog Box
                        AlertDialog alert = exitDialog.create();
                        alert.setTitle("Exit");
                        alert.show();


                    }// end Outer OnClick
                }// end View.OnClickListener()

        );// end exitButton.setOnClickListener()

        Log.i("State", "CALLED onExitButtonClickListener FUNCTION");
    }// end onButtonClickListener


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
        //int id = item.getItemId();

        // GOOGLE CODE - Return to MainMenu Action Bar

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}

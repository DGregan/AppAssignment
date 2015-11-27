package collegeapps.myassignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by dgreg_000 on 19/11/2015.
 */
public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // use this XML

        // Display the Splash_Screen for a certain amount of time
        // Then go to MainActivity

        // Creating Timer Mutli thread
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000); // Makes thread sleep | Time in Miliseconds - 4 Seconds total
                }// end try

                catch (InterruptedException e){
                    Log.e("ERROR", "Cannot Run Splash", e); // Return error to LogCat
                }// end catch

                // Once sleep() is completed, go to Main Activity
                finally{
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent); // Go to MainActivity

                }// end finally
            }// end run
        };// end Thread
        timer.start(); // Starts the thread
    }// end onCreate

    @Override
    protected void onPause() {
        super.onPause(); // Destroy splash screen after Timer is complete
        finish();
    }
}// SplashActivity

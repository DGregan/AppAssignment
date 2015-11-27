package collegeapps.myassignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by dgreg_000 on 25/11/2015.
 *
 *  View Activity | Handles displaying DB data + updating/deleting DB data
 *
 *      openDB() - Opens the DB for usage to View Activity
 *      addDataListView() - Adds user created Data from DB to ListView
 *      updateListItem() - Updates list item selected by the User
 *      deleteListItem() - Deletes list item selected by the User
 *      onClick_ListItemOnListener() - Listens for item selected by User
 */
public class ViewActivity extends AppCompatActivity {

    DBAdapter myDB; // Declaring instance of the DB
    // Declaring editText / Button variables for DB integration
    EditText editATitle, editSubName, editTMark, editDateD;

    private String TAG = DBAdapter.ID;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("State", "Entering View Assignment Activity");
        setContentView(R.layout.view_assignment); // Use this XML for the Activity


        // Opens the DB
        openDB();

        // Displays DB data into a list View
        addDataListView();

        // Listens for user select on Item in ListView
        onClick_ListItemOnListener();
    }// end onCreate

    private void openDB(){
        myDB = new DBAdapter(this);

        myDB.open();
    }// end openDB


    public void addDataListView(){
        Log.i("State", "CALLING addDataListView FUNCTION");
        Cursor cursor = myDB.getAllRows();

        String[] fromEditTexts = new String[] {
                DBAdapter.TITLE,
                DBAdapter.SUB_NAME,
                DBAdapter.T_MARKS,
                DBAdapter.D_DUE
        };// end of fromEditTexts

        int[] toTextViewIDs = new int[] {
                //R.id.textViewItemNumber,
                R.id.textViewItemTitle,
                R.id.textViewItemSubject,
                R.id.textViewItemMarks,
                R.id.textViewItemDate
        }; // end toTextViewIDs
        Log.i("State", "Creating SimpleCursorAdapter");
        // Creating SimpleCursorAdapter for List Viewing | Use row_item_layout for Row Item design
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.row_item_layout, cursor, fromEditTexts, toTextViewIDs, 0);
        ListView listViewTasks = (ListView) findViewById(R.id.listViewAssignments); //initializing List View
        Log.i("State", "Setting adapter to ListViewTasks");
        listViewTasks.setAdapter(myCursorAdapter); // Setting Adapter to ListView

        // If there are No assignments in the DB, display the following
/*
        //Initializing empty List View text display
        Log.i("State", "Creating TextView for Empty View Assignments Activity");
        TextView emptyListViewText = (TextView)findViewById(R.id.emptyViewDisplay); // initializing Text View
        Log.i("State", "Setting emptyListViewText to listViewTasks");
        //listViewTasks.setEmptyView(emptyListViewText);
*/
        //Initializing empty List View Image display
        Log.i("State", "Creating Image for Empty View Assignments Activity");
        ImageView emptyListImage = (ImageView) findViewById(R.id.emptyViewImage);
        Log.i("State", "Setting emptyViewImage to listViewTasks");
        listViewTasks.setEmptyView(emptyListImage);

        Log.i("State", "CALLED addDataListView FUNCTION");
    } // end addDataListView

// Handling DB updating of ListItem
    // TODO NEED TO GO UPDATE DB BY GOING BACK TO CREATE AND UPDATING FROM THERE
    // TODO ALSO HANDLE WHERE DB NEEDS TO CLOSE
    public void updateListItem(long id) {
        Log.i("State", "CALLING updateListItem FUNCTION");
        Cursor cursor = myDB.getARow(id); //Calls getARow function from DBAdapter
        // Move to first record if possible
        if (cursor.moveToFirst()){
            // Update each editText field
            String new_title = editATitle.getText().toString();
            String new_subName = editSubName.getText().toString();
            String new_mark = editTMark.getText().toString();
            String new_date = editDateD.getText().toString();

            // Calling updateRow function
            myDB.updateRow(id, new_title, new_subName, new_mark, new_date);


        }// end if
        else {
            Log.e("ERROR", "CURSOR MOVE NOT POSSIBLE");
        }
        cursor.close(); // closing cursor
    }// end updateListItem

    public void deleteListItem(long id){
        Log.i("State", "CALLING deleteListItem FUNCTION");
        myDB.deleteRow(id);
        Log.i("State", "CALLED deleteListItem FUNCTION");
    }// end deleteListItem

    // Listen for click of List Item
    // Listen for click of List Item
    public void onClick_ListItemOnListener(){
        ListView listViewAssignments = (ListView) findViewById(R.id.listViewAssignments); //initializing List View
        listViewAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Setting Item Listener to listViewItems
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                AlertDialog.Builder changeDialog = new AlertDialog.Builder(ViewActivity.this);

                //Setting Dialog message + Update/Delete/Cancel Items
                changeDialog.setMessage("Do you wish change this Item?");

                changeDialog.setPositiveButton("Update Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateListItem(id); // TODO FIX UPDATE LIST ITEM
                        addDataListView();
                    }// end OnClick Positive Button
                });// end Update Button attributes

                changeDialog.setNegativeButton("Delete Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteListItem(id);
                        addDataListView();
                    }// Onclick
                });// end Delete Button Attributes

                changeDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }// end onClick
                });// end Cancel Button Attributes
                // Dialog Box
                AlertDialog alert = changeDialog.create();
                alert.setTitle("Assignment Change");
                alert.show(); // Display alert dialog
            }// end onItemClick
        });// end listViewAssignments.setOnItemClickListener
    }// end onClick_ListItemOnListener



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
}

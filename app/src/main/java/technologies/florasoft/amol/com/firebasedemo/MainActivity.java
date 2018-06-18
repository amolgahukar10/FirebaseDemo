package technologies.florasoft.amol.com.firebasedemo;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

EditText text ;
Button button;
DatabaseReference databaseReference ;
ListView listview;
ArrayList<User> massageArray;
AdapterClass adapterClass;
String androidDeviceId;
int checkedCount = 0;
ArrayList list_item = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
        button.setEnabled(false);
        listview = findViewById(R.id.listview);

        androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        massageArray = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Massage");

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String id = databaseReference.push().getKey();
                String name = text.getText().toString();
                androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                User user =  new User(name,id,androidDeviceId);
                databaseReference.child(id).setValue(user);

                text.setText("");

            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = text.getText().toString().length();

                if (length  > 0){
                    button.setEnabled(true);
                }
                else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int length = text.getText().toString().length();

                if (length  > 0){
                    button.setEnabled(true);
                }
                else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = text.getText().toString().length();

                if (length  > 0){
                    button.setEnabled(true);
                }
                else {
                    button.setEnabled(false);
                }

            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                massageArray.clear();

                for(DataSnapshot massageSnapshot : dataSnapshot.getChildren()){

                    User user = massageSnapshot.getValue(User.class);
                    massageArray.add(user);
                }

                adapterClass = new AdapterClass( MainActivity.this, massageArray , androidDeviceId);
                listview.setAdapter(adapterClass);
                listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                listview.setSelection(listview.getAdapter().getCount()-1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                checkedCount = listview.getCheckedItemCount();


                User massageid = massageArray.get(position);

                String mid = massageid.getId();
                //setting CAB title
                mode.setTitle(checkedCount + " Selected");
                Log.v("checkedCount", String.valueOf(position));


                if(checked){
                    list_item.add(mid);     // Add to list when checked ==  true
                }else {
                    list_item.remove(mid);
                }


            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.main_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                final int deleteSize = list_item.size();
                int itemId = item.getItemId();
                if(itemId == R.id.action_delete) {




                    for (int i=0;i<list_item.size();i++){

                        String msId = String.valueOf(list_item.get(i));
                        Log.v("ArrayIten",msId);
                        DatabaseReference deletemassage =  FirebaseDatabase.getInstance().getReference("Massage").child(msId);
                        deletemassage.removeValue();
                    }

                    Log.v("list_item", String.valueOf(list_item));

                    /*for(long ids : list_item){
                        // Make proper check, if needed, before deletion
                        String whereDelId = DBOpenHelper.COL_ID + "=" + ids;
                        int res = getContentResolver().delete(TripProvider.CONTENT_URI_START, whereDelId, null);
                        if(res == -1){
                            Log.d(TAG, "onActionItemClicked: Delete Failed for ID = "+ids);
                        }
                    }*/

                    checkedCount = 0;
                    list_item.clear();
                    mode.finish();
                }
                //Toast.makeText(context,deleteSize+" Items deleted",Toast.LENGTH_SHORT).show();

                return true;

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


    }



}

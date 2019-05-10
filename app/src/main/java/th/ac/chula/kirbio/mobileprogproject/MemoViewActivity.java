package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import th.ac.chula.kirbio.mobileprogproject.db.AppDatabase;
import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;
import th.ac.chula.kirbio.mobileprogproject.db.utils.DatabaseInitializer;

public class MemoViewActivity extends AppCompatActivity implements OnItemClickListener{

    private static final String TAG = "MemoViews";

    RecyclerView rvMemo;
    List<MemoItem> memoList;

    MemoAdapter adapter;

    AppDatabase db;

    String userId;
    String userName;

    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);

        rvMemo = findViewById(R.id.recyclerView);
        welcomeText = findViewById(R.id.welcomeText);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        welcomeText.setText("Welcome, "+userName+"!");

        UserManagement.SetCurrentUserID(userId);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        //testPopulateData(db);

        // Initialize contacts
        memoList = db.memoModel().loadMemoByUID(UserManagement.GetCurrentUserID());
        // Create adapter passing in the sample user data
        adapter = new MemoAdapter(memoList,this);
        // Attach the adapter to the recyclerview to populate items
        rvMemo.setAdapter(adapter);
        // Set layout manager to position the items
        rvMemo.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==NewMemoActivity.CREATE_MEMO && resultCode== Activity.RESULT_OK) {
            Integer updateID=data.getIntExtra("updateId",0);
            //Log.i(TAG,"ticket:"+ticket);
            memoList = db.memoModel().loadMemoByUID(UserManagement.GetCurrentUserID());
            Log.i(TAG,"Update Memolist at "+updateID);
            Log.i(TAG,"Memolist total size is "+memoList.size());
            adapter.RefreshItemList(memoList);
            adapter.notifyItemInserted(updateID);
            Toast.makeText(this, "The memo has been added successfully.", Toast.LENGTH_SHORT).show();

        }

        if (requestCode==EditMemoActivity.EDIT_MEMO && resultCode== Activity.RESULT_OK) {
            Integer updateID=data.getIntExtra("updateId",0);
            Integer operationType = data.getIntExtra("operation",0);
            if(operationType==0) { //Update
                memoList = db.memoModel().loadMemoByUID(UserManagement.GetCurrentUserID());
                Log.i(TAG, "Update Memolist at " + updateID);
                Log.i(TAG, "Memolist total size is " + memoList.size());
                adapter.RefreshItemList(memoList);
                adapter.notifyItemChanged(updateID);
                Toast.makeText(this, "The memo has been updated successfully.", Toast.LENGTH_SHORT).show();
            }else if(operationType==1){ //Remove
                memoList = db.memoModel().loadMemoByUID(UserManagement.GetCurrentUserID());
                Log.i(TAG, "Remove Memolist at " + updateID);
                Log.i(TAG, "Memolist total size is " + memoList.size());
                adapter.RefreshItemList(memoList);
                //adapter.notifyItemRemoved(updateID);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "The memo has been deleted successfully.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "ERROR: Invalid Data Operation.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void addNewData(View v){
        Intent intent = new Intent(this,NewMemoActivity.class);
        startActivityForResult(intent,NewMemoActivity.CREATE_MEMO);
    }

    private void testPopulateData(AppDatabase db){
        DatabaseInitializer.testPolulateData(db);
    }

    @Override
    public void onItemClick(View view,int type,int position, UUID memoid) {
        if(type==0){
            //Toast.makeText(this, "Clicked to view "+memoid, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MemoDetailViewActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("memoId",memoid.toString());
            //startActivity(intent);
            startActivityForResult(intent,EditMemoActivity.EDIT_MEMO);
        }else{
            //Toast.makeText(this, "Clicked to edit "+memoid, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,EditMemoActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("memoId",memoid.toString());
            startActivityForResult(intent,EditMemoActivity.EDIT_MEMO);
        }

    }


    //private
}

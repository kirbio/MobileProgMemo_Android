package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import th.ac.chula.kirbio.mobileprogproject.db.AppDatabase;
import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;
import th.ac.chula.kirbio.mobileprogproject.db.utils.DatabaseInitializer;

public class NewMemoActivity extends AppCompatActivity {

    public static final int CREATE_MEMO = 777;

    AppDatabase db;

    EditText et_name;
    EditText et_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        et_name = findViewById(R.id.nameTF);
        et_detail = findViewById(R.id.detailTF);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
    }

    public void goBack(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void submit(View v){
        /*MemoItem memo = new MemoItem();
        memo.userId =
        db.memoModel().insertMemo();*/
        if(et_name.getText().toString().trim().length() == 0){
            Toast.makeText(this, "Memo name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseInitializer.addMemo(db,UserManagement.GetCurrentUserID(),et_name.getText().toString(),et_detail.getText().toString());

        Intent data=new Intent();
        data.putExtra("updateId",db.memoModel().countTotalMemoNumber());
        setResult(Activity.RESULT_OK, data);

        finish();
    }
}

package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import th.ac.chula.kirbio.mobileprogproject.db.AppDatabase;
import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;
import th.ac.chula.kirbio.mobileprogproject.db.utils.DatabaseInitializer;

public class EditMemoActivity extends AppCompatActivity {

    public static final int EDIT_MEMO = 420;

    AppDatabase db;

    EditText et_name;
    EditText et_detail;

    UUID memoId;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        et_name = findViewById(R.id.nameTF);
        et_detail = findViewById(R.id.detailTF);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        Intent intent = getIntent();
        memoId = UUID.fromString(intent.getStringExtra("memoId"));
        position = intent.getIntExtra("position",0);

        //Log.i("TestEdit","The memoId is"+memoId);
        //Log.i("TestEdit","The position is"+position);

        MemoItem memo = db.memoModel().loadMemoById(memoId);

        et_name.setText(memo.name);
        et_detail.setText(memo.detail);
    }

    public void goBack(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void submit(View v){
        if(et_name.getText().toString().trim().length() == 0){
            Toast.makeText(this, "Memo name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //DatabaseInitializer.addMemo(db,UserManagement.GetCurrentUserID(),et_name.getText().toString(),et_detail.getText().toString());

        db.memoModel().updateMemo(memoId,et_name.getText().toString(),et_detail.getText().toString());

        Intent data=new Intent();
        data.putExtra("updateId",position);
        data.putExtra("operation",0);
        setResult(Activity.RESULT_OK, data);

        finish();
    }

    public void delete(View v){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        db.memoModel().deleteMemoById(memoId);
                        Intent data=new Intent();
                        data.putExtra("updateId",position);
                        data.putExtra("operation",1);
                        setResult(Activity.RESULT_OK, data);

                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}

package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

import th.ac.chula.kirbio.mobileprogproject.db.AppDatabase;
import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;

public class MemoDetailViewActivity extends AppCompatActivity {

    UUID memoId;
    Integer position;

    AppDatabase db;

    TextView header;
    TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail_view);

        header = findViewById(R.id.textName);
        detail = findViewById(R.id.textDetail);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        Intent intent = getIntent();
        memoId = UUID.fromString(intent.getStringExtra("memoId"));
        position = intent.getIntExtra("position",0);

        MemoItem memo = db.memoModel().loadMemoById(memoId);

        header.setText(memo.name);

        String memodetail = memo.detail;

        if(memodetail.trim().length() == 0){
            memodetail = "This memo has no detail.";
        }
        detail.setText(memodetail);
    }

    public void goBack(View v){
        finish();
    }

    public void editMemo(View v){
        Intent intent = new Intent(this,EditMemoActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("memoId",memoId.toString());
        //intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivityForResult(intent,EditMemoActivity.EDIT_MEMO);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==EditMemoActivity.EDIT_MEMO){
            if(resultCode== Activity.RESULT_OK) {
                Intent data2 = new Intent();
                data2.putExtra("updateId", data.getIntExtra("updateId", 0));
                data2.putExtra("operation", data.getIntExtra("operation", 0));
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        }
    }
}

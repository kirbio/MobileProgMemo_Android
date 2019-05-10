package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SSODemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClick(View view) {
        Intent intent=new Intent(this,ChulaSSOActivity.class);
        startActivityForResult(intent,ChulaSSOActivity.LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ChulaSSOActivity.LOGIN && resultCode== Activity.RESULT_OK) {
            // Ticket can be reuse until expired.
            String ticket=data.getStringExtra("ticket");
            Log.i(TAG,"ticket:"+ticket);
            HelperTask helperTask=new HelperTask();
            helperTask.execute(ticket);


        }
    }

    void startApplication(String userid,String username){
        Intent intent = new Intent(this,MemoViewActivity.class);
        intent.putExtra("userId",userid);
        intent.putExtra("userName",username);
        startActivity(intent);
        finish();
    }

    class HelperTask extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String ticket=strings[0];
            String json = ChulaSSOHelper.serviceValidation(ticket);
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {
                JSONObject user = new JSONObject(json);
                //txtView.setText(user.getString("username")+"\n"+json);

                startApplication(user.getString("username"),user.getString("firstname")+" "+user.getString("lastname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

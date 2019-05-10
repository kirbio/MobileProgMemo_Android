package th.ac.chula.kirbio.mobileprogproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Set;

public class ChulaSSOActivity extends AppCompatActivity {

    private static final String TAG = "ChulaSSO";

    public static final int LOGIN = 100;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chula_sso);

        webView=findViewById(R.id.webViewSSO);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                //Log.i(TAG, uri.getQuery());
                //Log.i(TAG, uri.getScheme());
                if (uri.getScheme().toLowerCase().startsWith("chulasso")) {
                    Log.i(TAG,uri.getScheme());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processUri(uri);
                        }
                    });

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.loadUrl("https://account.it.chula.ac.th/login?serviceName=MobileProj&service=ChulaSSO://");
    }

    private void processUri(Uri uri) {

        // For debug
        Set<String> args = uri.getQueryParameterNames();
        for(String arg : args) {
            Log.d(TAG,arg+":"+uri.getQueryParameter(arg));
        }

        String ticket=uri.getQueryParameter("ticket");

        Intent data=new Intent();
        data.putExtra("ticket",ticket);
        setResult(Activity.RESULT_OK, data);
        finish();

    }

}

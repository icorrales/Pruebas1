package dia.upm.cconvexo.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dia.upm.cconvexo.R;
import dia.upm.cconvexo.gestores.GestorAlgoritmos;

/**
 * Created by ivan on 15/12/14.
 */
public class HelpWebViewActivity extends Activity {

    private WebView webView;
    private Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_tmp);

        webView = (WebView) findViewById(R.id.webview_parent);
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);

                if(progress == 100)
                    activity.setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("file:///android_asset/ayuda_usuario.html");
        webView.getProgress();
    }
}

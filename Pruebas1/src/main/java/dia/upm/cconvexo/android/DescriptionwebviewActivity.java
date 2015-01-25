package dia.upm.cconvexo.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dia.upm.cconvexo.gestores.GestorAlgoritmos;
import dia.upm.cconvexo.R;

public class DescriptionwebviewActivity extends Activity {

    private WebView webView;
    private DescriptionwebviewActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descriptionwebview);

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
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
                Log.d(DescriptionwebviewActivity.class.getName(),"Error encontrado");
            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.d(DescriptionwebviewActivity.class.getName(),"Pagina comienzo");

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.d(DescriptionwebviewActivity.class.getName(),"Pagina Final");
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        webView.loadUrl("http://www.google.es");
//        webView.reload();
        String footNote=GestorAlgoritmos.getInstancia().getAlgoritmo(GestorAlgoritmos.getInstancia().getAlgSelected()).getfootNote();
        webView.loadUrl("file:///android_asset/teoria.html#"+footNote);
//        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//        webView.loadData(summary, "text/html", null);

        webView.getProgress();

//        webView.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

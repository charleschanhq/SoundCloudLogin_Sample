package com.example.soundcloudloginsample;

import java.io.IOException;
import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	private WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWebView=(WebView) findViewById(R.id.webView);
	
	try {
        InputStream is = getAssets().open("soundcloud.html");

        String data = getResultFromStream(is);

        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setPluginsEnabled(true);

        MyWebChromeClient chromeClient = new MyWebChromeClient();
        MyWebViewClient webViewClient = new MyWebViewClient();

        mWebView.setWebChromeClient(chromeClient);
        mWebView.setWebViewClient(webViewClient);

        mWebView.loadData(data, "text/html","UTF-8");
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}   
public class MyWebViewClient extends WebViewClient{     
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Log.e("shouldOverrideUrlLoading","URL: "+url);

        if(url.startsWith("http://connect.soundcloud.com/examples/callback.html") && url.contains("access_token")){
            Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
        }           
        return super.shouldOverrideUrlLoading(view, url);
    }       
}

public class MyWebChromeClient extends WebChromeClient{    

    public boolean onJsAlert(WebView view, String url, String message,
            JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }       
}

private synchronized String getResultFromStream(InputStream stream)
        throws Exception {

    StringBuffer buffer = new StringBuffer();
    int ch = 0;
    while ((ch = stream.read()) != -1)
        buffer.append((char) ch);
    String result = buffer.toString().trim();
    return result;
}
	
	/* @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}   */
}

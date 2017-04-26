package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

public class WebViewManager {

    public static Boolean loadFromURL(WebView mWebView, String url, Context mContext)
            throws IOException {

        if (!validateURL(url, mContext)) {
            mWebView.setVisibility(View.INVISIBLE);
            return false;
        }

        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);

        return true;
    }

    private static Boolean validateURL(String url, Context mContext) throws IOException {

        try {

            ConnectivityManager connMgr =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr.getActiveNetworkInfo() == null) {
                return false;
            }

            if (!URLUtil.isValidUrl(url)) {
                return false;
            }

            return true;
        } catch (Exception exception) {
            Toast.makeText(mContext, "Error while checking URL!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}



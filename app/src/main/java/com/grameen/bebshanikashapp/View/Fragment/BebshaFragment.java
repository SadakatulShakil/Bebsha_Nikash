package com.grameen.bebshanikashapp.View.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.grameen.bebshanikashapp.R;

public class BebshaFragment extends Fragment {

    private WebView web;
    private ProgressBar progressBar;
    public BebshaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bebsha, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        web = (WebView) view.findViewById(R.id.webview);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        web.setWebViewClient(new MyWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setDatabaseEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowContentAccess(true);
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl("https://bebsha.com/login");

        web.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
                    web.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    public class MyWebClient extends WebViewClient {


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.GONE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/error.html");
        }

    }


}
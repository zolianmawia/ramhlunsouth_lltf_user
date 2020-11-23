package com.ramhluns.lltf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutApp extends Fragment {
    public WebView aboutWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

  View view = inflater.inflate(R.layout.about_appfragment,container, false);

        aboutWebView = (WebView)view.findViewById(R.id.aboutapp);
        aboutWebView.setWebViewClient(new MyWebViewClient());
        aboutWebView.getSettings().setAllowFileAccess(true);
        aboutWebView.getSettings().setDomStorageEnabled(true);
        aboutWebView.loadUrl("file:///android_asset/about.html");
        return view;

    }
    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }
}

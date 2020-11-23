package com.ramhluns.lltf;

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

public class Hruaitute extends Fragment {
    public WebView hWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hruaitute, container, false);

        hWebView = (WebView)view.findViewById(R.id.hruaitute);
        hWebView.setWebViewClient(new MyWebViewClient());
        hWebView.getSettings().setAllowFileAccess(true);
        hWebView.getSettings().setDomStorageEnabled(true);
        hWebView.loadUrl("file:///android_asset/hruaitute.html");
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

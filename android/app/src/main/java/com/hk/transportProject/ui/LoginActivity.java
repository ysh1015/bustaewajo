package com.hk.transportProject.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.hk.transportProject.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webView = findViewById(R.id.webView);

        // WebView 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 사용 허용
        webSettings.setDomStorageEnabled(true); // DOM 스토리지 사용 허용

        // WebView 내에서 링크 열기
        webView.setWebViewClient(new WebViewClient());

        // 스프링 부트 로그인 페이지 URL 로드
        webView.loadUrl("http://10.0.2.2:8085/login");
    }
}




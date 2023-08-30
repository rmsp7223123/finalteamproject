package com.example.finalteamproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalteamproject.R;

public class SearchAddress extends AppCompatActivity {

    private WebView webView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        // WebView 초기화
        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.daum_webview);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());

        // webview url load. php 파일 주소
        webView.loadUrl("http://192.168.0.33:8080/cloud/address");

    }


    private class AndroidBridge{
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("Args", "arg1 : " + arg1 + ", arg2 : " + arg2 + ", arg3 : " + arg3);
                    Intent intent = new Intent();
                    intent.putExtra("arg1",arg1);
                    intent.putExtra("arg2",arg2);
                    intent.putExtra("arg3",arg3);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }
}

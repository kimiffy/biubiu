package com.kimiffy.cn.biubiu.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.annotation.MainThread;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Created by kimiffy on 2019/3/22.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.wb)
    WebView wb;
//    @BindView(R.id.button_1)
//    Button button1;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
//        wb.addJavascriptInterface(new JsInterRation(), "android");
        wb.setWebViewClient(new WebViewController());
        WebSettings webSettings = wb.getSettings();
        //设置为可调用js方法*
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        wb.setWebChromeClient(new MyWebChromeClient());     //optional, for show console and alert
        wb.loadUrl("file:///android_asset/challengeList.html");
    }


    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d("webActivity", cm.message() + " -- From line "
                    + cm.lineNumber() + " of "
                    + cm.sourceId() );
            return true;
        }
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(WebActivity.this, message, Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    @Override
    protected void initUI(Bundle savedInstanceState) {

    }
//    @OnClick({R.id.button_1})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.button_1:
//                wb.loadUrl("JavaScript:show()");
//                break;
//
//        }
//    }

//    public class JsInterRation {
//        @JavascriptInterface
//        public String back() {
//            return "js 调用安卓方法 并返回";
//        }
//    }


    @Override
    protected void onDestroy() {
        if( wb!=null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再destory()
            ViewParent parent = wb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wb);
            }
            wb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wb.getSettings().setJavaScriptEnabled(false);
            wb.clearHistory();
            wb.loadUrl("about:blank");
            wb.removeAllViews();
            wb.destroy();
        }
        super.onDestroy();
    }
}

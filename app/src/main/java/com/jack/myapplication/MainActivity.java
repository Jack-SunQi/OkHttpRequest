package com.jack.myapplication;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jack.ok_lib.OkHttpManager;
import com.jack.ok_lib.callback.DownloadCallback;
import com.jack.ok_lib.callback.GsonCallback;
import com.jack.ok_lib.callback.MTWebSocketListener;
import com.jack.ok_lib.callback.StringCallback;
import com.jack.ok_lib.callback.UploadCallback;
import com.jack.ok_lib.request.IRequest;
import com.jack.ok_lib.request.ISocketRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void post(View view) {

        Map<String, String> header = new HashMap<>();

        OkHttpManager
                .post()
                .header(header)
                .url("url")
                .params("key", "value")
                .tag("tag")
                .setCallback(new StringCallback() {
                    @Override
                    public void onSuccess(int code, String result) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    public void get(View view) {
        OkHttpManager
                .get()
                .url("url")
                .tag("tag")
                .setCallback(new GsonCallback<TestBean>() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onSuccess(int code, TestBean response) {

                    }
                });
    }

    public void upload(View view) {

        File uploadFile = new File("your file path");

        Map<String, String> header = new HashMap<>();

        Map<String, String> params = new HashMap<>();

        IRequest request = OkHttpManager
                .upload()
                .file(uploadFile)
                .url("url")
                .header(header)
                .params("key", "value")
                .params("hello", "kitty")
                .setCallback(new UploadCallback() {
                    @Override
                    public void onProgress(long curr, long total, boolean isDone) {

                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

//        request.cancel();
    }

    public void download(View view) {

        Map<String, String> header = new HashMap<>();

        IRequest request = OkHttpManager
                .download()
                .fileName("FileName")
                .savePath("savePath")
                .header(header).url("url")
                .tag("tag")
                .setCallback(new DownloadCallback() {
                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void onProgress(long curr, long total) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    public void webSocket(View view) {
        ISocketRequest listener = OkHttpManager
                .webSocket()
                .url("url")
                .listener(new MTWebSocketListener() {
                    @Override
                    public void onMTOpen(WebSocket webSocket, Response response) {

                    }

                    @Override
                    public void onMTMessage(WebSocket webSocket, String text) {

                    }

                    @Override
                    public void onMTMessage(WebSocket webSocket, ByteString bytes) {

                    }

                    @Override
                    public void onMTClosing(WebSocket webSocket, int code, String reason) {

                    }

                    @Override
                    public void onMTClosed(WebSocket webSocket, int code, String reason) {

                    }

                    @Override
                    public void onMTFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {

                    }
                });
    }


}


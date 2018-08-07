package com.jack.ok_lib.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jack.ok_lib.builder.WebSocketParam;
import com.jack.ok_lib.utils.MyOkHttpClient;

import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

public class WebSocketRequest implements ISocketRequest {

    private WebSocketParam param;
    @Nullable
    private WebSocket webSocket;

    public WebSocketRequest(@NonNull WebSocketParam param) {
        this.param = param;
    }

    @Override
    public void connect() {
        if (param == null || TextUtils.isEmpty(param.getAttribute().getUrl()))
            return;
        Request request = new Request.Builder().url(param.getAttribute().getUrl()).build();

        webSocket = MyOkHttpClient.getClient().newWebSocket(request, param.getAttribute().getListener());
    }

    @Override
    public void close(final int code, final String reason) {
        assert webSocket != null;
        webSocket.close(code, reason);

    }

    @Override
    public void cancel() {
        assert webSocket != null;
        webSocket.cancel();

    }

    @Override
    public void send(final String text) {
        assert webSocket != null;
        webSocket.send(text);

    }

    @Override
    public void send(final ByteString bytes) {
        assert webSocket != null;
        webSocket.send(bytes);
    }

}

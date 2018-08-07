package com.jack.ok_lib.callback;

import android.support.annotation.Nullable;

import com.jack.ok_lib.utils.MainHandler;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public abstract class MTWebSocketListener extends WebSocketListener {

    @Override
    public void onOpen(final WebSocket webSocket, final Response response) {
        super.onOpen(webSocket, response);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTOpen(webSocket, response);
            }
        });
    }

    @Override
    public void onMessage(final WebSocket webSocket, final String text) {
        super.onMessage(webSocket, text);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTMessage(webSocket, text);
            }
        });
    }

    @Override
    public void onMessage(final WebSocket webSocket, final ByteString bytes) {
        super.onMessage(webSocket, bytes);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTMessage(webSocket, bytes);
            }
        });
    }

    @Override
    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
        super.onClosing(webSocket, code, reason);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTClosing(webSocket, code, reason);
            }
        });
    }

    @Override
    public void onClosed(final WebSocket webSocket, final int code, final String reason) {
        super.onClosed(webSocket, code, reason);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTClosed(webSocket, code, reason);
            }
        });
    }

    @Override
    public void onFailure(final WebSocket webSocket, final Throwable t, @Nullable final Response response) {
        super.onFailure(webSocket, t, response);
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                onMTFailure(webSocket, t, response);
            }
        });
    }

    public abstract void onMTOpen(WebSocket webSocket, Response response);

    public abstract void onMTMessage(WebSocket webSocket, String text);

    public abstract void onMTMessage(WebSocket webSocket, ByteString bytes);

    public abstract void onMTClosing(WebSocket webSocket, int code, String reason);

    public abstract void onMTClosed(WebSocket webSocket, int code, String reason);

    public abstract void onMTFailure(WebSocket webSocket, Throwable t, @Nullable Response response);
}

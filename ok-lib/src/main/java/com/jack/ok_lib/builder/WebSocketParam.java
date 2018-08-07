package com.jack.ok_lib.builder;

import com.jack.ok_lib.callback.MTWebSocketListener;
import com.jack.ok_lib.request.ISocketRequest;
import com.jack.ok_lib.request.WebSocketRequest;

public class WebSocketParam {

    private String url;
    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public WebSocketParam() {
    }

    private WebSocketParam(String url, MTWebSocketListener listener) {
        attribute = new Attribute(url, listener);
    }

    public WebSocketParam url(String url) {
        this.url = url;
        return this;
    }

    public ISocketRequest listener(MTWebSocketListener listener) {
        ISocketRequest request = new WebSocketRequest(new WebSocketParam(url, listener));
        request.connect();
        return request;
    }

    public static class Attribute{
        private String url;
        private MTWebSocketListener listener;

        private Attribute(String url, MTWebSocketListener listener) {
            this.url = url;
            this.listener = listener;
        }

        public String getUrl() {
            return url;
        }

        public MTWebSocketListener getListener() {
            return listener;
        }
    }


}

package com.jack.ok_lib.builder;

import android.support.annotation.NonNull;

import com.jack.ok_lib.callback.ICallback;
import com.jack.ok_lib.request.IRequest;
import com.jack.ok_lib.request.PostRequest;

import java.util.Map;

import okhttp3.RequestBody;


public class PostParam extends BaseParam<PostParam> {

    private RequestBody body;
    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public PostParam() {
    }

    public PostParam(String url, RequestBody body, Object tag, Map<String, String> header, ICallback callback) {
        attribute = new Attribute(url, body, tag, header, callback);
    }

    @Override
    public PostParam url(@NonNull String url) {
        this.url = url;
        return this;
    }

    public PostParam params(@NonNull RequestBody body) {
        this.body = body;
        return this;
    }

    @Override
    public PostParam tag(@NonNull Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostParam header(@NonNull Map<String, String> header) {
        this.header = header;
        return this;
    }

    public IRequest setCallback(@NonNull ICallback callback) {
        IRequest request = new PostRequest(new PostParam(url, body, tag, header, callback));
        request.exec();
        return request;
    }

    public static class Attribute {
        private String url;
        private RequestBody body;
        private Object tag;
        private Map<String, String> header;
        private ICallback callback;

        public Attribute(String url, RequestBody body, Object tag, Map<String, String> header, ICallback callback) {
            this.url = url;
            this.body = body;
            this.tag = tag;
            this.header = header;
            this.callback = callback;
        }

        public String getUrl() {
            return url;
        }

        public RequestBody getBody() {
            return body;
        }

        public Object getTag() {
            return tag;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public ICallback getCallback() {
            return callback;
        }
    }


}

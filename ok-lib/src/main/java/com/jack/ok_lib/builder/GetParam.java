package com.jack.ok_lib.builder;

import android.support.annotation.NonNull;

import com.jack.ok_lib.callback.ICallback;
import com.jack.ok_lib.request.GetRequest;

import java.util.Map;

public class GetParam extends BaseParam<GetParam> {

    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public GetParam() {

    }

    private ICallback callback;

    public GetParam(String url, Object tag, Map<String, String> header, ICallback callback) {
        attribute = new Attribute(url, tag, header, callback);
    }

    @Override
    public GetParam url(@NonNull String url) {
        this.url = url;
        return this;
    }

    @Override
    public GetParam tag(@NonNull Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public GetParam header(@NonNull Map<String, String> header) {
        this.header = header;
        return this;
    }

    public void setCallback(@NonNull ICallback callback) {
        this.callback = callback;
        new GetRequest(new GetParam(url, tag, header, callback)).exec();
    }

    public static class Attribute {
        private String url;
        private Object tag;
        private Map<String, String> header;
        private ICallback callback;

        public Attribute(String url, Object tag, Map<String, String> header, ICallback callback) {
            this.url = url;
            this.tag = tag;
            this.header = header;
            this.callback = callback;
        }

        public String getUrl() {
            return url;
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

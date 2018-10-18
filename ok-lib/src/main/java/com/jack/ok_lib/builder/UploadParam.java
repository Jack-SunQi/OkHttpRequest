package com.jack.ok_lib.builder;

import android.support.annotation.NonNull;

import com.jack.ok_lib.callback.UploadCallback;
import com.jack.ok_lib.request.IRequest;
import com.jack.ok_lib.request.UploadRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadParam extends BaseParam<UploadParam> {

    private File file;
    private Map<String, String> params;
    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public UploadParam() {
        this.params = new HashMap<>();
    }

    private UploadParam(String url, File file, Map<String, String> params, Map<String, String> header, Object tag, UploadCallback callback) {
        this.url = url;
        this.file = file;
        this.params = params;
        this.tag = tag;

        attribute = new Attribute(url, file, params, header, tag, callback);
    }

    public static class Attribute {
        private String url;
        private File file;
        private Map<String, String> param;
        private Map<String, String> header;
        private Object tag;
        private UploadCallback callback;

        public Attribute(String url, File file, Map<String, String> param, Map<String, String> header, Object tag, UploadCallback callback) {
            this.url = url;
            this.file = file;
            this.tag = tag;
            this.param = param;
            this.header = header;
            this.callback = callback;
        }

        public String getUrl() {
            return url;
        }

        public Object getTag() {
            return tag;
        }

        public File getFile() {
            return file;
        }

        public Map<String, String> getParam() {
            return param;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public UploadCallback getCallback() {
            return callback;
        }
    }

    @Override
    public UploadParam url(@NonNull String url) {
        this.url = url;
        return this;
    }

    @Override
    public UploadParam tag(@NonNull Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public UploadParam header(@NonNull Map<String, String> header) {
        this.header = header;
        return this;
    }

    public UploadParam file(@NonNull File file) {
        this.file = file;
        return this;
    }

    public UploadParam params(@NonNull String key, @NonNull String value) {
        params.put(key, value);
        return this;
    }

    public IRequest setCallback(@NonNull UploadCallback callback) {
        IRequest request = new UploadRequest(new UploadParam(url, file, params, header, tag, callback));
        request.exec();
        return request;
    }

}

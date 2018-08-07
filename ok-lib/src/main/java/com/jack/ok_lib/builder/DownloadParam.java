package com.jack.ok_lib.builder;

import android.support.annotation.NonNull;

import com.jack.ok_lib.callback.DownloadCallback;
import com.jack.ok_lib.request.DownloadRequest;
import com.jack.ok_lib.request.IRequest;

import java.util.Map;

public class DownloadParam extends BaseParam<DownloadParam> {

    private DownloadCallback callback;
    private String fileName;
    private String savePath;
    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public DownloadParam() {
    }

    public DownloadParam(String url, Object tag, Map<String, String> header, String fileName,
                         String savePath, DownloadCallback callback) {
        attribute = new Attribute(url, tag, header, fileName, savePath, callback);
    }

    @Override
    public DownloadParam url(@NonNull String url) {
        this.url = url;
        return this;
    }

    @Override
    public DownloadParam tag(@NonNull Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public DownloadParam header(@NonNull Map<String, String> header) {
        this.header = header;
        return this;
    }

    public DownloadParam fileName(@NonNull String fileName) {
        this.fileName = fileName;
        return this;
    }

    public DownloadParam savePath(@NonNull String savePath) {
        this.savePath = savePath;
        return this;
    }

    public IRequest setCallback(DownloadCallback callback) {
        this.callback = callback;
        IRequest request = new DownloadRequest(new DownloadParam(url, tag, header, fileName, savePath, callback));
        request.exec();

        return request;
    }

    public static class Attribute {
        private String url;
        private Object tag;
        private Map<String, String> header;
        private String fileName;
        private String savePath;
        private DownloadCallback callback;

        public Attribute(String url, Object tag, Map<String, String> header, String fileName, String savePath, DownloadCallback callback) {
            this.url = url;
            this.tag = tag;
            this.header = header;
            this.fileName = fileName;
            this.savePath = savePath;
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

        public String getFileName() {
            return fileName;
        }

        public String getSavePath() {
            return savePath;
        }

        public DownloadCallback getCallback() {
            return callback;
        }
    }

}

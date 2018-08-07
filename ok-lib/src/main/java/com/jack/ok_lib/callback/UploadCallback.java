package com.jack.ok_lib.callback;

public interface UploadCallback {

    void onProgress(long curr, long total, boolean isDone);
    void onSuccess();
    void onError(Exception e);
    void onCancel();
}

package com.jack.ok_lib.callback;

import java.io.File;

import okhttp3.Call;

public interface DownloadCallback {

    void onSuccess(File file);

    void onFailure(Call call, Exception e);

    void onProgress(long curr, long total);

    void onCancel();
}

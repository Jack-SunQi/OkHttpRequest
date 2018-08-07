package com.jack.ok_lib.callback;


import okhttp3.Response;

public interface ICallback {

    void onSuccess(Response response);

    void onError(Exception e);

    void onCancel();
}

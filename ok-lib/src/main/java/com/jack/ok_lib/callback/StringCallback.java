package com.jack.ok_lib.callback;

import com.jack.ok_lib.utils.MainHandler;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class StringCallback implements ICallback {

    @Override
    public void onSuccess(final Response response) {
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!response.isSuccessful())
                    onError(new IOException());
                else {
                    ResponseBody body = response.body();
                    if (body == null)
                        onError(new NullPointerException("result body is empty"));
                    else {
                        try {
                            String result = response.body().string();
                            int code = response.code();

                            onSuccess(code, result);

                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }
                }
            }
        });
    }

    public abstract void onSuccess(int code, String result);
}

package com.jack.ok_lib.request;

import android.text.TextUtils;

import com.jack.ok_lib.builder.GetParam;
import com.jack.ok_lib.utils.MyOkHttpClient;
import com.jack.ok_lib.utils.SQThreadPool;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest implements IRequest {

    private GetParam getParam;
    private Call call;

    public GetRequest(GetParam getParam) {
        this.getParam = getParam;
    }

    @Override
    public void exec() {
        if (getParam == null || TextUtils.isEmpty(getParam.getAttribute().getUrl()))
            throw new IllegalArgumentException("url cant be null");

        Request.Builder builder = new Request.Builder();
        builder.url(getParam.getAttribute().getUrl());

        if (getParam.getAttribute().getTag() != null)
            builder.tag(getParam.getAttribute().getTag());

        if (getParam.getAttribute().getHeader() != null) {
            Iterator<String> iterator = getParam.getAttribute().getHeader().keySet().iterator();
            while (iterator.hasNext()) {
                builder.addHeader(iterator.next(), getParam.getAttribute().getHeader().get(iterator.next()));
            }
        }

        final Request request = builder.build();

        SQThreadPool.getInstance(this.getClass().getSimpleName()).execute(new Runnable() {
            @Override
            public void run() {
                call = MyOkHttpClient.getClient().newCall(request);
                try {
                    final Response response = call.execute();

                    if (getParam.getAttribute().getCallback() == null)
                        return;

                            if (!response.isSuccessful()) {
                                getParam.getAttribute().getCallback().onError(new Exception("unsuccessful response"));
                            } else {
                                getParam.getAttribute().getCallback().onSuccess(response);
                            }
                } catch (final IOException e) {
                    e.printStackTrace();
                            getParam.getAttribute().getCallback().onError(e);
                }
            }
        });


    }

    @Override
    public void cancel() {
        if (call == null)
            return;

        call.cancel();
        if (getParam != null && getParam.getAttribute().getCallback() != null)
            getParam.getAttribute().getCallback().onCancel();
    }
}

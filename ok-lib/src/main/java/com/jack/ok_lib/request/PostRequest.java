package com.jack.ok_lib.request;

import android.text.TextUtils;

import com.jack.ok_lib.builder.PostParam;
import com.jack.ok_lib.utils.MyOkHttpClient;
import com.jack.ok_lib.utils.SQThreadPool;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class PostRequest implements IRequest {

    private static final String TAG = "PostRequest";

    private PostParam param;

    private Call call;

    public PostRequest(PostParam param) {
        this.param = param;
    }

    @Override
    public void exec() {
        if (TextUtils.isEmpty(param.getAttribute().getUrl()))
            throw new NullPointerException("url is empty");

        if (param.getAttribute().getBody() == null)
            throw new IllegalArgumentException("body is empty");


        SQThreadPool.getInstance(TAG).execute(new Runnable() {
            @Override
            public void run() {

                Request.Builder builder = new Request.Builder();
                builder.url(param.getAttribute().getUrl()).post(param.getAttribute().getBody());
                if (param.getAttribute().getTag() != null)
                    builder.tag(param.getAttribute().getTag());

                if (param.getAttribute().getHeader() != null) {
                    Iterator<String> iterator = param.getAttribute().getHeader().keySet().iterator();
                    while (iterator.hasNext()) {
                        builder.addHeader(iterator.next(), param.getAttribute().getHeader().get(iterator.next()));
                    }
                }

                Request request = builder.build();

                try {
                    call = MyOkHttpClient.getClient().newCall(request);

                    final Response response = call.execute();

                    if (param.getAttribute().getCallback() == null)
                        return;
                    if (response.isSuccessful())
                        param.getAttribute().getCallback().onSuccess(response);
                    else
                        param.getAttribute().getCallback().onError(new Exception("unsuccessful response"));


                } catch (IOException e) {
                    e.printStackTrace();
                    param.getAttribute().getCallback().onError(e);

                }
            }
        });
    }

    @Override
    public void cancel() {
        if (call == null)
            return;
        call.cancel();

        if (param != null && param.getAttribute().getCallback() != null)
            param.getAttribute().getCallback().onCancel();
    }
}

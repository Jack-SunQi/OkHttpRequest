package com.jack.ok_lib.request;

import com.jack.ok_lib.builder.UploadParam;
import com.jack.ok_lib.progress.UploadProgressBody;
import com.jack.ok_lib.utils.MainHandler;
import com.jack.ok_lib.utils.MimeType;
import com.jack.ok_lib.utils.MyOkHttpClient;
import com.jack.ok_lib.utils.SQThreadPool;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UploadRequest implements IRequest {

    private UploadParam uploadParam;
    private Call call;

    public UploadRequest(UploadParam uploadParam) {
        this.uploadParam = uploadParam;
    }

    @Override
    public void exec() {
        if (uploadParam.getAttribute().getFile() == null) {
            throw new IllegalArgumentException("file cant be null");
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("thumb", uploadParam.getAttribute().getFile().getName(),
                RequestBody.create(MediaType.parse(MimeType.getMIMEType(uploadParam.getAttribute().getFile())), uploadParam.getAttribute().getFile()));

        if (uploadParam.getAttribute().getParam() != null) {
            Iterator<String> iterator = uploadParam.getAttribute().getParam().keySet().iterator();
            while (iterator.hasNext()) {
                //添加其他参数
                builder.addFormDataPart(iterator.next(), uploadParam.getAttribute().getParam().get(iterator.next()));
            }
        }

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(uploadParam.getAttribute().getUrl()).post(new UploadProgressBody(builder.build(), new UploadProgressBody.UploadListener() {
            @Override
            public void onProgress(final long currLength, final long totalLength, final boolean isDone) {
                MainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (uploadParam.getAttribute().getCallback() != null)
                            uploadParam.getAttribute().getCallback().onProgress(currLength, totalLength, isDone);
                    }
                });

            }

            @Override
            public void onDone() {
                MainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (uploadParam.getAttribute().getCallback() != null)
                            uploadParam.getAttribute().getCallback().onSuccess();
                    }
                });

            }
        }));
        if (uploadParam.getAttribute().getTag() != null)
            requestBuilder.tag(uploadParam.getAttribute().getTag());

        final Request request = requestBuilder.build();

        SQThreadPool.getInstance(this.getClass().getSimpleName()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    call = MyOkHttpClient.getClient().newCall(request);
                    call.execute();
                } catch (final IOException e) {
                    e.printStackTrace();
                    MainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            uploadParam.getAttribute().getCallback().onError(e);
                        }
                    });

                }
            }
        });


    }

    @Override
    public void cancel() {
        if (call == null)
            return;
        call.cancel();

        if (uploadParam.getAttribute().getCallback() != null)
            uploadParam.getAttribute().getCallback().onCancel();

    }
}

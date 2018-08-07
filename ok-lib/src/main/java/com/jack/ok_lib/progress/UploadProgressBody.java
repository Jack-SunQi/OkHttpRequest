package com.jack.ok_lib.progress;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadProgressBody extends RequestBody {

    private final RequestBody requestBody;
    private BufferedSink bufferedSink;
    private final UploadListener listener;

    public interface UploadListener {
        void onProgress(long currLength, long totalLength, boolean isDone);

        void onDone();
    }

    public UploadProgressBody(RequestBody requestBody, UploadListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return requestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null)
            bufferedSink = Okio.buffer(sink(sink));

        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();

    }

    /**
     * 对source进行包装
     *
     * @param sink
     * @return
     */
    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {

            long bytesWrite = 0;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);

                bytesWrite += byteCount;

                boolean isDone = bytesWrite == contentLength();
                listener.onProgress(bytesWrite, contentLength(), isDone);

                if (isDone)
                    listener.onDone();

            }
        };
    }

}

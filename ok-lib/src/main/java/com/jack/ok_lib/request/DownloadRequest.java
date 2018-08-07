package com.jack.ok_lib.request;

import android.os.Environment;
import android.text.TextUtils;

import com.jack.ok_lib.builder.DownloadParam;
import com.jack.ok_lib.callback.DownloadCallback;
import com.jack.ok_lib.utils.MainHandler;
import com.jack.ok_lib.utils.MyOkHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sunqi on 2018/7/16.
 */

public class DownloadRequest implements IRequest {

    private DownloadParam param;
    private DownloadCallback downloadCallback;
    private String fileName;
    private String filePath;
    private volatile boolean isCanceled = false;
    private volatile boolean isDownloadSuccess = false;

    public DownloadRequest(DownloadParam param) {
        this.param = param;
        downloadCallback = param.getAttribute().getCallback();
        fileName = TextUtils.isEmpty(param.getAttribute().getFileName()) ? UUID.randomUUID().toString() : param.getAttribute().getFileName();

        filePath = TextUtils.isEmpty(param.getAttribute().getSavePath()) ? Environment.getExternalStorageDirectory().getAbsolutePath() : param.getAttribute().getSavePath();
    }

    @Override
    public void exec() {
        Request request = new Request.Builder().url(param.getAttribute().getUrl()).tag(param.getAttribute().getTag()).build();

        if (downloadCallback == null) {

            try {
                MyOkHttpClient.getClient().newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MyOkHttpClient.getClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {

                    MainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            downloadCallback.onFailure(call, e);
                        }
                    });

                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    if (response.code() != 200) {
                        /**
                         * 失败回调监听
                         */
                        MainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadCallback.onFailure(call, new Exception("wrong response code" + response.code()));
                            }
                        });
                        return;
                    }
                    //将返回结果转化为流，并写入文件
                    int len;
                    byte[] buf = new byte[2048];
                    InputStream inputStream = response.body().byteStream();
                    /**
                     * 写入本地文件
                     */
                    String responseFileName = getHeaderFileName(response);
                    File file = null;
                    /**
                     *如果服务器没有返回的话,使用自定义的文件名字
                     */
                    if (TextUtils.isEmpty(responseFileName)) {
                        file = new File(filePath, fileName);
                    } else {
                        file = new File(filePath, responseFileName);
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int curr = 0;
                    while ((len = inputStream.read(buf)) != -1 && !isCanceled) {
                        fileOutputStream.write(buf, 0, len);
                        curr += len;
                        final int finalCurr = curr;
                        MainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                assert response.body() != null;
                                downloadCallback.onProgress(finalCurr, response.body().contentLength());
                            }
                        });
                    }

                    final File finalFile = file;
                    MainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isCanceled) {
                                isDownloadSuccess = true;
                                downloadCallback.onSuccess(finalFile);
                            }
                        }
                    });

                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                }
            });
        }


    }

    @Override
    public void cancel() {
        if (isDownloadSuccess)
            return;

        isCanceled = true;
        MainHandler.post(new Runnable() {
            @Override
            public void run() {
                downloadCallback.onCancel();
            }
        });
    }

    /**
     * 解析文件头
     * Content-Disposition:attachment;filename=FileName.txt
     * Content-Disposition: attachment; filename*="UTF-8''%E6%9B%BF%E6%8D%A2%E5%AE%9E%E9%AA%8C%E6%8A%A5%E5%91%8A.pdf"
     */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split("; ");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }


}

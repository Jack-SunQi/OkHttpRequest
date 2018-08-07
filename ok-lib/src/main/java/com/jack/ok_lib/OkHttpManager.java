package com.jack.ok_lib;


import com.jack.ok_lib.builder.DownloadParam;
import com.jack.ok_lib.builder.GetParam;
import com.jack.ok_lib.builder.PostParam;
import com.jack.ok_lib.builder.UploadParam;
import com.jack.ok_lib.builder.WebSocketParam;

public class OkHttpManager {

    public static PostParam post() {
        return new PostParam();
    }

    public static UploadParam upload() {
        return new UploadParam();
    }

    public static WebSocketParam webSocket() {
        return new WebSocketParam();
    }

    public static GetParam get() {
        return new GetParam();
    }

    public static DownloadParam download() {
        return new DownloadParam();
    }
}

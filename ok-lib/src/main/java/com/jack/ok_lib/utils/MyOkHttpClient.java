package com.jack.ok_lib.utils;

import okhttp3.OkHttpClient;

public class MyOkHttpClient {

    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static void initClient(OkHttpClient client) {
        MyOkHttpClient.client = client;
    }
}

package com.jack.ok_lib.request;

import okio.ByteString;

public interface ISocketRequest {

    void connect();

    void close(int code, String reason);

    void cancel();

    void send(String text);

    void send(ByteString bytes);

}

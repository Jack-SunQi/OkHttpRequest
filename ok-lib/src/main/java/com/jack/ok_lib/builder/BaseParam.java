package com.jack.ok_lib.builder;

import android.support.annotation.NonNull;

import java.util.Map;


public abstract class BaseParam<T extends BaseParam> {

    protected String url;
    protected Object tag;
    protected Map<String, String> header;

    BaseParam() {
    }

    public abstract T url(@NonNull String url);

    public abstract T tag(@NonNull Object tag);

    public abstract T header(@NonNull Map<String, String> header);
}

package com.jack.ok_lib.callback;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class GsonCallback<T> implements ICallback {

    private Type type;

    public GsonCallback() {
        Type myClass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myClass;
        assert parameter != null;
        type = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    public abstract void onSuccess(int code, T response);

    @Override
    public void onSuccess(Response response) {
        Gson gson = new Gson();

        try {
            onSuccess(response.code(), (T) gson.fromJson(response.body().string(), type));
        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
        }
    }


}

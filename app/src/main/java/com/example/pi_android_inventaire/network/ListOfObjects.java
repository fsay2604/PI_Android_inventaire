package com.example.pi_android_inventaire.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

class ListOfSomething<X> implements ParameterizedType {

    private Class<?> wrapped;

    public ListOfSomething(Class<X> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {wrapped};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }

}
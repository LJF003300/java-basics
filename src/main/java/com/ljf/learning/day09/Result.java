package com.ljf.learning.day09;

public class Result<T> {
    private final T data;

    public Result(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
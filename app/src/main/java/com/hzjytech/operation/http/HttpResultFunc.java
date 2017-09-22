package com.hzjytech.operation.http;


import rx.functions.Func1;

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getStatusCode() != 200&&httpResult.getStatusCode()!=105) {
            throw new ApiException(httpResult);
        }

        return httpResult.getResults();
    }
}

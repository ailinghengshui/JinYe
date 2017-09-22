package com.hzjytech.operation.http;


public class HttpResult<T> implements JijiaRZData {
    private int statusCode;
    private String statusMsg;
    private T results;
    private boolean success;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                ", results=" + results +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean isEmpty() {
        return results==null;
    }
}

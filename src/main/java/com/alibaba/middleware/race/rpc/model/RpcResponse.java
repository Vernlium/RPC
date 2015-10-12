package com.alibaba.middleware.race.rpc.model;

import java.io.Serializable;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcResponse implements Serializable{

    private static final long serialVersionUID = -4364536436151723421L;

    private String errorMsg;

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    private Throwable error;
    private Object appResponse;

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setAppResponse(Object appResponse) {
        this.appResponse = appResponse;
    }

    public Object getAppResponse()
    {
        return this.appResponse;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public boolean isError() {
        return (this.errorMsg != null);
    }
}

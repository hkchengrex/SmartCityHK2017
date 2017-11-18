package model;

import java.util.HashMap;
import java.util.List;

import types.ErrorHandler;

/**
 * Created by fung on 2017/3/4.
 */

public class ModelResponse<T> {

    private T responseBody ;
    private ErrorHandler error;
    private List<T> responseListBody;

    public HashMap<String, String> getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(HashMap<String, String> responseHeader) {
        this.responseHeader = responseHeader;
    }

    private HashMap<String,String> responseHeader;

    public ModelResponse(T responseBody){
        this.responseBody = responseBody;
        this.error = null;
    }

    public ModelResponse(List<T> responseListBody){
        this.responseListBody = responseListBody;
        this.error = null;
        this.responseBody = null;
    }

    public ModelResponse(ErrorHandler errorHandler){
        this.error = errorHandler;
        this.responseBody = null;
    }

    public ModelResponse(int errorState){
        this.error = new ErrorHandler(errorState) ;
        this.responseBody = null;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public List<T> getResponseListBody() {
        return responseListBody;
    }

    public ErrorHandler getError() {
        return error;
    }

}

package types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fung on 2017/3/4.
 */

public class ErrorHandler {

    @SerializedName("code")
    String errorMessage;

    public String getErrorM() {
        return errorM;
    }

    public void setErrorM(String errorM) {
        this.errorM = errorM;
    }

    @SerializedName("message")
    String errorM;

    public ErrorHandler(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorHandler(int errorState) {
        switch (errorState){
            case -1 : this.errorMessage = "Cannot connect to Network" ; break;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

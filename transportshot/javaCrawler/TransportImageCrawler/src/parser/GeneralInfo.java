package parser;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import types.Shot;

/**
 * Created by cheng on 28/1/2017.
 */

public class GeneralInfo implements Serializable {

    private String errCode;
    private String msg;
    
    @SerializedName("data")
    private ArrayList<Shot> shot;

    public GeneralInfo(String errCode,String msg, ArrayList<Shot> shot){
        this.setErrCode(errCode);
        this.setMsg(msg);
        this.shot = shot;
    }

    public ArrayList<Shot> getShot() {
        return shot;
    }
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


}

package com.library.library.Exception;

import lombok.Data;

@Data
public class IdAlreadyExists extends Exception{
    private String errorMsg;
    private String reasonCode;
    public IdAlreadyExists(String errorMsg,String reasonCode){
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.reasonCode = reasonCode;
    }
}

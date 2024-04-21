package com.library.library.Exception;

import lombok.Data;

@Data
public class IdNotFoundException extends Exception{
    private String errorMsg;
    private String reasonCode;
    public IdNotFoundException(String errorMsg,String reasonCode){
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.reasonCode = reasonCode;
    }
}

package com.azanini.thenotelist.entities;

import com.azanini.thenotelist.enums.BusinessErrorCode;

public class BusinessException extends RuntimeException {

    public BusinessErrorCode businessErrorCode;

    public BusinessException(BusinessErrorCode ex) {
        this.businessErrorCode = ex;
    }
}

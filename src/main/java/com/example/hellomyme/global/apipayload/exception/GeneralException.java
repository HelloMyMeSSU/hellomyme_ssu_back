package com.example.hellomyme.global.apipayload.exception;

import com.example.hellomyme.global.apipayload.code.BaseCode;
import com.example.hellomyme.global.apipayload.code.StatusReasonDTO;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseCode code;

    public GeneralException(BaseCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public StatusReasonDTO getErrorReason() {
        return new StatusReasonDTO(this.code.getStatus(), false, this.code.getCode(), this.code.getMessage());
    }
}
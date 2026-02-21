package com.seventeamproject.api.customer.entity;

import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.MemberException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CustomerStatus {
    PENDING,
    ACTIVATED,
    DEACTIVATED,
    SUSPENDED;

    public static CustomerStatus fromStat(String stat){
        for(CustomerStatus status : values()){
            if(stat.equals(String.valueOf(status))){
                return status;
            }
        }
        throw new MemberException(ErrorCode.STATUS_NOT_FOUND);
    }
}

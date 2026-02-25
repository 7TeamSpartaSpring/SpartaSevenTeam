package com.seventeamproject.api.customer.enums;

import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.MemberException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지");

    private final String title;

    //String으로 입력받은 값을 enum과 비교해서 일치하는 enum을 반환
    public static CustomerStatus fromStat(String stat){
        for(CustomerStatus status : values()){
            if(stat.equals(String.valueOf(status))){
                return status;
            }
        }
        throw new MemberException(ErrorCode.STATUS_NOT_FOUND);
    }
}

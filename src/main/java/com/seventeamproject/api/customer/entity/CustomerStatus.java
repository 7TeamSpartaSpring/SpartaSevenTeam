package com.seventeamproject.api.customer.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CustomerStatus {
    PENDING(0),
    ACTIVATED(1),
    DEACTIVATED(2),
    SUSPENDED(3);

    private final int code;

    CustomerStatus(int code){
        this.code = code;
    }

    public static CustomerStatus fromStatCode(int statCode){
        for(CustomerStatus status : values()){
            if(status.code == statCode){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + statCode);

//        return Arrays.stream(values())
//                .filter(s -> s.code == statCode)
//                .findFirst()
//                .orElseThrow(() ->
//                        new IllegalArgumentException("Invalid status code"));
    }
}

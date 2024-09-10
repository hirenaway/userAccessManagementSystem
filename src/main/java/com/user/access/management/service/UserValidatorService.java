package com.user.access.management.service;

import com.user.access.management.model.Users;

public class UserValidatorService {

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber.startsWith("+91") && phoneNumber.length() == 13;
    }

    public  boolean isValid(Users users){
        return isEmailValid(users.getEmail()) &&
                isPhoneNumberValid(users.getPhoneNumber());
    }
}

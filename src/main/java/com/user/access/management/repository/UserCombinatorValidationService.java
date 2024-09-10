package com.user.access.management.repository;

import com.user.access.management.model.Users;

import java.util.function.Function;

import com.user.access.management.repository.UserCombinatorValidationService.ValidationResult;

import static com.user.access.management.repository.UserCombinatorValidationService.ValidationResult.*;

public interface UserCombinatorValidationService extends Function<Users, ValidationResult> {

    static UserCombinatorValidationService isEmailValid(){
        return customer -> customer.getEmail().contains("@") ? SUCCESSFULLY_VALID : INVALID_EMAIL_ID;
    }

    static UserCombinatorValidationService isPhoneNumberValid(){
        return customer -> customer.getPhoneNumber().startsWith("+91") && customer.getPhoneNumber().length() == 13 ?
                SUCCESSFULLY_VALID : INVALID_PHONE_NUMBER;
    }

    default UserCombinatorValidationService and(UserCombinatorValidationService validate){
        return user -> {
            ValidationResult result= this.apply(user);
            return result.equals(SUCCESSFULLY_VALID) ? validate.apply(user) : result;
        };
    }

    enum ValidationResult {
        SUCCESSFULLY_VALID,
        INVALID_EMAIL_ID,
        INVALID_PHONE_NUMBER
    }


}

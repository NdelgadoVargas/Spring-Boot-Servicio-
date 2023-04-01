package com.pruebatecnica.utils.validation;
import org.springframework.stereotype.Component;

@Component
public class ValidationRegex {

    public boolean emailFormat(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (!email.matches(emailRegex)) {
            return false;
        }
        return true;
    }

    public boolean passwordFormat(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d\\d)[A-Za-z\\d@$!%*?&]{8,}$";

        if (!password.matches(passwordRegex)) {
            return false;
        }

        return true;

    }

}

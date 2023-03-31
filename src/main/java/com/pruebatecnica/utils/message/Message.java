package com.pruebatecnica.utils.message;

public class Message {
    
    public static final String INVALID_EMAIL_FORMAT = "Formato de correo electrónico inválido";
    public static final String INVALID_PASSWORD_FORMAT = "Formato de clave inválido";
    public static final String EMAIL_REQUIRED = "El correo electrónico es obligatorio";
    public static final String EMAIL_ALREADY_REGISTERED = "El correo ya se encuentra registrado";
    public static final String USER_CREATED_SUCCESSFULLY = "Usuario creado exitosamente";
    public static final String ERROR_CREATING_USER = "No se pudo crear el usuario";
    public static final String ERROR_CREATING_USER_EXCEPTION = "No se pudo crear el usuario exception:";
    public static final String ERROR_INVALID_TOKEN = "Token invalido";
    public static final String ERROR_USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_FOUND_SUCCESS = "Usuario fue encontrado";
    
    
    public static String invalidEmailFormat() {
        return INVALID_EMAIL_FORMAT;
    }
    
    public static String invalidPasswordFormat() {
        return INVALID_PASSWORD_FORMAT;
    }
    
    public static String emailRequired() {
        return EMAIL_REQUIRED;
    }
    
    public static String emailAlreadyRegistered() {
        return EMAIL_ALREADY_REGISTERED;
    }
    
    public static String userCreatedSuccessfully() {
        return USER_CREATED_SUCCESSFULLY;
    }
    
    public static String errorCreatingUser() {
        return ERROR_CREATING_USER;
    }

    public static String errorCreatingUserException() {
        return ERROR_CREATING_USER_EXCEPTION;
    }

    public static String invalidToken() {
        return ERROR_INVALID_TOKEN;
    }
    public static String errorUserNotFound() {
        return ERROR_USER_NOT_FOUND;
    }

    public static String userFoundSucces() {
        return USER_FOUND_SUCCESS;
    }
    
}

package com.pruebatecnica.utils.message;

public class Message {
    
    public static final String INVALID_EMAIL_FORMAT = "Formato de correo electrónico inválido";
    public static final String INVALID_PASSWORD_FORMAT = "Formato de clave inválido";
    public static final String EMAIL_REQUIRED = "El correo electrónico es obligatorio";
    public static final String EMAIL_ALREADY_REGISTERED = "El correo ya se encuentra registrado";
    public static final String USER_CREATED_SUCCESSFULLY = "Usuario creado exitosamente";
    public static final String ERROR_CREATING_USER = "No se pudo crear el usuario";
    public static final String ERROR_INVALID_TOKEN = "Token invalido";
    public static final String TOKEN_SUCCESS = "Token valido";
    public static final String ERROR_USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_FOUND_SUCCESS = "Usuario fue encontrado";
    public static final String USER_DELETE_SUCCESS = "Usuario fue eliminado";
    public static final String USER_UPDATE_SUCCESS = "Usuario fue actualizado";
    public static final String ERROR_CREATING_USER_EXCEPTION = "Error interno no se pudo crear el usuario: ";
    public static final String ERROR_SEARCH_USER_EXCEPTION = "Error interno no se pudo encontrar el ususario: ";
    public static final String ERROR_DELETE_USER_EXCEPTION = "Error interno no se pudo eliminar el usuario: ";
    public static final String ERROR_UPDATE_USER_EXCEPTION = "Error interno no se pudo modificar el usuario: ";

    
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

    public static String invalidToken() {
        return ERROR_INVALID_TOKEN;
    }
    public static String tokenSuccess() {
        return TOKEN_SUCCESS;
    }
    public static String errorUserNotFound() {
        return ERROR_USER_NOT_FOUND;
    }

    public static String userFoundSuccess() {
        return USER_FOUND_SUCCESS;
    }
    public static String userDeleteSuccess() {
        return USER_DELETE_SUCCESS;
    }
    public static String userUpdateSuccess() {
        return USER_UPDATE_SUCCESS;
    }
    public static String errorCreatingUserException() {
        return ERROR_CREATING_USER_EXCEPTION;
    }
    public static String errorSearchUserException() {
        return ERROR_SEARCH_USER_EXCEPTION;
    }
    public static String errorDeleteUserException() {
        return ERROR_DELETE_USER_EXCEPTION;
    }
    public static String errorUpdateUserException() {
        return ERROR_UPDATE_USER_EXCEPTION;
    }
}

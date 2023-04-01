package com.pruebatecnica.utils.action;

public class ActionParameterized {
    
    public final String CREATE = "CREATE";
    public final String SEARCH_USER = "SEARCH_USER";
    public final String DELETE_USER = "DELETE_USER";
    public final String UPDATE_USER = "UPDATE_USER";


    public String create() {
        return CREATE;
    }
    public String search_user() {
        return SEARCH_USER;
    }
    public String delete_user() {
        return DELETE_USER;
    }
    public String update_user() {
        return UPDATE_USER;
    }
    

}

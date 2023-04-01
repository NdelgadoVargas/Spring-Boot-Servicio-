package com.pruebatecnica.utils.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public String dateFormat(Date date){

        Date creationDate = date;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String creationDateFormat = formato.format(creationDate);

        return creationDateFormat;
    }
    
}

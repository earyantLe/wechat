package com.ctsig.common.convrter;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements WebBindingInitializer {

//    @Override
//    public void initBinder(WebDataBinder webDataBinder) {
//
//    }

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df,  true));
    }  
  
}  
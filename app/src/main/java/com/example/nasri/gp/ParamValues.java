package com.example.nasri.gp;

/**
 * Created by Bors on 8/31/2018.
 */

public class ParamValues {

    String parametre ;
    String value ;
    int fieldId ;

    public ParamValues(String par ,String val){
        parametre = par ;
        value = val ;
    }
    public String getParametre(){return parametre;}
    public String getValue(){return value;}
}

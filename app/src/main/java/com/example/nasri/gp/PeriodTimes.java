package com.example.nasri.gp;

public class PeriodTimes {

    String startTime ;
    String endTime ;
    int stateColor ;

    public PeriodTimes (String start , String end , int state){
        startTime = start ;
        endTime = end ;
        stateColor = state ;
    }

    public String getStartTime(){return startTime;}
    public String getEndTime (){return endTime ;}
    public int getStateColor (){return stateColor ;}
}

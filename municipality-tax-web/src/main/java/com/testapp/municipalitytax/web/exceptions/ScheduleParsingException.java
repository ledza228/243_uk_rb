package com.testapp.municipalitytax.web.exceptions;

public class ScheduleParsingException extends RuntimeException {


    public ScheduleParsingException() {
        super("schedule should be: yearly, monthly, weekly, daily");
    }
}

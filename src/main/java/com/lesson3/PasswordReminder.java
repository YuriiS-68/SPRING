package com.lesson3;

import org.springframework.beans.factory.annotation.Autowired;

public class PasswordReminder {

    private DBConnector dbConnector;

    @Autowired
    public PasswordReminder(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void sendPassword(){
        //some logic
    }
}

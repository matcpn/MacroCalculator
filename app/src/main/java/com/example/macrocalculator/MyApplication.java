package com.example.macrocalculator;

import android.app.Application;

/**
 * Created by Matt on 2/9/2015.
 */
public class MyApplication extends Application {
    private User m_user = new User();

    public User getUser() {
        return m_user;
    }

    public void setUser(User u) {
        m_user = u;
    }
}

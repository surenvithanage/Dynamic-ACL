/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.listener;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 *
 * @author suren_v
 */
public class User implements HttpSessionBindingListener {

    // All logins.
    private static Map<User, HttpSession> logins = new HashMap<User, HttpSession>();

    // Normal properties.
    private Long id;
    private String username;
    // Etc.. Of course with public getters+setters.

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null) ? id.equals(((User) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null) ? (this.getClass().hashCode() + id.hashCode()) : super.hashCode();
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = logins.remove(this);
        System.out.println("-- HttpSessionBindingListener#valueBound() --");
        System.out.printf("added attribute name: %s, value:%s %n",
                event.getName(), event.getValue());
        if (session != null) {
            session.invalidate();
        }
        logins.put(this, event.getSession());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("-- HttpSessionBindingEvent#valueUnbound() --");
        System.out.printf("removed attribute name: %s, value:%s %n",
                event.getName(), event.getValue());
        logins.remove(this);
        System.out.println("logging out: " + getUsername());
    }

}

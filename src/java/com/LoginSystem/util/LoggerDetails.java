/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author suren_v
 */
public class LoggerDetails {
    public void getLogger(String msg, String type, String username, HttpServletRequest request) {
        Logger l = Logger.getLogger("Log");
        try {
            FileHandler fh = new FileHandler("C:/Users/suren_v/Documents/NetBeansProjects/LoginSystem/LogFile.log", true);
            l.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            if (type.equals("warn")) {
                l.warning(msg + " USERNAME: " + username + " IP ADDRESS: " + request.getRemoteAddr()+ "\n\n");
            } else if (type.equals("info")) {
                l.info(msg + " USERNAME: " + username + " IP ADDRESS: " + request.getRemoteAddr() + "\n\n");
            }
            fh.close();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

    }
}
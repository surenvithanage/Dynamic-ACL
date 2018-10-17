/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.common;

import java.util.HashMap;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author suren_v
 */
public class CommonAction extends HttpServlet{

    public boolean checkUserLogin(HashMap<String , String> usermap , String username ){
        HashMap<String , String> umap = usermap;
        String uname = username;
        String oldSessionID = umap.get(uname);
        if(oldSessionID == null){
            System.out.println("NO USER");
            return false;
        }
        else
        {
            System.out.println("user found with old session. Session ID :" + oldSessionID);
            return true;
        }   
    }
    
    public boolean checkUserDevice( HashMap<String,String> userdev , String username , String newIP){
       HashMap<String , String> userdevice = userdev;
       String uname = username;
       String newIPAddr = newIP;
       String oldIPAddr = userdevice.get(uname);
       
       if(oldIPAddr == null){
           System.out.println("Not a logged device");
           return true;
       }else{
           if(oldIPAddr.equals(newIPAddr)){
               System.out.println("Same device : Approve login");
               return true;
           }else{
               System.out.println("Another device : Restrict Login");
               return false;
           }
       }
    }

    

}

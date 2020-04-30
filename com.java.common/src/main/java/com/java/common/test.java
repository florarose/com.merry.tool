package com.java.common;

import java.net.HttpURLConnection;
import java.net.URL;

public class test {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.imagapp.com");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            System.out.println( connection.getResponseCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

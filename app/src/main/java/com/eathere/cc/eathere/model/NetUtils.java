package com.eathere.cc.eathere.model;

import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {
    public static String post(String url, String content) {
        HttpURLConnection conn = null;
        try {
            // Create an URL object
            URL mURL = new URL(url);
            // get HttpURLConnection
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");// method: post
            conn.setReadTimeout(5000);// Read Timeout
            conn.setConnectTimeout(10000);// Connect Timeout
            conn.setDoOutput(true);//


            String data = content;

            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); // no conn.connect() needed
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }

        return null;
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {

            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {

                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }
}
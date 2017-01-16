package com.betasa.soft.loginapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MySqlConnection extends Activity {

    static String key = "4750864444865047"; // 128 bit key

    public static boolean  funcUser(String username, String password,String option){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try{
            String link="http://10.0.2.2:8080/WebApplication1";
            String  encOptionValue=MySqlConnection.encryptString(option),
                    encUsernameValue=MySqlConnection.encryptString(username),
                    encPasswordValue=MySqlConnection.encryptString(password);
            String data  = URLEncoder.encode("option", "UTF-8") + "=" +
                    URLEncoder.encode(option , "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode( "password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();


            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            String respons= sb.toString();
            respons = MySqlConnection.decryptString(respons);
            Log.println(Log.INFO,"mysql",respons);
            if(Boolean.valueOf(respons))
                return true;
            else
                return false;
        } catch(Exception e){
            Log.println(Log.ERROR, "mysqlConError" ,new String("Exception: " + e.toString()) );
        }
        return false;
    }

    public static String encryptString(String str)
    {
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(str.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : encrypted) {
                sb.append((char) b);
            }

            // the encrypted String
            String enc = sb.toString();
            return enc;
        }catch (Exception e){}
        return "";
    }

    public static String decryptString(String str)
    {
        try
        {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] bb = new byte[str.length()];
            for (int i=0; i<str.length(); i++)
                bb[i] = (byte) str.charAt(i);
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            return decrypted;
        }
        catch(Exception e)
        {
            Log.println(Log.INFO,"encrypt",e.getMessage());
        }
        return "";
    }
}
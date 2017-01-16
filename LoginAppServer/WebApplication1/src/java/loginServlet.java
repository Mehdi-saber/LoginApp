/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class loginServlet extends HttpServlet {
    
    static String key = "4750864444865047"; // 128 bit key

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String res="false";
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/javaLogin","LoginCheck","47508644");  
            Statement stmt=con.createStatement();
            String option= request.getParameter("option"),
                    username=request.getParameter("username"),
                    password=request.getParameter("password");
            if(option.equals("signin"))
            {
                ResultSet rs=stmt.executeQuery(" SELECT username FROM users where "+
                        " username='"+username+"' and password= '"+password+"'");  
                if(rs.next())
                    res="true";
                con.close();  
            }
            else  if(option.equals("signup"))
            { 
                stmt.executeUpdate("INSERT INTO users VALUES ('"
                                   +username+"','"+password+"')");
                res="true";            
                con.close();  
            }
        }catch(Exception e){/*response.getWriter().append(e.toString());*/}
        response.getWriter().append(encryptString(res) );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
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
        }
        return "";
    }
    
}

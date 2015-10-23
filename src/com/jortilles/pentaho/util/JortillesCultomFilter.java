package com.jortilles.pentaho.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class JortillesCultomFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
    		FilterChain chain) throws IOException, ServletException {
    	

    	//Codificaccion del token
    	String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	String dst = (String) request.getParameter("dst");
    	String mi_token = (String) request.getParameter("token");
    	String password = date + "SomeExtraText" + dst;
    	System.out.println("llamada con token: " + mi_token +  " para el destino: " + dst );
    	
        MessageDigest md;
        StringBuffer sb = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			  md.update(password.getBytes());
	        byte byteData[] = md.digest();
	        //convert the byte to hex format method 1
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        System.out.println("Digest(in hex format):: " + sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		
		// Si el token que me pasan es igual al que genero
    	if ( mi_token.equals( sb.toString()  ) ){
    		
    		
    		chain.doFilter(new InyectaUsuario(request), response);
    	}else{
    		chain.doFilter(request, response);
    	}
    
    }

    
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }
    
    
}
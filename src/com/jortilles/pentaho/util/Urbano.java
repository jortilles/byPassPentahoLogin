package com.jortilles.pentaho.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Urbano extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 Properties prop = new Properties();
       
    public Urbano() {
        super();
		// Las redirecciones estan en un archivo properties
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/destinos.properties");
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		String dst = (String) request.getParameter("dst");
		String destino = prop.getProperty(dst);
		System.out.println("La direccion " +  dst  +  " redirigiendo a " + destino);
		System.out.println("Los parametros son  " +  request.getQueryString()) ;
		
		if( destino.indexOf("?")> 0){
			destino = destino  +  request.getQueryString();
		}else{
			destino = destino  + "?" +  request.getQueryString();
		}
		 
				

		 response.sendRedirect(destino);
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

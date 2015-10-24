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
		
		/* Esto es para comprobar los properties
		System.out.println("El tamaño es de " +  prop.size() );
		for (Iterator<Entry<Object,Object>> iterator = prop.entrySet().iterator(); iterator.hasNext();) 
	      {
	            Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
	            System.out.println(entry.getKey() +" : "+ entry.getValue());
	      }    
			*/
		
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Llego");
		String dst = (String) request.getParameter("dst");
		String destino = prop.getProperty(dst);
		//System.out.println("La direccion " +  dst  +  " redirigiendo a " + destino);
		//System.out.println("Los parametros son  " +  request.getQueryString()) ;
		
		if( destino.indexOf("?")> 0){
			destino = destino  +  request.getQueryString();
		}else{
			destino = destino  + "?" +  request.getQueryString();
		}
		 
		
		 
		/*
		
		//Redirigiendo los parametros
		Map<String, String[]> allParameters = request.getParameterMap();
		destino = destino  +  "?";
		Iterator iterator = allParameters.keySet().iterator();
		while(iterator.hasNext() ){
			String key   = iterator.next().toString();
			String value = allParameters.get(key).toString();
			destino = destino  +   key  +  "="  +  value  + "&" ;
		}

        */
		

		 response.sendRedirect(destino);
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

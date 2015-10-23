package com.jortilles.pentaho.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class InyectaUsuario extends HttpServletRequestWrapper
{
    private final Map<String, String[]> modifiableParameters;
    private Map<String, String[]> allParameters = null;
    private Map<String, String[]> additionalParams = new HashMap<String, String[]>();
    Properties prop = new Properties();

    public InyectaUsuario(ServletRequest request )
    {
        super((HttpServletRequest) request);
        
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
        
        
        
        
        modifiableParameters = new TreeMap<String, String[]>();
    	additionalParams.put("userid", new String[] { prop.getProperty("userid") });
    	additionalParams.put("password", new String[] {prop.getProperty("password") });
        modifiableParameters.putAll(additionalParams);
    	System.out.println("uyc inyectados, usu: " +  prop.getProperty("userid")  );
    	
    }

	@Override
    public String getParameter(final String name)
    {
        String[] strings = getParameterMap().get(name);
        if (strings != null)
        {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
        if (allParameters == null)
        {
            allParameters = new TreeMap<String, String[]>();
            allParameters.putAll(super.getParameterMap());
            allParameters.putAll(modifiableParameters);
        }
        //Return an unmodifiable collection because we need to uphold the interface contract.
        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name)
    {
        return getParameterMap().get(name);
    }
}
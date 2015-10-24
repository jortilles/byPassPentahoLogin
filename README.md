# byPassPentahoLogin


Bypass Pentaho Login is a tool to can access to some pentaho resources, such as reports or dashboards, from a third party tool.

The idea behind is to create a token codified in the same way in the Pentaho  server and in the third party tool that allow the login


#Usage

###place in WEB-INF/classes the properties file destinos.properties

This properties file looks like this:
```
  userid=admin
  password=password
  destination_1=/pentaho/api/repos/%3Apublic%3ASteel Wheels%3ADashboards%3ACTools_dashboard.wcdf/generatedContent
  destination_2=/pentaho/api/repos/%3Apublic%3ASteel Wheels%3ADashboards%3ACTools_dashboard.wcdf/generatedContent
```

This properties file have:
-The user credentials we will use to bypass the login. It is recommended  NOT to use the admin user for this.
-The route of all the destinations available... you can add as much as you want.


###Put the jar byPassPentahoLogin.jar in the pentaho/WEB-INF/lib directory 


###Declare the filter and the servlet in the Pentaho web.xml

You should add the filter before Spring Security Filter Chain Proxy
```
	<filter>
		<filter-name>JortillesCultomFilter</filter-name>
		<filter-class>com.jortilles.pentaho.util.JortillesCultomFilter</filter-class>
	</filter>
```
And the filter mapping also before Spring Security Filter Chain Proxy ...

```
    <filter-mapping>
	   <filter-name>JortillesCultomFilter</filter-name>
	   <url-pattern>/Urbano</url-pattern>
	</filter-mapping>
```

Add the servelt just at the end of the servlet declarations.
```
  <!-- insert additional servlets -->
  <servlet>
    <servlet-name>Urbano</servlet-name>
    <servlet-class>com.jortilles.pentaho.util.Urbano</servlet-class>
  </servlet>
  
   <servlet-mapping>
    <servlet-name>Urbano</servlet-name>
    <url-pattern>/Urbano</url-pattern>
  </servlet-mapping>
```


###Finally...
if you are using pentaho 5.3 or above you should  enable the option to accept user and password form the url. in /pentaho-solutions/system/security.properties you should set to true the parameter requestParameterAuthenticationEnabled

```
  requestParameterAuthenticationEnabled=true
```

Take a look at:
  
  https://help.pentaho.com/Documentation/5.3/0P0/000/090
  
  http://pedroalves-bi.blogspot.pt/2015/02/useful-tips-easy-authentication-in.html



###Now you are able to bypass the login by calling:


  http://localhost:8080/pentaho/Urbano?token=e99b5cdc07594f1e7bed336bbaf2e2db&dst=destination_1


Where: 
 The destination_1 is the destination you declared in the destinos.properties file.
 
 The token is the hash md5 of the string: date + "SomeExtraText" + destination   where:
    the date is the date with the format dd-MM-yyyy

    "SomeExtraText" is just some extra text to make the hash ugliest. You can modify the class JortillesCultomFilter and place here you desired  text

    destination is the destination you want to go... in this example destination_1
    

The filter will decode de call and attach the user and password defined in the properties file to de call. As well all the rest of the parameters.

So... now you only have to codify your hash in the same way in the third party tool.  Yo have to generate the call with the same logic.




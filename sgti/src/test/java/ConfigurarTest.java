package test.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;    

public class ConfigurarTest {  
	   
	protected boolean habilitarTest;
	
	protected static ClassPathXmlApplicationContext context;  
	
	public boolean isHabilitarTest() {
		
		Properties localProperties = new Properties();
		boolean testHabilitado = false;
		try {
			localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			testHabilitado = Boolean.valueOf(localProperties.getProperty("habilitarTest"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return testHabilitado;
		
	}
	
	

}

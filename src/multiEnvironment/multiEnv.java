package multiEnvironment;

import java.util.Properties;

import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;

public class multiEnv {

	public static void main(String[] args) throws IOException {
		multiEnv obj = new multiEnv();
		obj.runMultipleEnvs();
	}
	
	@Parameters({"env"})// tstNG annotation
	public void runMultipleEnvs() throws IOException {
		String propertyFilePath ="";
		String env = "SIT";
		
		switch(env) {
		case "SIT": 
			propertyFilePath= "src/multiEnvironment/file1.properties";
			break;
		case "UAT": 
			propertyFilePath= "src/multiEnvironment/file2.properties";
		}
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propertyFilePath);
		prop.load(fis);

		System.out.println(prop.getProperty("url"));
		System.out.println(System.getProperty("browser"));
	}
}

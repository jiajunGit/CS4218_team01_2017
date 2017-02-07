package test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;

import org.junit.Test;

public class CdApplicationTest {
	
	private InputStream input;
	private OutputStream output;
	
	@Test
	public void testCdNullArgumentsException() {
		CdApplication cdApp = new CdApplication();
		
		try {
			cdApp.run(null, input, output);
		} catch (CdException e) {
			assertEquals(e.getMessage(), "cd: Null argument detected");
		}
	}
	
	@Test
	public void testCdDirectoryUnspecifiedException(){
		CdApplication cdApp = new CdApplication();
		String[] args = {};
		
		try {
			cdApp.run(args, input, output);
		} catch (CdException e){
			assertEquals(e.getMessage(), "cd: No relative directory specified");
		}
	}
	
	@Test
	public void testCdMoreThanOneArgException(){
		CdApplication cdApp = new CdApplication();
		String[] args = {"HelloWorldDirectory", "HelloWorld2Directory"};
		
		try{
			cdApp.run(args, input, output);
		}catch (CdException e){
			assertEquals(e.getMessage(), "cd: Only 1 argument is expected");
		}
	}
	
	@Test
	public void testDirectoryDoesNotExistException(){
		CdApplication cdApp = new CdApplication();
		String[] args = {};
		
	}
}

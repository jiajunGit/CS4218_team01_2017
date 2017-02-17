package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

public class CallCommandTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpBeforeTest(){
		System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void tearDownAfterTest(){
		System.setOut(System.out);
	}
	
	@Test(expected=ShellException.class)
	public void testCallCommand() throws AbstractApplicationException, ShellException {
		CallCommand call = new CallCommand("\"echo\"");
		call.parse();
		call.evaluate(System.in, outContent);
	}
	
	@Test(expected=ShellException.class)
	public void testCallCommandNull() throws AbstractApplicationException, ShellException {
		CallCommand call = new CallCommand(null);
		call.parse();
		call.evaluate(System.in, outContent);
	}

	@Test
	public void testCallCommandArg1() throws AbstractApplicationException, ShellException{
		CallCommand call = new CallCommand("echo 'black cat'");
		call.parse();
		call.evaluate(System.in, outContent);
	}
	
}

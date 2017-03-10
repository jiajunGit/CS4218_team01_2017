package test.integration.sort;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class SortIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		Environment.setDefaultDirectory();
	}

	@After
	public void tearDown() throws Exception {
		Environment.setDefaultDirectory();
	}

	@Test
	public void testWithEcho() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01; echo hi");
		assertEquals("",output);
	}
	
	@Test
	public void testWithEchoWithCmdSub() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01; echo `sort -n input/input02`");
		assertEquals("",output);
	}
	
	@Test
	public void testWithCat() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01>input/test; cat<input/test");
		assertEquals("",output);
	}
	
	@Test
	public void testWithCd() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01; cd ../; pwd");
		assertEquals("",output);
	}

}

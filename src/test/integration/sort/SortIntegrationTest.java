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
	private static final String expectedOutput01="1"+LINE_SEPARATOR+
												"11"+LINE_SEPARATOR+
												"2"+LINE_SEPARATOR+
												"22"+LINE_SEPARATOR+
												"3"+LINE_SEPARATOR+
												"33"+LINE_SEPARATOR+
												"4"+LINE_SEPARATOR+
												"44"+LINE_SEPARATOR+
												"5"+LINE_SEPARATOR+
												"55"+LINE_SEPARATOR+
												"alice"+LINE_SEPARATOR+
												"bob"+LINE_SEPARATOR+
												"charlie"+LINE_SEPARATOR;
	
	private static final String expectedOutput02="1"+LINE_SEPARATOR+
												"11"+LINE_SEPARATOR+
												"2"+LINE_SEPARATOR+
												"22"+LINE_SEPARATOR+
												"3"+LINE_SEPARATOR+
												"33"+LINE_SEPARATOR+
												"4"+LINE_SEPARATOR+
												"44"+LINE_SEPARATOR+
												"5"+LINE_SEPARATOR+
												"55"+LINE_SEPARATOR;
	
	private static final String expectedOutput03="11"+LINE_SEPARATOR+
												"22"+LINE_SEPARATOR+
												"33"+LINE_SEPARATOR+
												"44"+LINE_SEPARATOR+
												"55"+LINE_SEPARATOR;
	
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
		assertEquals(expectedOutput01+"hi"+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithEchoWithCmdSub() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01; echo `sort -n input/input02`");
		assertEquals(expectedOutput01+"1 2 3 4 5 11 22 33 44 55"+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithCat() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01>input/test; cat<input/test");
		assertEquals(expectedOutput01,output);
	}
	
	@Test
	public void testWithCd() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01; cd ../; pwd");
		String CURRENT_DIRECTORY= Environment.currentDirectory;
		assertEquals(expectedOutput01+CURRENT_DIRECTORY+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithHead() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort input/input01 | head -n 10");
		assertEquals(expectedOutput02,output);
	}
	
	@Test
	public void testWithTail() throws AbstractApplicationException, ShellException {
		String output=shell.parseAndEvaluate("sort -n input/input02 | tail -n 5");
		assertEquals(expectedOutput03,output);
	}

}

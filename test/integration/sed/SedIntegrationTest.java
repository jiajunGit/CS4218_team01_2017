package integration.sed;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class SedIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_DIR = "test" + PATH_SEPARATOR + "integration"
			+ PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;

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
	public void testIntegrateDate() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/a/g " + RELATIVE_DIR + "sedIntegration; date");
	}

	@Test
	public void testIntegratePwd() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/' '/g " + RELATIVE_DIR + "sedIntegration; pwd");
	}

	@Test
	public void testIntegrateTail() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/many/g " + RELATIVE_DIR + "sedIntegration | tail -n 1");
	}

	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/`echo many`/ " + RELATIVE_DIR + "sedIntegration | head `cat " + RELATIVE_DIR + "parameters`");
	}

	@Test
	public void testIntegrateEcho() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/`echo many`/ " + RELATIVE_DIR + "sedIntegration | head -n 1");
	}

	@Test
	public void testIntegrateCat() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/many/ " + RELATIVE_DIR + "sedIntegration > " + RELATIVE_DIR
				+ "sedIntegration2.txt ; cat " + RELATIVE_DIR + "*");
		
		File file = new File(RELATIVE_DIR + "sedIntegration2.txt");
		if (file.exists()) {
			file.delete();
		}
	}

	@Test(expected = AbstractApplicationException.class)
	public void testIntegrateCd() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/a/g " + RELATIVE_DIR + "sedIntegration; cd pathThatDoesNotExist");
	}

	@Test
	public void testIntegrateSort() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | sort");
		String expected = "replacement substring" + LINE_SEPARATOR + "sub sub string" + LINE_SEPARATOR + "substitution"
				+ LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | grep 'sub'");
	}

	@Test
	public void testIntegrateCal() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration ; cal 3 2017");
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | wc -lw");
		String expected = "       6       2" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/sub1/ " + RELATIVE_DIR
				+ "sedIntegration | sed s/sub1/sub2/ | sed s/sub2/sub3/  | sed s/sub3/sub4/");
	}
}

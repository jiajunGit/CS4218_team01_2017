package test.integration.head;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class HeadIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_DIR = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration"
			+ PATH_SEPARATOR + "head" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
	private static final String ABS_DIR = Environment.currentDirectory + PATH_SEPARATOR;

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
	public void testIntegrateCat() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines > " + RELATIVE_DIR + "headOut.txt" + " ; cat " + RELATIVE_DIR + "headOut.txt");
		String expectedOut = "head1" + LINE_SEPARATOR + "head2" + LINE_SEPARATOR + "head3" + LINE_SEPARATOR + "head4"
				+ LINE_SEPARATOR + "head5" + LINE_SEPARATOR + "head6" + LINE_SEPARATOR + "head7" + LINE_SEPARATOR
				+ "head8" + LINE_SEPARATOR + "head9" + LINE_SEPARATOR + "head10" + LINE_SEPARATOR;
		
		File file = new File(RELATIVE_DIR + "headOut.txt");
		if (file.exists()){
			file.delete();
		}
		
		assertEquals(expectedOut, output);

	}

	@Test
	public void testIntegrateCd() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines" + " ; cd " + RELATIVE_DIR);

		assertEquals(Environment.currentDirectory + PATH_SEPARATOR, ABS_DIR + RELATIVE_DIR);
	}

	@Test
	public void testIntegratePwd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines" + " ; pwd ");
		String expectedOut = "head1" + LINE_SEPARATOR + "head2" + LINE_SEPARATOR + "head3" + LINE_SEPARATOR + "head4"
				+ LINE_SEPARATOR + "head5" + LINE_SEPARATOR + "head6" + LINE_SEPARATOR + "head7" + LINE_SEPARATOR
				+ "head8" + LINE_SEPARATOR + "head9" + LINE_SEPARATOR + "head10" + LINE_SEPARATOR
				+ Environment.currentDirectory + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testTerminateIntegratePwd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "1000headlines" + " ; pwd ");
	}

	@Test
	public void testIntegrateEcho() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("echo `head " + RELATIVE_DIR + "10headlines `");
		String expectedOut = "head1 " + "head2 " + "head3 " + "head4 " + "head5 " + "head6 " + "head7 " + "head8 "
				+ "head9 " + "head10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines | head ");
		String expectedOut = "head1" + LINE_SEPARATOR + "head2" + LINE_SEPARATOR + "head3" + LINE_SEPARATOR + "head4"
				+ LINE_SEPARATOR + "head5" + LINE_SEPARATOR + "head6" + LINE_SEPARATOR + "head7" + LINE_SEPARATOR
				+ "head8" + LINE_SEPARATOR + "head9" + LINE_SEPARATOR + "head10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testIntegrateTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines | tail -n 5 ");
		String expectedOut = "head6" + LINE_SEPARATOR + "head7" + LINE_SEPARATOR + "head8" + LINE_SEPARATOR + "head9"
				+ LINE_SEPARATOR + "head10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testIntegrateSort() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines | sort ");
		String expectedOut = "head1" + LINE_SEPARATOR + "head10" + LINE_SEPARATOR + "head2" + LINE_SEPARATOR + "head3"
				+ LINE_SEPARATOR + "head4" + LINE_SEPARATOR + "head5" + LINE_SEPARATOR + "head6" + LINE_SEPARATOR
				+ "head7" + LINE_SEPARATOR + "head8" + LINE_SEPARATOR + "head9" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines | grep head ");
		String expectedOut = "head1" + LINE_SEPARATOR + "head2" + LINE_SEPARATOR + "head3" + LINE_SEPARATOR + "head4"
				+ LINE_SEPARATOR + "head5" + LINE_SEPARATOR + "head6" + LINE_SEPARATOR + "head7" + LINE_SEPARATOR
				+ "head8" + LINE_SEPARATOR + "head9" + LINE_SEPARATOR + "head10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head " + RELATIVE_DIR + "10headlines* | sed \"s/head/tail/\"");

		String expectedOut = "tail1" + LINE_SEPARATOR + "tail2" + LINE_SEPARATOR + "tail3" + LINE_SEPARATOR + "tail4"
				+ LINE_SEPARATOR + "tail5" + LINE_SEPARATOR + "tail6" + LINE_SEPARATOR + "tail7" + LINE_SEPARATOR
				+ "tail8" + LINE_SEPARATOR + "tail9" + LINE_SEPARATOR + "tail10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testIntegrateCal() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head -n 1 " + RELATIVE_DIR + "10headlines; cal 3 2017");
		String expectedOut = "head1" + LINE_SEPARATOR +      
		"     March 2017     "   + LINE_SEPARATOR +
		"Su Mo Tu We Th Fr Sa"   + LINE_SEPARATOR +
		"         1  2  3  4 " + LINE_SEPARATOR +
		"5  6  7  8  9  10 11" + LINE_SEPARATOR +
		"12 13 14 15 16 17 18" + LINE_SEPARATOR +
		"19 20 21 22 23 24 25" + LINE_SEPARATOR +
		"26 27 28 29 30 31   " + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("head -n 1 " + RELATIVE_DIR + "10headlines | wc -w");
		String expectedOut = "1" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

}

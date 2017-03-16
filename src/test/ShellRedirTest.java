package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellRedirTest {

	private static ShellImpl shell;
	private static byte[] buf;

	@BeforeClass
	public static void setup() {

		shell = new ShellImpl();
		buf = new byte[10000];

		String fileName = Environment.getAbsPath("out");
		Environment.deleteFile(fileName);

		fileName = Environment.getAbsPath("1out");
		Environment.deleteFile(fileName);

		assertTrue(createTxtFile("in1", "one"));
		assertTrue(createTxtFile("in2", "two"));
	}

	@AfterClass
	public static void tearDown() {
		assertTrue(Environment.deleteFile("in1"));
		assertTrue(Environment.deleteFile("in2"));
	}

	@Before
	public void setupBeforeTest() {

		String fileName = Environment.getAbsPath("out");
		assertTrue(Environment.createNewFile(fileName));

		fileName = Environment.getAbsPath("1out");
		assertTrue(Environment.createNewFile(fileName));
	}

	@After
	public void tearDownAfterTest() {

		String fileName = Environment.getAbsPath("out");
		assertTrue(Environment.deleteFile(fileName));

		fileName = Environment.getAbsPath("1out");
		assertTrue(Environment.deleteFile(fileName));
	}

	@Test
	public void singleGlobWithArgs() throws AbstractApplicationException, ShellException {

		String command = "echo >out* hi there";
		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, "hi there");
	}

	@Test(expected = AbstractApplicationException.class)
	public void endingWithNullOutput() throws AbstractApplicationException, ShellException {

		String command = "cat <in1 >";
		String out = shell.parseAndEvaluate(command);

		assertTrue(out.isEmpty());

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, Symbol.NEW_LINE_S);
	}

	@Test
	public void endingWithNullInput() throws AbstractApplicationException, ShellException {

		String command = "echo >out <";
		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, "");
	}

	@Test
	public void beforePipeNullInput() throws AbstractApplicationException, ShellException {

		String command = "echo <|cat";
		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);
	}

	@Test
	public void beforeSeqNullInput() throws AbstractApplicationException, ShellException {

		String command = "echo >out <;echo hi";
		String out = shell.parseAndEvaluate(command);

		assertEquals(out, "hi" + Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, Symbol.NEW_LINE_S);
	}

	@Test(expected = AbstractApplicationException.class)
	public void beforeSeqNullOutput() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 > ;echo hi";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void nullInputWithEcho() throws AbstractApplicationException, ShellException {

		String command = "echo < '' >out ";
		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, "");
	}

	@Test(expected = AbstractApplicationException.class)
	public void nullInput() throws AbstractApplicationException, ShellException {

		String command = "cat < '' >out ";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = AbstractApplicationException.class)
	public void nullOutput() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 >'' ";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = ShellException.class)
	public void endWithInputRedirBeforeGlob() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 >out*  <";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = ShellException.class)
	public void endWithInputRedir() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 >out  <";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = ShellException.class)
	public void endWithOutputRedirBeforeGlob() throws AbstractApplicationException, ShellException {

		String command = "cat >out < in1* >";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = ShellException.class)
	public void endWithOutputRedir() throws AbstractApplicationException, ShellException {

		String command = "cat >out < in1 >";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void oneGlobAtTheEndWithInputRedir() throws AbstractApplicationException, ShellException {

		String command = "cat > out* < in1*";

		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, "one");
	}

	@Test
	public void oneGlobAtTheEndWithOutputRedir() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 > out*";

		String out = shell.parseAndEvaluate(command);

		assertEquals(out, Symbol.NEW_LINE_S);

		String fileName = Environment.getAbsPath("out");
		String contents = getContents(fileName);

		assertEquals(contents, "one");
	}

	@Test(expected = ShellException.class)
	public void afterOneGlobThenOutput() throws AbstractApplicationException, ShellException {

		String command = "cat >1out < in1* > out";
		shell.parseAndEvaluate(command);
	}

	@Test(expected = ShellException.class)
	public void afterOneGlobThenInput() throws AbstractApplicationException, ShellException {

		String command = "cat < in1 > out* < in2";
		shell.parseAndEvaluate(command);
	}

	private String getContents(String fileName) {

		String out = null;
		try {

			FileInputStream fs = new FileInputStream(fileName);

			StringBuilder sb = new StringBuilder();
			while (true) {
				int bytesRead = fs.read(buf);
				if (bytesRead < 0) {
					break;
				}
				sb.append(new String(buf, 0, bytesRead));
			}
			out = sb.toString();

			fs.close();

		} catch (IOException e) {
		}
		return out;
	}

	private static boolean createTxtFile(String fileName, String content) {

		if (fileName == null || content == null) {
			return false;
		}

		fileName = Environment.getAbsPath(fileName);

		if (!Environment.isExists(fileName)) {
			if (!Environment.createNewFile(fileName)) {
				return false;
			}
		} else if (Environment.isDirectory(fileName)) {
			return false;
		}

		try {
			FileOutputStream os = new FileOutputStream(fileName);
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}

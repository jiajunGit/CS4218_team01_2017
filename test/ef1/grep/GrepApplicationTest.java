package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "grep";
	private static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY;

	private static GrepApplication grep;
	private static byte[] buf;

	@BeforeClass
	public static void setup() {
		grep = new GrepApplication();
		buf = new byte[10000];
		makeAllContentsCompatible();
	}

	@AfterClass
	public static void tearDown() {
		grep = null;
	}

	@Test(expected = GrepException.class)
	public void testRunNullArgs() throws GrepException {
		grep.run(null, System.in, System.out);
	}

	@Test(expected = GrepException.class)
	public void testRunNullStdin() throws GrepException, IOException {

		String pattern = "some";

		String[] args = { pattern };

		grep.run(args, null, System.out);
	}

	@Test(expected = GrepException.class)
	public void testRunNullStdout() throws GrepException, IOException {

		String pattern = "some";

		String[] args = { pattern };

		grep.run(args, System.in, null);
	}

	@Test(expected = GrepException.class)
	public void testRunInvalidArgumentCount() throws GrepException {

		String[] args = {};

		grep.run(args, System.in, System.out);
	}

	@Test(expected = GrepException.class)
	public void testRunClosedStdout() throws GrepException, IOException {

		String pattern = "some";
		String content = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out.txt";
		File tempFileOne = new File(filePathOne);

		String[] args = { pattern };

		InputStream stdin = new ByteArrayInputStream(content.getBytes());
		OutputStream stdout = new FileOutputStream(tempFileOne);
		stdout.close();

		grep.run(args, stdin, stdout);
	}

	@Test(expected = GrepException.class)
	public void testRunClosedStdin() throws GrepException, IOException {

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";
		
		File tempFileOne = new File(filePathOne);

		String[] args = { "some" };

		InputStream stdin = new FileInputStream(tempFileOne);
		stdin.close();

		grep.run(args, stdin, System.out);
	}

	@Test
	public void testRunGrepFromStdin() throws GrepException, IOException {

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";
		File tempFileOne = new File(filePathOne);

		String[] args = { "some" };

		InputStream stdin = new FileInputStream(tempFileOne);
		OutputStream stdout = new ByteArrayOutputStream();

		try{
			grep.run(args, stdin, stdout);
		} finally {
			stdin.close();
		}

		String output = stdout.toString();
		String expected = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "seom  some";

		assertEquals(output, expected);
	}

	@Test
	public void testRunGrepFromOneFile() throws GrepException, IOException {

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";

		String[] args = { "some", filePathOne };

		OutputStream stdout = new ByteArrayOutputStream();

		grep.run(args, null, stdout);

		String output = stdout.toString();
		String expected = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "seom  some";

		assertEquals(output, expected);
	}

	@Test
	public void testRunGrepFromEmptyFile() throws GrepException, IOException {

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out.txt";

		String[] args = { "some", filePathOne };

		OutputStream stdout = new ByteArrayOutputStream();

		grep.run(args, null, stdout);

		String output = stdout.toString();
		String expected = "";

		assertEquals(output, expected);
	}

	@Test
	public void testRunGrepFromMultipleFiles() throws GrepException, IOException {

		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";
		String filePathTwo = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "2.txt";
		String filePathThree = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "3.txt";

		String expected = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt: some day" + LINE_SEPARATOR
				+ ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt:     somsome some\t" + LINE_SEPARATOR
				+ ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt: seom  some" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt: some day" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt:     somsome some\t" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt: seom  some" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt: some day" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt:     somsome some\t" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt: seom  some";

		String[] args = { "some", filePathOne, filePathTwo, filePathThree };

		OutputStream stdout = new ByteArrayOutputStream();

		grep.run(args, null, stdout);

		String output = stdout.toString();

		assertEquals(output, expected);
	}

	@Test(expected = GrepException.class)
	public void testRunWithClosedOutputStream() throws IOException, GrepException {

		String pattern = "some";
		String content = "some day" + LINE_SEPARATOR + "    somsome some\t" + LINE_SEPARATOR + "      somme"
				+ LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		String[] args = { pattern };

		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out.txt";
		File tempFileOne = new File(filePath);

		OutputStream stdout = new FileOutputStream(tempFileOne);
		stdout.close();

		InputStream stdin = new ByteArrayInputStream(content.getBytes());

		grep.run(args, stdin, stdout);
	}

	@Test(expected = GrepException.class)
	public void testRunWithClosedInputStream() throws IOException, GrepException {

		String pattern = "some";
		
		String[] args = { pattern };

		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";
		File tempFileOne = new File(filePath);

		InputStream stdin = new FileInputStream(tempFileOne);
		stdin.close();

		grep.run(args, stdin, System.out);
	}

	@Test
	public void testGrepFromStdinEmptyPattern() throws GrepException {

		String pattern = "";
		String content = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		InputStream is = new ByteArrayInputStream(content.getBytes());

		String output = grep.grepFromStdin(pattern, is);

		assertEquals(output, content);
	}

	@Test
	public void testGrepFromStdinEmptyPatternWithNewLineAtTheFront() throws GrepException {

		String pattern = "";
		String content = LINE_SEPARATOR + "some day" + LINE_SEPARATOR + "    somsome some" + "\t"
				+ LINE_SEPARATOR + "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		InputStream is = new ByteArrayInputStream(content.getBytes());

		String output = grep.grepFromStdin(pattern, is);

		String expected = LINE_SEPARATOR + "some day" + LINE_SEPARATOR + "    somsome some" + "\t"
				+ LINE_SEPARATOR + "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		assertEquals(output, expected);
	}

	@Test
	public void testGrepFromStdinEmptyPatternWithNewLineAtTheBack() throws GrepException {

		String pattern = "";
		String content = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some" + LINE_SEPARATOR;

		InputStream is = new ByteArrayInputStream(content.getBytes());

		String output = grep.grepFromStdin(pattern, is);

		String expected = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		assertEquals(output, expected);
	}

	@Test(expected = GrepException.class)
	public void testGrepFromStdinNullPattern() throws GrepException {

		String content = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		InputStream is = new ByteArrayInputStream(content.getBytes());

		grep.grepFromStdin(null, is);
	}

	@Test(expected = GrepException.class)
	public void testGrepFromStdinNullInputStream() throws GrepException {

		String pattern = "pattern";
		grep.grepFromStdin(pattern, null);
	}

	@Test
	public void testGrepFromStdinEmptyInputStream() throws GrepException {

		String pattern = "some";

		String content = "";
		InputStream is = new ByteArrayInputStream(content.getBytes());

		String output = grep.grepFromStdin(pattern, is);

		assertEquals(output, "");
	}

	@Test
	public void testInterfaceGrepFromMultipleFiles() throws GrepException, IOException {

		String pattern = "some";
		
		String filePathOne = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";
		String filePathTwo = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "2.txt";
		String filePathThree = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "3.txt";

		String expected = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt: some day" + LINE_SEPARATOR
				+ ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt:     somsome some\t" + LINE_SEPARATOR
				+ ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt: seom  some" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt: some day" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt:     somsome some\t" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "2.txt: seom  some" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt: some day" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt:     somsome some\t" + LINE_SEPARATOR + ABSOLUTE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "3.txt: seom  some" + LINE_SEPARATOR;

		String command = "grep " + pattern + " \"" + filePathOne + "\"" + " \"" + filePathTwo + "\"" + " \""
				+ filePathThree + "\"";

		String output = grep.grepFromMultipleFiles(command);

		assertEquals(output, expected);
	}

	@Test
	public void testInterfaceGrepInvalidPatternInFile() throws GrepException, IOException {

		String pattern = "?*some*";
		
		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";

		String command = "grep " + pattern + " \"" + filePath + "\"";

		String expected = "grep:Invalid pattern specified";

		String output = grep.grepInvalidPatternInFile(command);

		assertEquals(output, expected);
	}

	@Test
	public void testInterfaceGrepFromOneFile() throws GrepException, IOException {

		String pattern = "some";

		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";

		String command = "grep " + pattern + " \"" + filePath + "\"";

		String expected = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "seom  some" + LINE_SEPARATOR;

		String output = grep.grepFromOneFile(command);

		assertEquals(output, expected);
	}

	@Test
	public void testInterfaceGrepInvalidPatternInStdin() throws GrepException, IOException {

		String pattern = "?*some*";

		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";

		String command = "grep " + pattern + " <" + filePath;

		String expected = "grep:Invalid pattern specified";

		String output = grep.grepInvalidPatternInStdin(command);

		assertEquals(output, expected);
	}

	@Test
	public void testInterfaceGrepFromStdin() throws GrepException, IOException {

		String pattern = "some";
		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt";

		String command = "grep " + pattern + " <" + filePath;

		String expected = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "seom  some" + LINE_SEPARATOR;

		String output = grep.grepFromStdin(command);

		assertEquals(output, expected);
	}

	@Test
	public void testGrepFromStdin() throws GrepException {

		String pattern = "some";

		String content = "some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR
				+ "      somme" + LINE_SEPARATOR + LINE_SEPARATOR + "seom  some";

		InputStream is = new ByteArrayInputStream(content.getBytes());

		String output = grep.grepFromStdin(pattern, is);

		assertEquals(output,
				"some day" + LINE_SEPARATOR + "    somsome some" + "\t" + LINE_SEPARATOR + "seom  some");
	}

	@Test(expected = GrepException.class)
	public void testGrepFromNonExistentFilePath() throws GrepException {

		String pattern = "some";

		String filePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "7.txt";

		grep.grepFromOneFile(pattern, filePath);
	}

	@Test(expected = GrepException.class)
	public void testGrepFromInvalidFilePath() throws GrepException {

		String pattern = "some";

		String filePath = ":grep" + PATH_SEPARATOR + "1.txt";

		grep.grepFromOneFile(pattern, filePath);
	}

	@Test(expected = GrepException.class)
	public void testGrepFromMultipleFilesEmptyArgs() throws GrepException, IOException {

		String[] arguments = {};
		grep.grepFromMultipleFiles(arguments);
	}

	@Test(expected = GrepException.class)
	public void testGrepFromMultipleFilesNullArgs() throws GrepException, IOException {

		String[] arguments = null;
		grep.grepFromMultipleFiles(arguments);
	}
	
	private static String getNewLineInExpectedFile() {
		return getContents(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "newLine");
	}
	
	private static void makeAllContentsCompatible() {
		String newLine = getNewLineInExpectedFile();
		makeContentsCompatible( ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "1.txt", newLine );
		makeContentsCompatible( ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "2.txt", newLine );
		makeContentsCompatible( ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "3.txt", newLine );
	}
	
	private static void makeContentsCompatible( String filePath, String newLine ) {
		
		String contents = getContents(filePath);
		
		if(contents == null || newLine == null || newLine.isEmpty()){
			return;
		}
		
		String newContents = makeContentCompatible(contents, newLine);
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filePath);
			os.write(newContents.getBytes());
			
		} catch (IOException e) {}
		  finally{
			  closeOutputStream(os);
		  }
	}
	
	private static void closeOutputStream( FileOutputStream os ) {
		if(os != null){
			try { os.close();
			} catch (IOException e) {}
		}
	}
	
	private static String makeContentCompatible(String contents, String newLine) {

		if (contents == null) {
			return "";
		}
		
		newLine = Pattern.quote(newLine);
		contents.replaceAll(newLine, LINE_SEPARATOR);

		return contents;
	}
	
	private static String getContents(String fileName) {

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
}

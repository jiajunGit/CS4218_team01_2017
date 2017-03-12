package test.ef2.wc;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class WcApplicationTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "wc"
			+ PATH_SEPARATOR;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();


	@AfterClass
	public static void tearDownAfterTest(){
		System.setOut(System.out);
	}
	
	@Test
	public void testPrintCharacterCountInFile(){
		WcApplication wcApp = new WcApplication();
		assertEquals(" 6488666", wcApp.printCharacterCountInFile("wc -m " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
	}

	@Test
	public void testPrintWordCountInFile(){
		WcApplication wcApp = new WcApplication();
		assertEquals(" 1095695", wcApp.printWordCountInFile("wc -w " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
	}

	@Test
	public void testPrintNewlineCountInFile(){
		WcApplication wcApp = new WcApplication();
		assertEquals("  128457", wcApp.printNewlineCountInFile("wc -l " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
			
	}

	@Test
	public void testPrintAllCountsInFile(){
		WcApplication wcApp = new WcApplication();
		assertEquals(" 6488666 1095695  128457", wcApp.printAllCountsInFile("wc -mwl " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
			
	}
	
//	@Test
//	public void testPrintCharacterCountInFileSmall(){
//		WcApplication wcApp = new WcApplication();
//		assertEquals(" 6488666" + LINE_SEPARATOR, wcApp.printCharacterCountInFile("wc -m " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
//	}
//
//	@Test
//	public void testPrintWordCountInFileSmall(){
//		WcApplication wcApp = new WcApplication();
//		assertEquals(" 1095695" + LINE_SEPARATOR, wcApp.printWordCountInFile("wc -w " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
//	}
//
//	@Test
//	public void testPrintNewlineCountInFileSmall(){
//		WcApplication wcApp = new WcApplication();
//		assertEquals("  128457" + LINE_SEPARATOR, wcApp.printNewlineCountInFile("wc -l " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
//			
//	}
//
//	@Test
//	public void testPrintAllCountsInFileSmall(){
//		WcApplication wcApp = new WcApplication();
//		assertEquals(" 6488666 1095695  128457" + LINE_SEPARATOR, wcApp.printAllCountsInFile("wc -mwl " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));
//			
//	}

	@Test
	public void testPrintCharacterCountInStdin(){
		try {
			WcApplication wcApp = new WcApplication();
			InputStream stdin = writeToStdin("text1");
			assertEquals("    3576", wcApp.printCharacterCountInStdin("wc -m", stdin));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} 
	}

	static InputStream writeToStdin(String fileName) throws IOException {
		String path = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + fileName;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		InputStream stdin = new ByteArrayInputStream(encoded);
		return stdin;
		
	}

	@Test
	public void testPrintWordCountInStdin(){
		try {
			WcApplication wcApp = new WcApplication();
			InputStream stdin = writeToStdin("text1");
			assertEquals("     592", wcApp.printWordCountInStdin("wc -w", stdin));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} 		
	}

	@Test
	public void testPrintNewlineCountInStdin(){
		try {
			WcApplication wcApp = new WcApplication();
			InputStream stdin = writeToStdin("text1");
			assertEquals("      71", wcApp.printNewlineCountInStdin("wc -l", stdin));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} 
	}

	@Test
	public void testPrintAllCountsInStdin(){
		try {
			WcApplication wcApp = new WcApplication();
			InputStream stdin = writeToStdin("text1");
			assertEquals("    3576     592      71", wcApp.printAllCountsInStdin("wc -mwl", stdin));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}		
	}
	
	@Test
	public void testMoreThan3Options(){
		WcApplication wcApp = new WcApplication();
		assertEquals("Invalid Options", wcApp.printAllCountsInFile("wc -mwlq " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));		
	}
	
	@Test
	public void test2Files(){
		WcApplication wcApp = new WcApplication();
		String response = "    3576     592      71" + LINE_SEPARATOR + "     306      53      10";
		assertEquals(response, wcApp.printAllCountsInFile("wc -mwl " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text1 " 
				+ RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2" ));
	}
	
	@Test
	public void test4Files(){
		WcApplication wcApp = new WcApplication();
		String response = " 6488666 1095695  128457" + LINE_SEPARATOR 
				+ "    3576     592      71" + LINE_SEPARATOR
				+ "     306      53      10" + LINE_SEPARATOR
				+ "      99       0      99";
		assertEquals(response, wcApp.printAllCountsInFile("wc -mwl " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt " 
				+ RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text1 "
				+ RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2 "
				+ RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "textblank"));		
	}
	
	@Test
	public void testInvalidFile(){
		WcApplication wcApp = new WcApplication();
		assertEquals("Invalid File", wcApp.printAllCountsInFile("wc -mwl " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "noexist.txt"));				
	}
	
	@Test
	public void testNoFileStdinNull(){
		WcApplication wcApp = new WcApplication();
		assertEquals("Specify file or stdin", wcApp.printNewlineCountInStdin("wc -mwl", null));		
	}
	
	@Test
	public void testInvalidOption(){
		WcApplication wcApp = new WcApplication();
		assertEquals("Invalid Options", wcApp.printAllCountsInFile("wc -stu " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "big.txt"));	
	}
	
	@Test
	public void testRunPrintCharacterCountInFile(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-m", RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2"}; 
		try {
			wcApp.run(arguments, null, System.out);
			assertEquals("     306", out.toString());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRunPrintWordCountInFile(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-w", RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2"}; 
		try {
			wcApp.run(arguments, null, System.out);
			assertEquals("      53", out.toString());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRunPrintNewlineCountInFile(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-l", RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2"}; 
		try {
			wcApp.run(arguments, null, System.out);
			assertEquals("      10", out.toString());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
			fail();
		}		
	}

	@Test
	public void testRunPrintAllCountsInFile(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-m", "-w", "-l", RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "text2"}; 
		try {
			wcApp.run(arguments, null, System.out);
			assertEquals("     306      53      10", out.toString());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
			fail();
		}	
	}

	@Test
	public void testRunPrintCharacterCountInStdin(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-m"}; 
		try {
			InputStream stdin = writeToStdin("textblank");
			wcApp.run(arguments, stdin, System.out);
			assertEquals("      99", out.toString());
		} catch (AbstractApplicationException | IOException e) {
			e.printStackTrace();
			fail();
		}		
	}

	@Test
	public void testRunPrintWordCountInStdin(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-w"}; 
		try {
			InputStream stdin = writeToStdin("textblank");
			wcApp.run(arguments, stdin, System.out);
			assertEquals("       0", out.toString());
		} catch (AbstractApplicationException | IOException e) {
			e.printStackTrace();
			fail();
		}		
	}

	@Test
	public void testRunPrintNewlineCountInStdin(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-l"}; 
		try {
			InputStream stdin = writeToStdin("textblank");
			wcApp.run(arguments, stdin, System.out);
			assertEquals("      99", out.toString());
		} catch (AbstractApplicationException | IOException e) {
			e.printStackTrace();
			fail();
		}		
	}

	@Test
	public void testRunPrintAllCountsInStdin(){
		WcApplication wcApp = new WcApplication();
		System.setOut(new PrintStream(out));
		String[] arguments = {"-mwl"}; 
		try {
			InputStream stdin = writeToStdin("textblank");
			wcApp.run(arguments, stdin, System.out);
			assertEquals("      99       0      99", out.toString());
		} catch (AbstractApplicationException | IOException e) {
			e.printStackTrace();
			fail();
		}		
	}
	

}

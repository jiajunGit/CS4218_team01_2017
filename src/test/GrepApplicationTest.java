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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {
	
    private static String absTestDirPath;
    private static String relativeDirPath;
    private static String baseDirName;
    
    private static File tempFileOne;
    private static File tempFileTwo;
    private static File tempFileThree;
    
    private static GrepApplication grep;
    
    @BeforeClass
    public static void setup(){
        
        grep = new GrepApplication();
        
        absTestDirPath = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                         + Symbol.PATH_SEPARATOR_S + "grep";
        
        relativeDirPath = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "grep";
        
        baseDirName = "grep";
        
        cleanUpFilesLeftOver();
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @AfterClass
    public static void tearDown(){
        
        cleanUpFilesLeftOver();
        
        grep = null;
        
        absTestDirPath = "";
        relativeDirPath = "";
        baseDirName = "";
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @Before
    public void preTest() {
        
        cleanUpFilesLeftOver();
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @After
    public void postTest() {
        
        cleanUpFilesLeftOver();
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
	@Test(expected=GrepException.class)
	public void testRunNullArgs() throws GrepException {
		grep.run( null, System.in, System.out );
	}

	@Test(expected=GrepException.class)
    public void testRunNullStdin() throws GrepException, IOException {
        
	    String pattern = "some";
        
	    String[] args = { pattern };
	    
	    grep.run( args, null, System.out );
    }
	
	@Test(expected=GrepException.class)
    public void testRunNullStdout() throws GrepException, IOException {
        
        String pattern = "some";
        
        String[] args = { pattern };
        
        grep.run( args, System.in, null );
    }
	
	@Test(expected=GrepException.class)
    public void testRunInvalidArgumentCount() throws GrepException {
        
        String[] args = {};
        
        grep.run( args, System.in, System.out );
    }
	
	@Test(expected=GrepException.class)
    public void testRunClosedStdout() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                         + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                         + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        
        String[] args = { pattern };
        
        InputStream stdin = new ByteArrayInputStream(content.getBytes());
        OutputStream stdout = new FileOutputStream(tempFileOne);
        stdout.close();
        
        grep.run( args, stdin, stdout );
    }
	
	@Test(expected=GrepException.class)
    public void testRunClosedStdin() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                         + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                         + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] args = { pattern };
        
        InputStream stdin = new FileInputStream(tempFileOne);
        stdin.close();
        
        grep.run( args, stdin, System.out );
    }
	
	@Test
    public void testRunGrepFromStdin() throws GrepException, IOException {
        
	    String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                         + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                         + "seom  some";
	    
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] args = { pattern };
        
        InputStream stdin = new FileInputStream(tempFileOne);
        OutputStream stdout = new ByteArrayOutputStream();
        
        grep.run( args, stdin, stdout );
        
        stdin.close();
        
        String output = stdout.toString();
        String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                          + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testRunGrepFromOneFile() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                         + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                         + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] args = { pattern, filePathOne };
        
        OutputStream stdout = new ByteArrayOutputStream();
        
        grep.run( args, null, stdout );
        
        String output = stdout.toString();
        String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                          + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testRunGrepFromEmptyFile() throws GrepException, IOException {
        
        String pattern = "some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        
        String[] args = { pattern, filePathOne };
        
        OutputStream stdout = new ByteArrayOutputStream();
        
        grep.run( args, null, stdout );
        
        String output = stdout.toString();
        String expected = "";
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testRunGrepFromMultipleFiles() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String[] args = { pattern, filePathOne, filePathTwo, filePathThree };
        
        OutputStream stdout = new ByteArrayOutputStream();
        
        grep.run( args, null, stdout );
        
        String output = stdout.toString();
        
        assertEquals( output, expected );
    }
	
	@Test(expected=GrepException.class)
	public void testRunWithClosedOutputStream() throws IOException, GrepException {
	    
	    String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String[] args = { pattern };
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        OutputStream stdout = new FileOutputStream(tempFileOne);
        stdout.write(content.getBytes());
        stdout.close();
        
        InputStream stdin = new ByteArrayInputStream(content.getBytes());
        
        grep.run( args, stdin, stdout );
	}
	
	@Test(expected=GrepException.class)
    public void testRunWithClosedInputStream() throws IOException, GrepException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String[] args = { pattern };
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        InputStream stdin = new FileInputStream(tempFileOne);
        stdin.close();
        
        grep.run( args, stdin, System.out );
    }
	
	@Test
	public void testGrepFromStdinEmptyPattern() throws GrepException {
		
		String pattern = "";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
		                + Symbol.NEW_LINE_S + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, content );
	}
	
	@Test
    public void testGrepFromStdinEmptyPatternWithNewLineAtTheFront() throws GrepException {
        
        String pattern = "";
        String content = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                        + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
                        + Symbol.NEW_LINE_S + "seom  some";
        
        InputStream is = new ByteArrayInputStream( content.getBytes() );
        
        String output = grep.grepFromStdin( pattern, is );
        
        String expected = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                          + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
                          + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromStdinEmptyPatternWithNewLineAtTheBack() throws GrepException {
        
        String pattern = "";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                        + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
                        + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        InputStream is = new ByteArrayInputStream( content.getBytes() );
        
        String output = grep.grepFromStdin( pattern, is );
        
        String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals( output, expected );
    }
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinNullPattern() throws GrepException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" 
		                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grep.grepFromStdin( null, is );
	}

	@Test(expected=GrepException.class)
	public void testGrepFromStdinNullInputStream() throws GrepException {
	    
		String pattern = "pattern";
		grep.grepFromStdin( pattern, null );
	}
	
	@Test
	public void testGrepFromStdinEmptyInputStream() throws GrepException {
		
		String pattern = "some";
		
		String content = "";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "" );
	}
	
	@Test
    public void testInterfaceGrepFromMultipleFiles() throws GrepException, IOException {
	    
	    String pattern = "some";
	    
	    String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                         absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some" + Symbol.NEW_LINE_S;
        
        String command = "grep " + pattern + " \"" + filePathOne + "\"" + " \"" + filePathTwo + "\"" + " \"" + filePathThree + "\"";
        
        String output = grep.grepFromMultipleFiles(command);
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testInterfaceGrepInvalidPatternInFile() throws GrepException, IOException {
        
        String pattern = "?*some*";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String command = "grep " + pattern + " \"" + filePath + "\"";
        
        String expected = "grep:Invalid pattern specified";
        
        String output = grep.grepInvalidPatternInFile(command);
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testInterfaceGrepFromOneFile() throws GrepException, IOException {
	    
	    String pattern = "some";
	    
	    String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                + "seom  some";
	    
	    String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
	    
	    String command = "grep " + pattern + " \"" + filePath + "\"";
	    
	    String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                          + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
	    
	    String output = grep.grepFromOneFile(command);
	    
	    assertEquals( output, expected );
	}
	
	@Test
    public void testInterfaceGrepInvalidPatternInStdin() throws GrepException, IOException {
        
        String pattern = "?*some*";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String command = "grep " + pattern + " <" + filePath;
        
        String expected = "grep:Invalid pattern specified";
        
        String output = grep.grepInvalidPatternInStdin(command);
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testInterfaceGrepFromStdin() throws GrepException, IOException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String command = "grep " + pattern + " <" + filePath;
        
        String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String output = grep.grepFromStdin(command);
        
        assertEquals( output, expected );
    }
	
	@Test
	public void testGrepFromStdin() throws GrepException {
		
		String pattern = "some";
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
		                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
		                + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		              + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" );
	}
	
	@Test
    public void testGrepFromStdinWithNewLineAtTheBack() throws GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                        + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                        + "seom  some" + Symbol.NEW_LINE_S;
        
        InputStream is = new ByteArrayInputStream( content.getBytes() );
        
        String output = grep.grepFromStdin( pattern, is );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                      + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromStdinWithNewLineAtTheFront() throws GrepException {
        
        String pattern = "some";
        
        String content = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
                        + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
                        + "seom  some" ;
        
        InputStream is = new ByteArrayInputStream( content.getBytes() );
        
        String output = grep.grepFromStdin( pattern, is );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some" 
                      + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
	public void testGrepFromStdinDelimiterAtStartOfContent() throws GrepException {
		
		String pattern = "some";
		
		String content = Symbol.NEW_LINE_S + "  some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
		                + Symbol.NEW_LINE_S + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
		              + Symbol.NEW_LINE_S + "seom  some" );
	}
	
	@Test
	public void testGrepFromStdinDelimiterBeforeContent() throws GrepException {
		
		String pattern = "some";
		
		String content = " " + Symbol.NEW_LINE_S + "  some day" + Symbol.NEW_LINE_S + "    somsome some\t" 
		                     + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
		                     + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		              + "seom  some" );
	}
	
	@Test
	public void testGrepFromStdinContentStartingWithSpaces() throws GrepException {
		
		String pattern = "some";
		
		String content = "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" 
		              + Symbol.NEW_LINE_S + "seom  some" );
	}
	
	@Test
	public void testGrepFromStdinContentEndingWithSpaces() throws GrepException {
		
		String pattern = "some";
		
		String content = "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some  \t";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		              + "seom  some  \t" );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinInValidPattern() throws GrepException {
		
		String pattern = "*?some?";
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                  + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grep.grepFromStdin( pattern, is );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinClosedInputStream() throws GrepException, IOException {
		
		String pattern = "some";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
		                 + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
		
		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		FileInputStream inStream = new FileInputStream(tempFileOne);
		inStream.close();
		
		grep.grepFromStdin( pattern, inStream );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullPattern() throws GrepException, IOException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		grep.grepFromOneFile( null, filePath );
	}
	
	@Test
    public void testGrepFromOneFileEmptyPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                          + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = grep.grepFromOneFile( "", filePath );
        
        assertEquals(out, content);
    }
	
	@Test
    public void testGrepFromOneFileEmptyPatternWithNewLineAtTheFront() throws GrepException, IOException {
        
        String content = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" ;
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = grep.grepFromOneFile( "", filePath );
        
        String expected = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
                          + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals(out, expected);
    }
	
	@Test
    public void testGrepFromOneFileEmptyPatternWithNewLineAtTheBack() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                          + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = grep.grepFromOneFile( "", filePath );
        
        String expected = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                          + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        assertEquals(out, expected);
    }
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullFileName() throws GrepException, IOException {
		
		String pattern = "some";
		
		grep.grepFromOneFile( pattern, null );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileMissingFile() throws GrepException, IOException {
		
		String pattern = "some";
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
		assertTrue( tempFileOne.delete() );
		
		grep.grepFromOneFile( pattern, filePath );
	}
	
	@Test
    public void testGrepFromOneFileRelativePath() throws GrepException, IOException {
	    
	    String pattern = "some";
	    
	    String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

	    String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
	    
	    assertTrue(Environment.createNewFile(filePath));
	    
	    tempFileOne = new File(filePath);
	    tempFileOne.deleteOnExit();
	    
	    FileOutputStream outStream = new FileOutputStream(tempFileOne);
	    outStream.write(content.getBytes());
	    outStream.close();
	    
	    String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
	    String output = grep.grepFromOneFile( pattern, relativeFilePath );
	    
	    assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
	}
	
	@Test
    public void testGrepFromOneFileWithNewLineAtTheFront() throws GrepException, IOException {
        
        String pattern = "some";
        
        String content = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, relativeFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromOneFileWithNewLineAtTheBack() throws GrepException, IOException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, relativeFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromOneFileRelativePathWithCurrentDirectorySymbol() throws GrepException, IOException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, relativeFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromOneFileRelativePathWithPreviousDirectorySymbol() throws GrepException, IOException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, relativeFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromNonExistentFilePath() throws GrepException {
        
        String pattern = "some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        grep.grepFromOneFile( pattern, filePath );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromInvalidFilePath() throws GrepException {
        
        String pattern = "some";
        
        String filePath = ":grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        grep.grepFromOneFile( pattern, filePath );
    }
	
	@Test
    public void testGrepFromOneEmptyFile() throws GrepException, IOException {
        
        String pattern = "some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        String output = grep.grepFromOneFile( pattern, filePath );
        
        assertEquals( output, "" );
    }
	
	@Test
	public void testGrepFromOneFileWithValidFile() throws GrepException, IOException {
		
		String pattern = "some";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		String output = grep.grepFromOneFile( pattern, filePath );
		
		assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
	}
	
	@Test
    public void testGrepFromOneFileWithValidFileWithCurrentDirectorySymbol() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, inputFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromOneFileWithValidFileWithPreviousDirectorySymbol() throws GrepException, IOException {
        
        String pattern = "some";
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String output = grep.grepFromOneFile( pattern, inputFilePath );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesNullPattern() throws GrepException, IOException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		String[] arguments = { null, filePath };
	
		grep.grepFromMultipleFiles( arguments );
	}
    
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesWithNonExistentFilePaths() throws GrepException, IOException {
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", filePathOne, filePathTwo, filePathThree };
        
        grep.grepFromMultipleFiles( arguments );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesWithInvalidFilePaths() throws GrepException, IOException {
        
        String filePathOne = ":grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = ":grep" + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = ":grep" + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", filePathOne, filePathTwo, filePathThree };
        
        grep.grepFromMultipleFiles( arguments );
    }
	
	@Test
	public void testGrepFromMultipleFilesWithOneFileRelativePath() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, relativeFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithNewLineAtTheFront() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = Symbol.NEW_LINE_S + "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, relativeFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithNewLineAtTheBack() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, relativeFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithOneFileRelativePathWithCurrentDirectorySymbol() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, relativeFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithOneFileRelativePathWithPreviousDirectorySymbol() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, relativeFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithOneFile() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, inputFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithOneFileWithCurrentDirectorySymbol() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, inputFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesWithOneFileWithPreviousDirectorySymbol() throws IOException, GrepException {
        
        String pattern = "some";
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";

        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String[] arguments = { pattern, inputFilePath };
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" );
    }
	
	@Test
    public void testGrepFromMultipleFilesRelativePathsWithCurrentDirectorySymbol() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePathOne = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String relativeFilePathTwo = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2.txt";
        String relativeFilePathThree = relativeDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", relativeFilePathOne, relativeFilePathTwo, relativeFilePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesRelativePathsWithPreviousDirectorySymbol() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePathOne = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String relativeFilePathTwo = relativeDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String relativeFilePathThree = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", relativeFilePathOne, relativeFilePathTwo, relativeFilePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
	public void testGrepFromMultipleFilesRelativePaths() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String relativeFilePathOne = relativeDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String relativeFilePathTwo = relativeDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String relativeFilePathThree = relativeDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", relativeFilePathOne, relativeFilePathTwo, relativeFilePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesValidPatternWithCurrentDirectorySymbol() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "1.txt";
        String inputFilePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2.txt";
        
        String[] arguments = { "some", inputFilePathOne, inputFilePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesValidPatternWithPreviousDirectorySymbol() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String inputFilePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "1.txt";
        String inputFilePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + baseDirName + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        String[] arguments = { "some", inputFilePathOne, filePathTwo, inputFilePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesValidPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "some", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesInvalidPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "?*some*", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesEmptyPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S 
                         + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesEmptyPatternWithNewLineAtTheFront() throws GrepException, IOException {
        
        String content = Symbol.NEW_LINE_S + 
                         "some day" + Symbol.NEW_LINE_S 
                         + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "", filePathOne, filePathTwo, filePathThree };
    
        String expected = 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: " + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: " + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: " + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesEmptyPatternWithNewLineAtTheBack() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S 
                         + "    somsome some\t" + Symbol.NEW_LINE_S 
                         + "      somme" + Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt:       somme" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: " + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some";
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesEmptyFileName() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some";
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "some", filePathOne, "", filePathThree };
        
        grep.grepFromMultipleFiles( arguments );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesEmptyArgs() throws GrepException, IOException {
        
        String[] arguments = {};
        grep.grepFromMultipleFiles( arguments );
    }
	
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesNullArgs() throws GrepException, IOException {
        
	    String[] arguments = null;
	    grep.grepFromMultipleFiles( arguments );
    }
	
	private static boolean deleteFile(String absPath) {

        if (absPath == null) {
            return false;
        }

        if( !Environment.isExists(absPath) ){
            return true;
        }
        
        boolean isDeleted = false;
        File file = new File(absPath);
        try {
            isDeleted = file.delete();
        } catch (SecurityException e) {}

        return isDeleted;
    }
	
	private static String[] getPathsOfFilesCreated() {
	    String[] paths = {
            absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt",
            absTestDirPath + Symbol.PATH_SEPARATOR_S + "2.txt",
            absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt"
	    };
	    return paths;
	}
	
	private static void cleanUpFilesLeftOver() {
	    String[] paths = getPathsOfFilesCreated();
	    for(String path : paths) {
	        assertTrue(deleteFile(path));
	    }
	}
}

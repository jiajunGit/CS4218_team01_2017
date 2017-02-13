package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {
	
    private String absTestDirPath;
    private String relativeDirPath;
    private GrepApplication grep;
    
    @Before
    public void setUpBeforeTest(){
        grep = new GrepApplication();
        absTestDirPath = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test";
        relativeDirPath = "src" + Symbol.PATH_SEPARATOR_S + "test"; ;
    }
    
	@Test(expected=GrepException.class)
	public void testRunNullArgs() throws GrepException {
		grep.run( null, System.in, System.out );
	}

	@Test(expected=GrepException.class)
	public void testGrepFromStdinEmptyPattern() throws GrepException {
		
		String pattern = "";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
		                + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grep.grepFromStdin( pattern, is );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinNullPattern() throws GrepException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" 
		                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
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
	public void testGrepFromStdinValidPatternAndValidInputStream() throws GrepException {
		
		String pattern = "some";
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
		                + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
		                + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some" 
		              + Symbol.TAB + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S );
	}
	
	@Test
	public void testGrepFromStdinDelimiterAtStartOfContent() throws GrepException {
		
		String pattern = "some";
		
		String content = Symbol.NEW_LINE_S + "  some day" + Symbol.NEW_LINE_S + "    somsome some" 
		                + Symbol.TAB + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S 
		                + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day" + Symbol.NEW_LINE_S + "    somsome some" + Symbol.TAB 
		              + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S );
	}
	
	@Test
	public void testGrepFromStdinDelimiterBeforeContent() throws GrepException {
		
		String pattern = "some";
		
		String content = " " + Symbol.NEW_LINE_S + "  some day" + Symbol.NEW_LINE_S + "    somsome some\t" 
		                     + Symbol.NEW_LINE_S + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S 
		                     + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		              + "seom  some" + Symbol.NEW_LINE_S );
	}
	
	@Test
	public void testGrepFromStdinContentStartingWithSpaces() throws GrepException {
		
		String pattern = "some";
		
		String content = "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" 
		              + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S );
	}
	
	@Test
	public void testGrepFromStdinContentEndingWithSpaces() throws GrepException {
		
		String pattern = "some";
		
		String content = "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some  \t";
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grep.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		              + "seom  some  \t" + Symbol.NEW_LINE_S );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinInValidPattern() throws GrepException {
		
		String pattern = "*?some?";
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                  + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grep.grepFromStdin( pattern, is );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinClosedInputStream() throws GrepException, IOException {
		
		String pattern = "some";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
		                 + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
		
		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		FileInputStream inStream = new FileInputStream(tempFile);
		inStream.close();
		
		tempFile.deleteOnExit();
		
		grep.grepFromStdin( pattern, inStream );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullPattern() throws GrepException, IOException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		grep.grepFromOneFile( null, tempFile.getAbsolutePath() );
	}
	
	@Test(expected=GrepException.class)
    public void testGrepFromOneFileEmptyPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                          + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

        FileOutputStream outStream = new FileOutputStream(tempFile);
        outStream.write(content.getBytes());
        outStream.close();
        
        tempFile.deleteOnExit();
        
        grep.grepFromOneFile( "", tempFile.getAbsolutePath() );
    }
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullFileName() throws GrepException, IOException {
		
		String pattern = "some";
		
		grep.grepFromOneFile( pattern, null );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileMissingFile() throws GrepException, IOException {
		
		String pattern = "some";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
		assertTrue( tempFile.delete() );
		
		grep.grepFromOneFile( pattern, tempFile.getAbsolutePath() );
	}
	
	@Test
    public void testGrepFromOneFileRelativePath() throws GrepException, IOException {
	    
	    String pattern = "some";
	    
	    String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;

	    String filePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
	    
	    assertTrue(Environment.createNewFile(filePath));
	    
	    File tempFile = new File(filePath);
	    tempFile.deleteOnExit();
	    
	    FileOutputStream outStream = new FileOutputStream(tempFile);
	    outStream.write(content.getBytes());
	    outStream.close();
	    
	    String output = grep.grepFromOneFile( pattern, tempFile.getAbsolutePath() );
	    
	    tempFile.delete();
	    
	    assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" 
                + Symbol.NEW_LINE_S );
	}
	
	@Test(expected=GrepException.class)
    public void testGrepFromNonExistentFilePath() throws GrepException {
        
        String pattern = "some";
        
        String filePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        
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
        
        String filePath = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        
        assertTrue(Environment.createNewFile(filePath));
        
        File tempFile = new File(filePath);
        tempFile.deleteOnExit();
        
        String output = grep.grepFromOneFile( pattern, filePath );
        
        tempFile.delete();
        
        assertEquals( output, "" );
    }
	
	@Test
	public void testGrepFromOneFileValidPatternAndValidFile() throws GrepException, IOException {
		
		String pattern = "some";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		String output = grep.grepFromOneFile( pattern, tempFile.getAbsolutePath() );
		
		assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" 
		              + Symbol.NEW_LINE_S );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesNullPattern() throws GrepException, IOException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		String[] arguments = { null, tempFile.getAbsolutePath() };
	
		grep.grepFromMultipleFiles( arguments );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesEmptyPattern() throws GrepException, IOException {
	    
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

        FileOutputStream outStream = new FileOutputStream(tempFile);
        outStream.write(content.getBytes());
        outStream.close();
        
        tempFile.deleteOnExit();
        
        String[] arguments = { "", tempFile.getAbsolutePath() };
    
        grep.grepFromMultipleFiles( arguments );
	}
    
	@Test(expected=GrepException.class)
    public void testGrepFromMultipleFilesWithNonExistentFilePaths() throws GrepException, IOException {
        
        String filePathOne = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt";
        
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
	public void testGrepFromMultipleFilesRelativePaths() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePathOne = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = relativeDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        File tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        File tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        File tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "some", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some" + Symbol.NEW_LINE_S;
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        tempFileOne.delete();
        tempFileTwo.delete();
        tempFileThree.delete();
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesValidPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePathOne = absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt";
        String filePathTwo = absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt";
        String filePathThree = absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt";
        
        assertTrue(Environment.createNewFile(filePathOne));
        File tempFileOne = new File(filePathOne);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathTwo));
        File tempFileTwo = new File(filePathTwo);
        tempFileTwo.deleteOnExit();
        outStream = new FileOutputStream(tempFileTwo);
        outStream.write(content.getBytes());
        outStream.close();
        
        assertTrue(Environment.createNewFile(filePathThree));
        File tempFileThree = new File(filePathThree);
        tempFileThree.deleteOnExit();
        outStream = new FileOutputStream(tempFileThree);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "some", filePathOne, filePathTwo, filePathThree };
    
        String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt: some day" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt:     somsome some\t" + Symbol.NEW_LINE_S + 
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "1.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "2.txt: seom  some" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt: some day" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt:     somsome some\t" + Symbol.NEW_LINE_S +
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "grep" + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some" + Symbol.NEW_LINE_S;
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        tempFileOne.delete();
        tempFileTwo.delete();
        tempFileThree.delete();
        
        assertEquals( output, expected );
    }
	
	 @After
     public void tearDown() {
        grep = null;
        absTestDirPath = "";
        relativeDirPath = "";
     }
}

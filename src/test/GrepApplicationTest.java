package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    
    private static File tempFileOne;
    private static File tempFileTwo;
    private static File tempFileThree;
    
    private static GrepApplication grep;
    
    @BeforeClass
    public static void setup(){
        
        grep = new GrepApplication();
        
        absTestDirPath = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "grep";
        relativeDirPath = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "grep";
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @AfterClass
    public static void tearDown(){
        
        grep = null;
        
        absTestDirPath = "";
        relativeDirPath = "";
        
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        assertTrue( deleteFile(tempFileThree) );
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @Before
    public void preTest() {
        
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        assertTrue( deleteFile(tempFileThree) );
        
        tempFileOne = null;
        tempFileTwo = null;
        tempFileThree = null;
    }
    
    @After
    public void postTest() {
        
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        assertTrue( deleteFile(tempFileThree) );
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
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		grep.grepFromOneFile( null, filePath );
	}
	
	@Test(expected=GrepException.class)
    public void testGrepFromOneFileEmptyPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                          + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        grep.grepFromOneFile( "", filePath );
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
	    
	    assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" 
                + Symbol.NEW_LINE_S );
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
	public void testGrepFromOneFileValidPatternAndValidFile() throws GrepException, IOException {
		
		String pattern = "some";
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
		String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

		FileOutputStream outStream = new FileOutputStream(tempFileOne);
		outStream.write(content.getBytes());
		outStream.close();
		
		String output = grep.grepFromOneFile( pattern, filePath );
		
		assertEquals( output, "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "seom  some" 
		              + Symbol.NEW_LINE_S );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesNullPattern() throws GrepException, IOException {
		
		String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S 
		                 + "      somme" + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
		
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
	public void testGrepFromMultipleFilesEmptyPattern() throws GrepException, IOException {
	    
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR_S + "1.txt";
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();

        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String[] arguments = { "", filePath };
    
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
	public void testGrepFromMultipleFilesRelativePaths() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
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
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some" + Symbol.NEW_LINE_S;
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	@Test
    public void testGrepFromMultipleFilesValidPattern() throws GrepException, IOException {
        
        String content = "some day" + Symbol.NEW_LINE_S + "    somsome some\t" + Symbol.NEW_LINE_S + "      somme" 
                         + Symbol.NEW_LINE_S + Symbol.NEW_LINE_S + "seom  some" + Symbol.NEW_LINE_S;
        
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
                          absTestDirPath + Symbol.PATH_SEPARATOR_S + "3.txt: seom  some" + Symbol.NEW_LINE_S;
        
        String output = grep.grepFromMultipleFiles( arguments );
        
        assertEquals( output, expected );
    }
	
	private static boolean deleteFile( File file ) {
	    
	    boolean isDeleted = false;
	    
	    if(file != null && file.exists()){
	        try { isDeleted = file.delete(); }
	        catch( SecurityException e ){}
	    } else {
	        isDeleted = true;
	    }
	    return isDeleted;
	}
}

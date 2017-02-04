package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {
	
	@Test(expected=GrepException.class)
	public void testRunNullArgs() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		grepApp.run( null, System.in, System.out );
	}

	@Test(expected=GrepException.class)
	public void testGrepFromStdinEmptyPattern() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "";
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grepApp.grepFromStdin( pattern, is );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinNullPattern() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grepApp.grepFromStdin( null, is );
	}

	@Test(expected=GrepException.class)
	public void testGrepFromStdinNullInputStream() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "pattern";
		
		grepApp.grepFromStdin( pattern, null );
	}
	
	@Test
	public void testGrepFromStdinEmptyInputStream() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = "";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "" );
	}
	
	@Test
	public void testGrepFromStdinValidPatternAndValidInputStream() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "some day\n    somsome some\t\nseom  some\n" );
	}
	
	@Test
	public void testGrepFromStdinDelimiterAtStartOfContent() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = "\n  some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day\n    somsome some\t\nseom  some\n" );
	}
	
	@Test
	public void testGrepFromStdinDelimiterBeforeContent() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = " \n  some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "  some day\n    somsome some\t\nseom  some\n" );
	}
	
	@Test
	public void testGrepFromStdinContentStartingWithSpaces() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = "   some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day\n    somsome some\t\nseom  some\n" );
	}
	
	@Test
	public void testGrepFromStdinContentEndingWithSpaces() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		String content = "   some day\n    somsome some\t\n      somme\n\nseom  some  \t";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		String output = grepApp.grepFromStdin( pattern, is );
		
		assertEquals( output, "   some day\n    somsome some\t\nseom  some  \t\n" );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinInValidPattern() throws GrepException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "*?some?";
		
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		InputStream is = new ByteArrayInputStream( content.getBytes() );
		
		grepApp.grepFromStdin( pattern, is );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromStdinClosedInputStream() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
		
		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		FileInputStream inStream = new FileInputStream(tempFile);
		inStream.close();
		
		tempFile.deleteOnExit();
		
		grepApp.grepFromStdin( pattern, inStream );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullPattern() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		grepApp.grepFromOneFile( null, tempFile.getAbsolutePath() );
	}
	
	@Test(expected=GrepException.class)
    public void testGrepFromOneFileEmptyPattern() throws GrepException, IOException {
        
        GrepApplication grepApp = new GrepApplication();
        
        String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
        
        File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

        FileOutputStream outStream = new FileOutputStream(tempFile);
        outStream.write(content.getBytes());
        outStream.close();
        
        tempFile.deleteOnExit();
        
        grepApp.grepFromOneFile( "", tempFile.getAbsolutePath() );
    }
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileNullFileName() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		grepApp.grepFromOneFile( pattern, null );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromOneFileMissingFile() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
		assertTrue( tempFile.delete() );
		
		grepApp.grepFromOneFile( pattern, tempFile.getAbsolutePath() );
	}
	
	@Test
	public void testGrepFromOneFileValidPatternAndValidFile() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String pattern = "some";
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		String output = grepApp.grepFromOneFile( pattern, tempFile.getAbsolutePath() );
		
		assertEquals( output, "some day\n    somsome some\t\nseom  some\n" );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesNullPattern() throws GrepException, IOException {
		
		GrepApplication grepApp = new GrepApplication();
		
		String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
		
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

		FileOutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(content.getBytes());
		outStream.close();
		
		tempFile.deleteOnExit();
		
		String[] arguments = { null, tempFile.getAbsolutePath() };
	
		grepApp.grepFromMultipleFiles( arguments );
	}
	
	@Test(expected=GrepException.class)
	public void testGrepFromMultipleFilesEmptyPattern() throws GrepException, IOException {
	    
	    GrepApplication grepApp = new GrepApplication();
        
        String content = "some day\n    somsome some\t\n      somme\n\nseom  some\n";
        
        File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

        FileOutputStream outStream = new FileOutputStream(tempFile);
        outStream.write(content.getBytes());
        outStream.close();
        
        tempFile.deleteOnExit();
        
        String[] arguments = { "", tempFile.getAbsolutePath() };
    
        grepApp.grepFromMultipleFiles( arguments );
	}
}

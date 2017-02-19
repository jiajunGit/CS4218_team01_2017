package test.ef2.sed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedApplicationTest {

    private static String absTestDirPath = "";
    private static String relativeDirPath = "";
    
    private static SedApplication sed;
    
    private static File tempFileOne;
    private static File tempFileTwo;
    
    private static InputStream stdin;
    private static OutputStream stdout;
    
    @BeforeClass
    public static void setup() {
        
        sed = new SedApplication();
        absTestDirPath = Environment.currentDirectory + Symbol.PATH_SEPARATOR + "src" + Symbol.PATH_SEPARATOR + "test" + Symbol.PATH_SEPARATOR + "sed";
        relativeDirPath = "src" + Symbol.PATH_SEPARATOR + "test" + Symbol.PATH_SEPARATOR + "sed";
        
        stdin = null;
        stdout = null;
        tempFileOne = null;
        tempFileTwo = null;
    }
    
    @AfterClass
    public static void tearDown() {
        
        assertTrue( closeStdin(stdin) );
        assertTrue( closeStdout(stdout) );
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        
        sed = null;
        absTestDirPath = "";
        relativeDirPath = "";
        stdin = null;
        stdout = null;
        tempFileOne = null;
        tempFileTwo = null;
    }
    
    @Before
    public void preTest() {
        
        assertTrue( closeStdin(stdin) );
        assertTrue( closeStdout(stdout) );
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        
        stdin = null;
        stdout = null;
        tempFileOne = null;
        tempFileTwo = null;
    }
    
    @After
    public void postTest() {
        
        assertTrue( closeStdin(stdin) );
        assertTrue( closeStdout(stdout) );
        assertTrue( deleteFile(tempFileOne) );
        assertTrue( deleteFile(tempFileTwo) );
        
        stdin = null;
        stdout = null;
        tempFileOne = null;
        tempFileTwo = null;
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunNullInputStream() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, null, stdout);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunClosedOutputStream() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String outFilepath = absTestDirPath + Symbol.PATH_SEPARATOR + "out.txt";
        assertTrue(Environment.createNewFile(outFilepath));
        tempFileTwo = new File(outFilepath);
        tempFileTwo.deleteOnExit();
        stdout = new FileOutputStream(tempFileTwo);
        stdout.close();
        
        stdin = new FileInputStream(tempFileOne);
        
        sed.run(args, stdin, stdout);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunNullOutputStream() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        
        sed.run(args, stdin, null);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunClosedInputStream() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdout = new ByteArrayOutputStream();
        stdin = new FileInputStream(tempFileOne);
        stdin.close();
        
        sed.run(args, stdin, stdout);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunInvalidArgsCount() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = new String[0];
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, stdin, stdout);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunNullArgs() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(null, stdin, stdout);
    }
    
    @Test
    public void testRunForAllReplacementInStdin() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, stdin, stdout);
        
        String out = stdout.toString();
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testRunForFirstReplacementInStdin() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, stdin, stdout);
        
        String out = stdout.toString();
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testRunForAllReplacementInFile() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, null, stdout);
        
        String out = stdout.toString();
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testRunForFirstReplacementInFile() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, null, stdout);
        
        String out = stdout.toString();
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;;
        
        assertEquals( out, expected );
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunNullFileNameArg() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { replacementString, null };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, stdin, stdout);
    }
    
    @Test(expected=AbstractApplicationException.class)
    public void testRunNullReplacementStringArg() throws IOException, AbstractApplicationException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String[] args = { null, filePath };
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        stdin = new FileInputStream(tempFileOne);
        stdout = new ByteArrayOutputStream();
        
        sed.run(args, stdin, stdout);
    }
    
    @Test
    public void testInvalidRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/*?night?/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceSubstringWithInvalidReplacement(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testInvalidReplacement() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morn/ing/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceSubstringWithInvalidReplacement(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplacementWithInvalidStart() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "ss/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceSubstringWithInvalidReplacement(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplacementWithInvalidEnd() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/gg";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceSubstringWithInvalidReplacement(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplacementWithOnlyRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/nightmorning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceSubstringWithInvalidReplacement(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringFromStdinWithEmptyReplacementString() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night//g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInStdin(command);
        String expected = "good  at " + Symbol.NEW_LINE_S + "all " + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringFromStdinWithEmptyRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s//morning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInStdin(command);
        String expected = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringFromStdinUsingOtherReplacementSeparator() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s@night@morning@g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInStdin(command);
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringFromStdin() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInStdin(command);
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFileRelativePath() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + relativeFilePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFileUsingOtherReplacementSeparator() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s@night@morning@g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFileWithEmptyReplacementString() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night//g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = "good  at " + Symbol.NEW_LINE_S + "all " + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFileWithEmptyRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s//morning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFile() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/g";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = "good morning at morning" + Symbol.NEW_LINE_S + "all morning" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInEmptyFile() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/g";
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "";
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceAllSubStringInFileInvalidFilePath() {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + ":test";
        String replacementString = "s/hi/there/g";
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        String out = sed.replaceAllSubstringsInFile(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInEmptyFile() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "";
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringFromStdinUsingOtherReplacementSeparator() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s@night@morning@";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringFromStdin(command);
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringFromStdinWithEmptyReplacmentString() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night//";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringFromStdin(command);
        String expected = "good  at " + Symbol.NEW_LINE_S + "all " + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringFromStdinWithEmptyRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s//morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringFromStdin(command);
        String expected = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringFromStdin() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " <\"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringFromStdin(command);
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFileRelativePath() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String relativeFilePath = relativeDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + relativeFilePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFileUsingOtherReplacementSeparator() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s@night@morning@";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFileUsingEmptyReplacementString() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night//";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "good  at " + Symbol.NEW_LINE_S + "all " + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFileUsingEmptyRegex() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s//morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFile() throws IOException {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + "1.txt";
        String replacementString = "s/night/morning/";
        String content = "good night at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        assertTrue(Environment.createNewFile(filePath));
        tempFileOne = new File(filePath);
        tempFileOne.deleteOnExit();
        FileOutputStream outStream = new FileOutputStream(tempFileOne);
        outStream.write(content.getBytes());
        outStream.close();
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = "good morning at night" + Symbol.NEW_LINE_S + "all night" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testReplaceFirstSubStringInFileInvalidFilePath() {
        
        String filePath = absTestDirPath + Symbol.PATH_SEPARATOR + ":test";
        String replacementString = "s/hi/there/";
        
        String command = "sed " + replacementString + " \"" + filePath + "\"";
        
        String out = sed.replaceFirstSubStringInFile(command);
        String expected = ""; // TODO replace with exception class string
        
        assertEquals( out, expected );
    }
    
    private static boolean deleteFile( File file ) {
        
        boolean isDeleted = false;
        
        if(file != null && file.exists()){
            try { 
                isDeleted = file.delete(); 
            }
            catch( SecurityException e ){}
        } else {
            isDeleted = true;
        }
        return isDeleted;
    }
    
    private static boolean closeStdin( InputStream inputStream ) {
        
        boolean isClosed = true;
        
        if(inputStream != null && inputStream != System.in){
            try { 
                inputStream.close();
            } catch( IOException e ){
                isClosed = false;
            }
        }
        return isClosed;
    }
    
    private static boolean closeStdout( OutputStream outputStream ) {
        
        boolean isClosed = true;
        
        if(outputStream != null && outputStream != System.out){
            try { 
                outputStream.close();
            } catch( IOException e ){
                isClosed = false;
            }
        }
        return isClosed;
    }
}

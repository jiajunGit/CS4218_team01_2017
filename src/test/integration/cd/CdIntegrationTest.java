package test.integration.cd;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CdIntegrationTest {

    private static ShellImpl shell;
    
    private static byte[] buf;
    
    public static final String NEW_LINE = System.lineSeparator();
    public static final String PATH_SEPARATOR = File.separator;

    public static final String NEW_LINE_IN_EXPECTED = getNewLineInExpectedFile();
    
    private static final String RELATIVE_EXPECTED_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                              + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + "expected";

    private static final String ABSOLUTE_EXPECTED_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_EXPECTED_DIRECTORY;
    
    private static final String TEST_DIRECTORY_ONE_NAME = "testDirOne";
    
    private static final String RELATIVE_TEST_DIRECTORY_ONE = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                              + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_ONE_NAME;

    private static final String ABSOLUTE_TEST_DIRECTORY_ONE = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY_ONE;
    
    private static final String TEST_DIRECTORY_TWO_NAME = "testDirTwo";
    
    private static final String RELATIVE_TEST_DIRECTORY_TWO = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                              + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_TWO_NAME;

    private static final String ABSOLUTE_TEST_DIRECTORY_TWO = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY_TWO;
    
    private static final String TEST_DIRECTORY_THREE_NAME = "testDirThree";
    
    private static final String RELATIVE_TEST_DIRECTORY_THREE = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                                + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_THREE_NAME;

    private static final String ABSOLUTE_TEST_DIRECTORY_THREE = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY_THREE;
    
    private static final String TEST_EMPTY_DIRECTORY_NAME = "emptyDir";
    
    private static final String RELATIVE_EMPTY_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                                + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_EMPTY_DIRECTORY_NAME;
    
    private static final String ABSOLUTE_EMPTY_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_EMPTY_TEST_DIRECTORY;
    
    @BeforeClass
    public static void setup() {
        shell = new ShellImpl();
        buf = new byte[10000];
    }
    
    @Before
    public void setupBeforeTest() {
        Environment.setDefaultDirectory();
    }
    
    @After
    public void tearDownAfterTest() {
        deleteFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt");
        deleteFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in");
        deleteFile(ABSOLUTE_TEST_DIRECTORY_THREE + PATH_SEPARATOR + "in");
        deleteFile(ABSOLUTE_EMPTY_TEST_DIRECTORY);
        Environment.setDefaultDirectory();
    }
    
    @Test
    public void testCdWithHead() throws AbstractApplicationException, ShellException {
        
        String command = "cd " + RELATIVE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "| cd *.|head -n 4 in";
        
        String fileContents = "   February 2020    " + NEW_LINE
                            + "Su Mo Tu We Th Fr Sa" + NEW_LINE
                            + "                  1 " + NEW_LINE
                            + "2  3  4  5  6  7  8 " + NEW_LINE
                            + "9  10 11 12 13 14 15" + NEW_LINE
                            + "16 17 18 19 20 21 22" + NEW_LINE
                            + "23 24 25 26 27 28 29";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "   February 2020    " + NEW_LINE
                        + "Su Mo Tu We Th Fr Sa" + NEW_LINE
                        + "                  1 " + NEW_LINE
                        + "2  3  4  5  6  7  8 " + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testCdWithPwd() throws AbstractApplicationException, ShellException {
        
        String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + "*" + PATH_SEPARATOR + "." + PATH_SEPARATOR 
                       + ".." + PATH_SEPARATOR + TEST_DIRECTORY_ONE_NAME + "|pwd";
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = ABSOLUTE_TEST_DIRECTORY_ONE + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testCdWithCd() throws AbstractApplicationException, ShellException {
        
        String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + "; cd ..; cd ." + PATH_SEPARATOR + TEST_DIRECTORY_TWO_NAME 
                       + "; cat in";
        
        String fileContents = "Reached destination!";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = NEW_LINE + NEW_LINE + NEW_LINE + "Reached destination!" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testCdWithCal() throws AbstractApplicationException, ShellException {
        
        String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + " >; cal `cat *in`";
        
        String fileContents = "2 2020" + NEW_LINE;
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_THREE + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "   February 2020    " + NEW_LINE
                        + "Su Mo Tu We Th Fr Sa" + NEW_LINE
                        + "                  1 " + NEW_LINE
                        + "2  3  4  5  6  7  8 " + NEW_LINE
                        + "9  10 11 12 13 14 15" + NEW_LINE
                        + "16 17 18 19 20 21 22" + NEW_LINE
                        + "23 24 25 26 27 28 29" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    private static String getContents( String fileName ) {
        
        String out = null;
        try {
            
            FileInputStream fs = new FileInputStream(fileName);
            
            StringBuilder sb = new StringBuilder();
            while( true ){
                int bytesRead = fs.read(buf);
                if(bytesRead < 0){
                    break;
                }
                sb.append(new String( buf, 0, bytesRead ));
            }
            out = sb.toString();
            
            fs.close();
            
        } catch (IOException e) {}
        return out;
    }
    
    private static String getNewLineInExpectedFile() {
        return getContents( ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "newLine" );
    }
    
    private String makeContentsCompatible( String contents ) {
        
        if( contents == null ){
            return "";
        }
        
        String newLine = NEW_LINE_IN_EXPECTED != null ? NEW_LINE_IN_EXPECTED : "";
        newLine = Pattern.quote(newLine);
        contents.replaceAll(newLine, NEW_LINE);
        
        return contents;
    }
    
    private static boolean createFile( String fileName, String content ) {
        
        if( fileName == null || content == null ){
            return false;
        }
        
        fileName = Environment.getAbsPath(fileName);
        
        if(!Environment.isExists(fileName)){
            if( !Environment.createNewFile(fileName)){
                return false;
            }
        } else if( Environment.isDirectory(fileName) ){
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
    
    private static boolean createDirectory( String dirName ) {
        
        if (dirName == null) {
            return false;
        }

        String tempDirName = Environment.getAbsPath(dirName);
        if( Environment.isDirectory(tempDirName) ){
            return true;
        }
        
        boolean isCreated = false;
        File file = new File(dirName);
        try {
            isCreated = file.mkdir();
        } catch (SecurityException e) {}

        return isCreated;
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
}

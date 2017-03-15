package test.integration.tail;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TailIntegrationTest {

    private static ShellImpl shell;
    
    private static byte[] buf;
    
    public static final String NEW_LINE = System.lineSeparator();
    public static final String PATH_SEPARATOR = File.separator;
    
    public static final String NEW_LINE_IN_EXPECTED = getNewLineInExpectedFile();
    
    private static final String RELATIVE_EXPECTED_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                              + PATH_SEPARATOR + "tail" + PATH_SEPARATOR + "expected";
    
    private static final String ABSOLUTE_EXPECTED_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_EXPECTED_DIRECTORY;
    
    private static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" 
                                                          + PATH_SEPARATOR + "tail" + PATH_SEPARATOR + "testDir";
    
    private static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY;
    
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
        deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out");
        deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in");
        deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2");
        deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in3");
        deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in4");
        Environment.setDefaultDirectory();
    }
    
    @Test
    public void testTailWithWc() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 9 <" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in > " 
                         + ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out; wc -m <" + ABSOLUTE_TEST_DIRECTORY 
                         + PATH_SEPARATOR + "out";
        
        String fileContents = "\t12" + NEW_LINE
                + "  11" + NEW_LINE
                + "10 \t\t\t\t " + NEW_LINE
                + "space time" + NEW_LINE
                + "time \"space time\"" + NEW_LINE
                + "\"space time\" space \"space time\" time  " + NEW_LINE
                + " \"hi `echo space` there\"6" + NEW_LINE
                + "5 " + NEW_LINE
                + "\"space time\"`echo '\"space time\"'`\"space time\"" + NEW_LINE
                + "\"hi space time there\"3" + NEW_LINE
                + "2  hi space time there" + NEW_LINE
                + "1 '\"space time\"'";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String readContents = getContents(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out");
        int expectedCharCount = readContents.getBytes().length;
        
        String expected = "     " + expectedCharCount + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithSed() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 12 " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "*in|sed s/\"`echo '\"space time\"'`\"/\"'t'\"/";
        
        String fileContents = "\t12" + NEW_LINE
                            + "  11" + NEW_LINE
                            + "10 \t\t\t\t " + NEW_LINE
                            + "space time" + NEW_LINE
                            + "time \"space time\"" + NEW_LINE
                            + "\"space time\" space \"space time\" time  " + NEW_LINE
                            + " \"hi `echo space` there\"6" + NEW_LINE
                            + "5 " + NEW_LINE
                            + "\"space time\"`echo '\"space time\"'`\"space time\"" + NEW_LINE
                            + "\"hi space time there\"3" + NEW_LINE
                            + "2  hi space time there" + NEW_LINE
                            + "1 '\"space time\"'";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "\t12" + NEW_LINE
                        + "  11" + NEW_LINE
                        + "10 \t\t\t\t " + NEW_LINE
                        + "space time" + NEW_LINE
                        + "time t" + NEW_LINE
                        + "t space \"space time\" time  " + NEW_LINE
                        + " \"hi `echo space` there\"6" + NEW_LINE
                        + "5 " + NEW_LINE
                        + "t`echo '\"space time\"'`\"space time\"" + NEW_LINE
                        + "\"hi space time there\"3" + NEW_LINE
                        + "2  hi space time there" + NEW_LINE
                        + "1 't'" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithDate() throws AbstractApplicationException, ShellException {
        
        String command = "tail " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in; date < `echo ''`";
        
        String fileContents = "";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
        String expected = ZonedDateTime.now().format(formatter);

        assertEquals(out, NEW_LINE + expected + NEW_LINE);
    }
    
    @Test
    public void testTailWithSort() throws AbstractApplicationException, ShellException {
        
        String command = "tail " + ABSOLUTE_TEST_DIRECTORY + "\"''\"" + PATH_SEPARATOR + "in|sort|tail -n 6";
        
        String fileContents = "12" + NEW_LINE
                            + "9128" + NEW_LINE
                            + "8122  " + NEW_LINE
                            + "300000" + NEW_LINE
                            + "-36" + NEW_LINE
                            + "+37" + NEW_LINE
                            + "14" + NEW_LINE
                            + "1" + NEW_LINE
                            + "1000" + NEW_LINE
                            + "17" + NEW_LINE
                            + "35" + NEW_LINE
                            + "100" + NEW_LINE
                            + "1000" + NEW_LINE
                            + "10" + NEW_LINE
                            + "0" + NEW_LINE
                            + "0" + NEW_LINE
                            + "21000" + NEW_LINE
                            + "+1000" + NEW_LINE
                            + "+0010" + NEW_LINE
                            + "212" + NEW_LINE
                            + "200" + NEW_LINE
                            + "2100" + NEW_LINE
                            + "200";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "10" + NEW_LINE
                        + "200" + NEW_LINE
                        + "200" + NEW_LINE
                        + "2100" + NEW_LINE
                        + "21000" + NEW_LINE
                        + "212" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithGrep() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 7 \"" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in\"|grep '\"hi `echo space` there\"'";
        
        String fileContents = "12" + NEW_LINE
                            + "  11" + NEW_LINE
                            + "10  " + NEW_LINE
                            + "9\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8" + NEW_LINE
                            + "8\t8\t8\t8\t8\t8\"hi `echo space` there\"\t8\t8\t8 " + NEW_LINE
                            + "  7hi `echo space` there  " + NEW_LINE
                            + " \"hi `echo space` there\"6" + NEW_LINE
                            + "5 " + NEW_LINE
                            + "4\t8\t8\t8\"hi `echo space` there\"\t8\t8\t8\t8\t8" + NEW_LINE
                            + "\"hi space there\"3" + NEW_LINE
                            + "2  hi space there" + NEW_LINE
                            + "1" + NEW_LINE;
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = " \"hi `echo space` there\"6" + NEW_LINE
                        + "4\t8\t8\t8\"hi `echo space` there\"\t8\t8\t8\t8\t8" + NEW_LINE;

        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithTail() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 8 '" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in'|tail -n 4";
        
        String fileContents = "12" + NEW_LINE
                            + "  11" + NEW_LINE
                            + "10  " + NEW_LINE
                            + "9\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8" + NEW_LINE
                            + "8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8 " + NEW_LINE
                            + "  7  " + NEW_LINE
                            + " 6" + NEW_LINE
                            + "5 " + NEW_LINE
                            + "4\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8" + NEW_LINE
                            + "3" + NEW_LINE
                            + "2  " + NEW_LINE
                            + "1" + NEW_LINE;
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "4\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8\t8" + NEW_LINE
                        + "3" + NEW_LINE
                        + "2  " + NEW_LINE
                        + "1" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithEcho() throws AbstractApplicationException, ShellException {
        
        String currentDir = Environment.currentDirectory;
        
        String command = "tail " + currentDir + PATH_SEPARATOR + "`echo \"`echo \"" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in\"`\"`";
        
        String fileContents = "12" + NEW_LINE
                            + "11" + NEW_LINE
                            + "10" + NEW_LINE
                            + "9" + NEW_LINE
                            + "8" + NEW_LINE
                            + "7" + NEW_LINE
                            + "6" + NEW_LINE
                            + "5" + NEW_LINE
                            + "4" + NEW_LINE
                            + "3" + NEW_LINE
                            + "2" + NEW_LINE
                            + "1";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "10" + NEW_LINE
                        + "9" + NEW_LINE
                        + "8" + NEW_LINE
                        + "7" + NEW_LINE
                        + "6" + NEW_LINE
                        + "5" + NEW_LINE
                        + "4" + NEW_LINE
                        + "3" + NEW_LINE
                        + "2" + NEW_LINE
                        + "1" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithHead() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 1 `head -n 7 " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in > " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR 
                       + "out; echo '" + ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out'`";
        
        String fileContents = NEW_LINE
                            + NEW_LINE
                            + NEW_LINE
                            + NEW_LINE
                            + NEW_LINE
                            + NEW_LINE
                            + "Treasure" + NEW_LINE
                            + NEW_LINE 
                            + NEW_LINE;
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = "Treasure" + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithPwd() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 1 `pwd`" + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        
        String fileContents = "hi there" + NEW_LINE
                            + "hi all" + NEW_LINE 
                            + NEW_LINE;
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));
        
        String out = shell.parseAndEvaluate(command);
        
        String expected = NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithCd() throws AbstractApplicationException, ShellException {
        
        String command = "tail -n 17 <`head -n 20 "+ABSOLUTE_TEST_DIRECTORY+PATH_SEPARATOR+"out >"+RELATIVE_TEST_DIRECTORY+PATH_SEPARATOR+"in"
                       + ";echo '" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in'` > "  + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"
                       + "; cd " + RELATIVE_TEST_DIRECTORY + ">"
                       + "; echo Here is your pepsi >" + ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in3"
                       + "; sed s/'COKE '/PEPSI/ in2 > in4 ;cat in3 in4";
        
        String fileContents = "hi there!                " + NEW_LINE 
                            + "  let me treat you to a  " + NEW_LINE 
                            + "\t bottle of virtual coke" + NEW_LINE
                            + "                         " + NEW_LINE
                            + "         ________        " + NEW_LINE
                            + "        (((_()___)       " + NEW_LINE
                            + "         || ^   |        " + NEW_LINE
                            + "         || ^   |        " + NEW_LINE
                            + "        // ^     \"      " + NEW_LINE
                            + "      /// ^      `\"     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^ COKE  ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      ||| ^       ||     " + NEW_LINE
                            + "      \"\\ ^     //      " + NEW_LINE
                            + "        ==========       " + NEW_LINE
                            + "                         " + NEW_LINE
                            + "   Treat me              " + NEW_LINE
                            + "to a Pepsi next time   \t";
        assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out", fileContents));

        String out = shell.parseAndEvaluate(command);
        
        String expected = "Here is your pepsi                         " + NEW_LINE
                        + NEW_LINE
                        + "         ________        " + NEW_LINE
                        + "        (((_()___)       " + NEW_LINE
                        + "         || ^   |        " + NEW_LINE
                        + "         || ^   |        " + NEW_LINE
                        + "        // ^     \"      " + NEW_LINE
                        + "      /// ^      `\"     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^ PEPSI ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      ||| ^       ||     " + NEW_LINE
                        + "      \"\\ ^     //      " + NEW_LINE
                        + "        ==========       " + NEW_LINE
                        + NEW_LINE;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testTailWithCal() throws AbstractApplicationException, ShellException {
        
        String command = "echo '' hi|tail; cal < ''";
                
        String out = shell.parseAndEvaluate(command);
        
        String currentMonthPath = getCurrentMonthAbsoluteFilePath();
        String contents = getContents(currentMonthPath);
        contents = makeContentsCompatible(contents);
        
        String expected = " hi" + NEW_LINE + contents;
        
        assertEquals(out, expected);
    }
    
    private static String getNewLineInExpectedFile() {
        return getContents( ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "newLine" );
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
    
    private String makeContentsCompatible( String contents ) {
        
        if( contents == null ){
            return "";
        }
        
        String newLine = NEW_LINE_IN_EXPECTED != null ? NEW_LINE_IN_EXPECTED : "";
        newLine = Pattern.quote(newLine);
        contents.replaceAll(newLine, NEW_LINE);
        
        return contents;
    }
    
    private int getCurrentMonth() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
    
    private String getCurrentMonthAbsoluteFilePath() {
        int currentMonth = getCurrentMonth();
        switch(currentMonth) {
            case Calendar.MARCH:
                return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMarch";
            case Calendar.APRIL:
                return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthApril";
            case Calendar.MAY:
                return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMay";
            case Calendar.JUNE:
                return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthJune";
            default:
                break;
        }
        return "";
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

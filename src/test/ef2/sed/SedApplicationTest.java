package test.ef2.sed;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedApplicationTest {

    private static SedApplication sed;
    
    public static final String PATH_SEPARATOR = File.separator;
    public static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "ef2" 
                                                         + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "sed";
    public static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + "src" 
                                                         + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR 
                                                         + "sed" + PATH_SEPARATOR + "sed";
    
    @BeforeClass
    public static void setup() {
        sed = new SedApplication();
    }
    
    @Before
    public void setupBeforeTest() {
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
    }
    
    @After
    public void tearDownAfterTest() {
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
        assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
    }
    
    @Test
    public void testSedWithSpaceRegex() throws SedException {
        
        String[] arg = { "s/ /tail/g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  head heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahead head" + Symbol.NEW_LINE_S
                         + "head who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "tailtailheadtailheattailoftailthetailmomenttailhi" + Symbol.NEW_LINE_S
                + "piehihaheadtailhead" + Symbol.NEW_LINE_S
                + "headtailwhotailaretailu?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedWithEmptyRegex() throws SedException {
        
        String[] arg = { "s//tail/g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  head heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahead head" + Symbol.NEW_LINE_S
                         + "head who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  head heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahead head" + Symbol.NEW_LINE_S
                + "head who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedWithSpaceReplacement() throws SedException {
        
        String[] arg = { "s/head/ /g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  head heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahead head" + Symbol.NEW_LINE_S
                         + "head who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "    heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha   " + Symbol.NEW_LINE_S
                + "  who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedWithEmptyReplacement() throws SedException {
        
        String[] arg = { "s/head//g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  head heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahead head" + Symbol.NEW_LINE_S
                         + "head who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "   heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha " + Symbol.NEW_LINE_S
                + " who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithGSeparator() throws SedException {

        String[] arg = { "sghihihigagg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                          + Symbol.NEW_LINE_S 
                          + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                          + "piehihaa a" + Symbol.NEW_LINE_S
                          + "a who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithGSeparatorAndEmptyReplacement() throws SedException {

        String[] arg = { "sghihihiggg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                        + Symbol.NEW_LINE_S 
                        + "   heat of the moment hi" + Symbol.NEW_LINE_S
                        + "piehiha " + Symbol.NEW_LINE_S
                        + " who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithGSeparatorAndEmptyRegex() throws SedException {

        String[] arg = { "sggagg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithGSeparatorAndEmptyRegexAndReplacement() throws SedException {

        String[] arg = { "sgggg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithTwoGSeparatorAtTheBack() throws SedException {

        String[] arg = { "sghihihigaggg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithTwoGSeparatorInTheMiddle() throws SedException {

        String[] arg = { "sghihihiggagg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithTwoGSeparatorAtTheFront() throws SedException {

        String[] arg = { "sgghihihigagg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test
    public void testSedReplaceFirstWithGSeparator() throws SedException {
        
        String[] arg = { "sghihihigag" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihaa hihihi" + Symbol.NEW_LINE_S
                         + "a who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithGSeparatorAndEmptyReplacement() throws SedException {
        
        String[] arg = { "sghihihigg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "   heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha hihihi" + Symbol.NEW_LINE_S
                + " who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithGSeparatorAndEmptyRegex() throws SedException {
        
        String[] arg = { "sggag" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithGSeparatorAndEmptyRegexAndReplacement() throws SedException {
        
        String[] arg = { "sggg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }

    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithTwoGSeparatorInTheMiddle() throws SedException {
        
        String[] arg = { "sghihihiggag" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihaa hihihi" + Symbol.NEW_LINE_S
                         + "a who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithTwoGSeparatorAtTheFront() throws SedException {
        
        String[] arg = { "sgghihihigag" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihaa hihihi" + Symbol.NEW_LINE_S
                         + "a who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithSSeparator() throws SedException {
        
        String[] arg = { "sshihihisasg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihaa a" + Symbol.NEW_LINE_S
                + "a who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithSSeparatorAndEmptyReplacement() throws SedException {
        
        String[] arg = { "sshihihissg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "   heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha " + Symbol.NEW_LINE_S
                + " who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithSSeparatorAndEmptyRegex() throws SedException {
        
        String[] arg = { "sssasg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceAllWithSSeparatorAndEmptyRegexAndReplacement() throws SedException {
        
        String[] arg = { "ssssg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsAtTheBack() throws SedException {
        
        String[] arg = { "sshihihisassg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsInTheMiddle() throws SedException {
        
        String[] arg = { "sshihihissasg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsAtTheFront() throws SedException {
        
        String[] arg = { "ssshihihisasg" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test
    public void testSedReplaceFirstWithSSeparator() throws SedException {
        
        String[] arg = { "sshihihisas" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihaa hihihi" + Symbol.NEW_LINE_S
                + "a who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithSSeparatorAndEmptyReplacement() throws SedException {
        
        String[] arg = { "sshihihiss" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "   heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha hihihi" + Symbol.NEW_LINE_S
                + " who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithSSeparatorAndEmptyRegex() throws SedException {
        
        String[] arg = { "sssas" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test
    public void testSedReplaceFirstWithSSeparatorAndEmptyRegexAndReplacement() throws SedException {
        
        String[] arg = { "ssss" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithTwoSSeparatorAtTheBack() throws SedException {
        
        String[] arg = { "sshihihisass" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithTwoSSeparatorsInTheMiddle() throws SedException {
        
        String[] arg = { "sshihihissas" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithTwoSSeparatorsAtTheFront() throws SedException {
        
        String[] arg = { "ssshihihisas" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S ; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test
    public void testSedWithBackSlashAsSeparator() throws SedException {
        
        String[] arg = { "s\\hihihi\\a\\g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihaa a" + Symbol.NEW_LINE_S
                + "a who are u?" + Symbol.NEW_LINE_S; 

        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedWithInvalidRegex() throws SedException {
        
        String[] arg = { "s\\*?hihihi\\a\\g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceAllWithExtraSeparatorAtTheBack() throws SedException {
        
        String[] arg = { "s\\hihihi\\a\\g\\" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithMissingSeparatorAtTheBack() throws SedException {
        
        String[] arg = { "s\\hihihi\\a" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout); 
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithGSuffixAtTheBack() throws SedException {
        
        String[] arg = { "s\\hihihi\\g" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithInvalidSuffixAtTheBack() throws SedException {
        
        String[] arg = { "s\\hihihi\\k" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout); 
    }
    
    @Test(expected=SedException.class)
    public void testSedReplaceFirstWithNoReplacementStringAndSeparatorAtTheBack() throws SedException {
        
        String[] arg = { "s\\hihihi\\" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout); 
    }
    
    @Test
    public void testSedReplaceFirstWithNoReplacementStringAndNoRegex() throws SedException {
        
        String[] arg = { "s\\\\\\" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                         + "hihihi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout); 
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testSedReplaceFirstWithOverlappingMatchedSubString() throws SedException {
        
        String[] arg = { "s\\hihihi\\%\\" };
        String content = Symbol.NEW_LINE_S 
                         + Symbol.NEW_LINE_S 
                         + "  hihihihihi heat of the moment hi" + Symbol.NEW_LINE_S
                         + "piehihahihihi hihihihihi" + Symbol.NEW_LINE_S
                         + "hihihihhi who are u?" + Symbol.NEW_LINE_S; 
        
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, stdin, stdout); 
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  %hihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehiha% hihihihihi" + Symbol.NEW_LINE_S
                + "%hhi who are u?" + Symbol.NEW_LINE_S;
        
        assertEquals(out, expected);
    }
    
    @Test
    public void testSedWithRelativeFilePath() throws SedException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        
        assertTrue( createFile(fileName, content) );
        
        String relativePath = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        String[] arg = { "s\\hihihi\\a\\g", relativePath };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihaa a" + Symbol.NEW_LINE_S
                + "a who are u?" + Symbol.NEW_LINE_S; 

        assertEquals( out, expected );
    }
    
    @Test
    public void testSedWithAbsoluteFilePath() throws SedException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        
        assertTrue( createFile(fileName, content) );
        
        String[] arg = { "s\\hihihi\\a\\g", fileName };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
        
        String out = new String(stdout.toByteArray());
        
        String expected = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  a heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihaa a" + Symbol.NEW_LINE_S
                + "a who are u?" + Symbol.NEW_LINE_S; 

        assertEquals( out, expected );
    }
    
    @Test(expected=SedException.class)
    public void testSedWithInvalidFilePath() throws SedException {
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2";
        
        String[] arg = { "s\\hihihi\\a\\g", fileName };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithNullOutput() throws SedException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        
        assertTrue( createFile(fileName, content) );
        
        String[] arg = { "s\\hihihi\\a\\g", fileName };
        
        sed.run(arg, null, null);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithNullInputAndNoFileSpecified() throws SedException {
        
        String[] arg = { "s\\hihihi\\a\\g" };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithNullArgs() throws SedException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        
        sed.run(null, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithZeroArgCount() throws SedException {
        
        String[] arg = {};
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithThreeArgCount() throws SedException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileOneName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        String fileTwoName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2";
        
        assertTrue( createFile(fileOneName, content) );
        assertTrue( createFile(fileTwoName, content) );
        
        String[] arg = { "s\\hihihi\\a\\g", fileOneName, fileTwoName };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithClosedOutput() throws SedException, IOException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        String outFileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out";
        
        assertTrue( createFile(fileName, content) );
        
        String[] arg = { "s\\hihihi\\a\\g", fileName };
        
        FileOutputStream stdout = new FileOutputStream(outFileName);
        stdout.close();
        
        sed.run(arg, null, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithClosedInput() throws SedException, IOException {
        
        String content = Symbol.NEW_LINE_S 
                + Symbol.NEW_LINE_S 
                + "  hihihi heat of the moment hi" + Symbol.NEW_LINE_S
                + "piehihahihihi hihihi" + Symbol.NEW_LINE_S
                + "hihihi who are u?" + Symbol.NEW_LINE_S;
        
        String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
        
        assertTrue( createFile(fileName, content) );
        
        String[] arg = { "s\\hihihi\\a\\g" };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        FileInputStream stdin = new FileInputStream(fileName);
        stdin.close();
        
        sed.run(arg, stdin, stdout);
    }
    
    @Test(expected=SedException.class)
    public void testSedWithDirectoryAsFilePath() throws SedException {
        
        String[] arg = { "s\\hihihi\\a\\g", ABSOLUTE_TEST_DIRECTORY };
        
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        
        sed.run(arg, null, stdout);
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

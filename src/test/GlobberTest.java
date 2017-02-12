package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.Globber;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobberTest {

    private Globber globber;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        globber = new Globber();
    }
    
    @Test
    public void testRelativePathWithConsecutiveGlobCharacters() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                       + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "271**";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 2 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "271.txt";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                + "2712" + Symbol.PATH_SEPARATOR_S + "2712";

        assertEquals( out[1], expected );
    }
    
    @Test
    public void testAbsolutePathWithConsecutiveGlobCharacters() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "271**";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 2 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "271.txt";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                + "2712" + Symbol.PATH_SEPARATOR_S + "2712";

        assertEquals( out[1], expected );
    }
    
    @Test
    public void testEmptyInput() throws ShellException {
        
        String inputSymbols = ShellImpl.generateSymbolString("");
        
        String[] out = globber.processGlob("", inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testAbsolutePathWithEndingGlobCharacter() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "271*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 2 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "271.txt";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                + "2712" + Symbol.PATH_SEPARATOR_S + "2712";

        assertEquals( out[1], expected );
    }
    
    @Test
    public void testRelativePathWithEndingGlobCharacter() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                      + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "271*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 2 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "271.txt";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                + "2712" + Symbol.PATH_SEPARATOR_S + "2712";

        assertEquals( out[1], expected );
    }
    
    @Test
    public void testRelativePathWithEndingPathSeparator() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                        + ".cab.car" + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "*12" + Symbol.PATH_SEPARATOR_S;
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testRelativePathWithStartingPathSeparator() throws ShellException {
        
        String input = Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" 
                       + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testRelativePathWithStartingGlobCharacter() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                      + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testRelativePathWithPreviousDirectorySymbol() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                       + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                       + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testRelativePathWithCurrentDirectorySymbol() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                       + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testAbsolutePathWithPreviousDirectorySymbol() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S + "glob" 
                       + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testAbsolutePathWithCurrentDirectorySymbol() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S 
                       + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testAbsolutePathWithEndingPathSeparator() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "*12" + Symbol.PATH_SEPARATOR_S;
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testAbsolutePathWithStartingGlobCharacter() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                      + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                      + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "*12";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testRelativePathWithSpecialCharacters() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                       + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "27.*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testAbsolutePathWithSpecialCharacters() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "27.*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testRelativePathWithEscapeCharacters() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                       + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "\\Q2712\\E*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testAbsolutePathWithEscapeCharacters() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                       + "2712" + Symbol.PATH_SEPARATOR_S + "\\Q2712\\E*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testPathWithNonMatchingCases() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "Src" + Symbol.PATH_SEPARATOR_S 
                       + "tESt" + Symbol.PATH_SEPARATOR_S + "glOb" + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        Environment.setDefaultDirectory();
        
        String[] expected = {
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "Src" + Symbol.PATH_SEPARATOR_S + "tESt" + Symbol.PATH_SEPARATOR_S 
                + "glOb" + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "file with spaces", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "Src" + Symbol.PATH_SEPARATOR_S + "tESt" + Symbol.PATH_SEPARATOR_S 
                + "glOb" + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "file with spaces.txt", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "Src" + Symbol.PATH_SEPARATOR_S + "tESt" + Symbol.PATH_SEPARATOR_S 
                + "glOb" + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "New folder"
        };
        
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
    }
    
    @Test
    public void testPathWithSpaces() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S 
                       + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        Environment.setDefaultDirectory();
        
        String[] expected = {
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "file with spaces", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "file with spaces.txt", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "New folder"
        };
        
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
    }
    
    @Test
    public void testRelativePathWithSingleGlobCharacter() throws ShellException {
        
        Environment.currentDirectory = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S 
                                       + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces";
        
        String input = "*";

        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
        
        Environment.setDefaultDirectory();
        
        String[] expected = {
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "file with spaces", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "file with spaces.txt", 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "New folder"
        };
        
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
    }
    
    @Test
    public void testRelativePath() throws ShellException {
        
        String input = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S 
                        + "ca*"  + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712*";
  
        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 6 );
        
        String[] expected = { 
                
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712",
                              
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712.txt", 
                              
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712", 
                              
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712.txt", 
                              
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712", 
                              
                Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712.txt" 
        }; 
   
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
        assertEquals( out[3], expected[3] );
        assertEquals( out[4], expected[4] );
        assertEquals( out[5], expected[5] );
    }
    
    @Test
    public void testMultipleDirectoriesInMultipleDirectories() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "ca*"  + Symbol.PATH_SEPARATOR_S + "c*";
  
        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 8 );
        
        String[] expected = { Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" 
        }; 
   
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
        assertEquals( out[3], expected[3] );
        assertEquals( out[4], expected[4] );
        assertEquals( out[5], expected[5] );
        assertEquals( out[6], expected[6] );
        assertEquals( out[7], expected[7] );
    }
    
    @Test
    public void testMultipleFilesInMultipleDirectories() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "ca*"  + Symbol.PATH_SEPARATOR_S + "cat" 
                + Symbol.PATH_SEPARATOR_S + "2712*";
  
        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 6 );
        
        String[] expected = { Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712",
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712.txt", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712.txt", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712", 
                              
                              Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                              + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" 
                              + Symbol.PATH_SEPARATOR_S + "2712.txt" }; 
   
        assertEquals( out[0], expected[0] );
        assertEquals( out[1], expected[1] );
        assertEquals( out[2], expected[2] );
        assertEquals( out[3], expected[3] );
        assertEquals( out[4], expected[4] );
        assertEquals( out[5], expected[5] );
    }
    
    @Test
    public void testMultipleDirectories() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car"  + Symbol.PATH_SEPARATOR_S + "ca*";
  
        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 3 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab";

        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car";
        
        assertEquals( out[1], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat";
        
        assertEquals( out[2], expected );
    }
    
    @Test
    public void testSingleDirectory() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car"  + Symbol.PATH_SEPARATOR_S + "-ca*";
  
        String inputSymbols = ShellImpl.generateSymbolString(input);
          
        String[] out = globber.processGlob(input, inputSymbols);
          
        assertTrue( out.length == 1 );
          
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr";
          
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testInvalidCharactersInPath() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                    + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ":(-.-):" + Symbol.PATH_SEPARATOR_S + "-.-" 
                    + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testNonExistentPath() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                    + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "(-.-)" + Symbol.PATH_SEPARATOR_S + "-.-" 
                    + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testMultiplePathSeparator() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S 
                + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" 
                + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 3 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                          + Symbol.PATH_SEPARATOR_S + "car";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                   + Symbol.PATH_SEPARATOR_S + "car.txt";
        
        assertEquals( out[1], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                   + Symbol.PATH_SEPARATOR_S + "carrier";
        
        assertEquals( out[2], expected );
    }
    
    @Test
    public void testMultipleFileGlob() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                + Symbol.PATH_SEPARATOR_S + "c*r*";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 3 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                          + Symbol.PATH_SEPARATOR_S + "car";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                   + Symbol.PATH_SEPARATOR_S + "car.txt";
        
        assertEquals( out[1], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                   + Symbol.PATH_SEPARATOR_S + "carrier";
        
        assertEquals( out[2], expected );
    }
    
    @Test
    public void testMultipleFilesInSingleDirectory() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                       + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                       + Symbol.PATH_SEPARATOR_S + "c*r";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 2 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S 
                          + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car";
        
        assertEquals( out[0], expected );
        
        expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                   + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-" 
                   + Symbol.PATH_SEPARATOR_S + "carrier";
        
        assertEquals( out[1], expected );
    }
    
    @Test
    public void testSingleFile() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                      + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                      + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "27*2";
        
        String inputSymbols = ShellImpl.generateSymbolString(input);
        
        String[] out = globber.processGlob(input, inputSymbols);
        
        assertTrue( out.length == 1 );
        
        String expected = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                          + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S 
                          + "2712" + Symbol.PATH_SEPARATOR_S + "2712";
        
        assertEquals( out[0], expected );
    }
    
    @Test
    public void testNullInputSymbol() throws ShellException {
        
        String input = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S + "test" 
                + Symbol.PATH_SEPARATOR_S + "glob" + Symbol.PATH_SEPARATOR_S + ".cab.car" 
                + Symbol.PATH_SEPARATOR_S +"2712" + Symbol.PATH_SEPARATOR_S + "27*2";
        
        String[] out = globber.processGlob(input, null);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testNullInput() throws ShellException {
        
        String inputSymbols = ShellImpl.generateSymbolString(null);
        
        String[] out = globber.processGlob(null, inputSymbols);
        
        assertTrue( out.length == 0 );
    }
    
    @Test
    public void testNullArguments() throws ShellException {
        String[] out = globber.processGlob(null, null);
        assertTrue( out.length == 0 );
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        globber = null;
    }
}

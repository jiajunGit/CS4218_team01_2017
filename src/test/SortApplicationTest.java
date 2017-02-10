package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "sort"
			+ PATH_SEPARATOR;
	
	@BeforeClass
	public static void setUpOnce(){
		try {
			copyInputToTestFolder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDownOnce(){
	}
	
	private static void copyInputToTestFolder() throws IOException {
		File folder = new File(RELATIVE_TEST_DIRECTORY + "input");
		System.out.println(folder.getAbsolutePath());
	    for (final File fileEntry : folder.listFiles()) {
	    	Path source = Paths.get(fileEntry.getAbsolutePath());
	    	Path dest = Paths.get(RELATIVE_TEST_DIRECTORY + "toTest" 
	    + PATH_SEPARATOR + fileEntry.getName());
	    	Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
	    }	
	}

	@Test
	public void testSortStringsSimple() {
		
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortStringsSimple";
		assertEquals("simple sort", "a\nb\nc\nd\ne", sort.sortStringsSimple("sort "+file));
	}

	@Test
	public void testSortStringsCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortStringsCapital";
		assertEquals("simple sort", "A\nB\nL\nL\nS", sort.sortStringsCapital("sort "+file));
	}

	@Test
	public void testSortNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortNumbers";
		assertEquals("simple sort", "1\n10\n2\n6\n8\n9", sort.sortNumbers("sort "+file));
	}

	@Test
	public void testSortSimpleCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleCapital";
		assertEquals("simple sort", "A\nE\nO\nT\nf\nr\ns\nt\nw", sort.sortSimpleCapital("sort "+file));
	}

	@Test
	public void testSortSimpleNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleNumbers";
		assertEquals("simple sort", "4\n5\n6\n7\nh\ni\nj\nk\nl", sort.sortSimpleNumbers("sort "+file));
	}

	@Test
	public void testSortSimpleSpecialChars() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		assertEquals("simple sort", "\"\n\'\n*\n;\n<\n>\n`\n|", sort.sortSimpleNumbers("sort "+file));
	}

}

package sg.edu.nus.comp.cs4218.test.app;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {

	@Test
	public void testSortStringsSimple() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "a\nb\nc\nd\ne", sort.sortStringsSimple("e\nd\nc\nb\na"));
	}

	@Test
	public void testSortStringsCapital() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "A\nB\nL\nL\nS", sort.sortStringsCapital("B\nA\nL\nL\nS"));
	}

	@Test
	public void testSortNumbers() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "1\n10\n2\n6\n8\n9", sort.sortNumbers("6\n2\n1\n9\n8\n10"));
	}

	@Test
	public void testSortSimpleCapital() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "A\nE\nO\nT\nf\nr\ns\nt\nw", sort.sortSimpleCapital("s\nO\nf\nt\nT\nw\nA\nr\nE"));
	}

	@Test
	public void testSortSimpleNumbers() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "4\n5\n6\n7\nh\ni\nj\nk\nl", sort.sortSimpleNumbers("l\n7\nk\n6\nj\n5\ni\n4\nh"));
	}

	@Test
	public void testSortSimpleSpecialChars() {
		SortApplication sort = new SortApplication();
		assertEquals("simple sort", "\"\n\'\n*\n;\n<\n>\n`\n|", sort.sortSimpleNumbers(";\n*\n|\n\'\n>\n<\n\"\n`"));
	}

}

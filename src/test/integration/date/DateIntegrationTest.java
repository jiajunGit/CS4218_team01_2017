package test.integration.date;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class DateIntegrationTest {
	private ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	
	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		Environment.setDefaultDirectory();
	}

	@After
	public void tearDown() throws Exception {
		Environment.setDefaultDirectory();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

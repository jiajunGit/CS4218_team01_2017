package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;

public final class GlobTestHelper {

	public static void setupGlobFiles(String absTestDirPath) {

		String[] dirsToCreate = getDirectoriesToCreate(absTestDirPath);
		String[] filesToCreate = getFilesToCreate(absTestDirPath);

		for (String filePath : filesToCreate) {
			deleteFile(filePath);
		}
		for (int i = dirsToCreate.length - 1; i >= 0; --i) {
			deleteFile(dirsToCreate[i]);
		}

		for (String dirPath : dirsToCreate) {
			File dir = createNewDirectory(dirPath);
			assertTrue(dir != null);
			dir.deleteOnExit();
		}
		for (String filePath : filesToCreate) {
			File file = createNewDirectory(filePath);
			assertTrue(file != null);
			file.deleteOnExit();
		}
	}

	private static File createNewDirectory(String absPath) {

		if (absPath == null) {
			return null;
		}

		boolean isCreated = false;
		File file = new File(absPath);

		if (file.exists()) {
			return file;
		}

		try {
			isCreated = file.mkdir();
		} catch (SecurityException e) {
		}

		return isCreated ? file : null;
	}

	public static File createNewFile(String absPath) {

		if (absPath == null) {
			return null;
		}

		boolean isCreated = false;
		File file = new File(absPath);

		if (file.exists()) {
			return file;
		}

		try {
			isCreated = file.createNewFile();
		} catch (IOException | SecurityException e) {
		}

		return isCreated ? file : null;
	}

	private static boolean deleteFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		if (!Environment.isExists(absPath)) {
			return true;
		}

		boolean isDeleted = false;
		File file = new File(absPath);
		try {
			isDeleted = file.delete();
		} catch (SecurityException e) {
		}

		return isDeleted;
	}

	private static String[] getDirectoriesToCreate(String absTestDirPath) {

		if (absTestDirPath == null) {
			return new String[0];
		}
		String[] directories = { absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S
						+ "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces", absTestDirPath + Symbol.PATH_SEPARATOR_S
						+ "file with spaces" + Symbol.PATH_SEPARATOR_S + "New folder" };
		return directories;
	}

	private static String[] getFilesToCreate(String absTestDirPath) {

		if (absTestDirPath == null) {
			return new String[0];
		}
		String[] files = {
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "carrier",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + ".cab.car"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "271.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712 2712 2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "-.-"
						+ Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cab"
						+ Symbol.PATH_SEPARATOR_S + "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "car"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "2712"
						+ Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-.-.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "-carr.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + ".cab.car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cab.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "car.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "cat.txt",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces.txt" };
		return files;
	}

	private GlobTestHelper() {
	}
}

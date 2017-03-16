package sg.edu.nus.comp.cs4218;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Environment {

	/**
	 * Java VM does not support changing the current working directory. For this
	 * reason, we use Environment.currentDirectory instead.
	 */
	public static volatile String currentDirectory = System.getProperty("user.dir");

	private Environment() {
	};

	public static void setDefaultDirectory() {
		currentDirectory = System.getProperty("user.dir");
	}

	public static boolean isExists(String absPath) {

		if (absPath == null) {
			return false;
		}
		File file = new File(absPath);
		return file.exists();
	}

	public static boolean isFile(String absPath) {

		if (absPath == null) {
			return false;
		}
		File file = new File(absPath);
		return file.isFile();
	}

	public static boolean isDirectory(String absPath) {

		if (absPath == null) {
			return false;
		}
		File file = new File(absPath);
		return file.isDirectory();
	}

	public static boolean isHidden(String absPath) {

		if (absPath == null) {
			return false;
		}
		File file = new File(absPath);
		return file.isHidden();
	}

	public static boolean isAbsPath(String path) {

		if (path == null) {
			return false;
		}
		File file = new File(path);
		return file.isAbsolute();
	}

	/*
	 * WARNING: This method only constructs the full path without all the . or
	 * .. or extra /. It does NOT mean that the path constructed exists [ Use
	 * isFile() or isExists() or isDirectory() ].
	 *
	 * Returns empty string if absolute path cannot be created
	 *
	 * Supports both absolute path and relative path and . and ..
	 */
	public static String getAbsPath(String file) {
		String path = file;
		
		if (path == null || path.isEmpty()) {
			return "";
		}

		String absPath = "";
		try {
			if (!isAbsPath(path)) {
				String oldPath = path;
				path = currentDirectory;
				path += Symbol.PATH_SEPARATOR;
				path += oldPath;
			}
			Path filePath = Paths.get(path);
			absPath = filePath.normalize().toString();
		} catch (InvalidPathException e) {
			return "";
		}
		return absPath;
	}

	/*
	 * WARNING: This method only constructs the parent path. It does NOT mean
	 * that the path constructed exists [ Use isFile() or isExists() or
	 * isDirectory() ].
	 * 
	 * Returns empty string if parent path cannot be created
	 */
	public static String getParentPathFrom(String filePath) {
		String path = filePath;

		if (path == null) {
			return "";
		}
		File file = new File(path);
		path = file.getParent();
		return (path != null ? path : "");
	}

	public static boolean createNewFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		boolean isCreated = false;
		File file = new File(absPath);
		try {
			isCreated = file.createNewFile();
		} catch (IOException | SecurityException e) {
		}

		return isCreated;
	}

	public static boolean deleteFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		boolean isDeleted = false;
		File file = new File(absPath);
		try {
			isDeleted = file.delete();
		} catch (SecurityException e) {
		}

		return isDeleted;
	}

	public static boolean createNewDirectory(String absPath) {

		if (absPath == null) {
			return false;
		}

		boolean isCreated = false;
		File file = new File(absPath);
		try {
			isCreated = file.mkdir();
		} catch (SecurityException e) {
		}

		return isCreated;
	}
}

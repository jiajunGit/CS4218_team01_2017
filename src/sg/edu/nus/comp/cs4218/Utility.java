package sg.edu.nus.comp.cs4218;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Utility {

    /**
     * Static method to pipe data from an outputStream to an inputStream, for
     * the evaluation of the Pipe Commands.
     * 
     * @param outputStream
     *            Source outputStream to get stream from.
     * 
     * @return InputStream with data piped from the outputStream.
     * 
     * @throws ShellException
     *             If exception is thrown during piping.
     */
    public static InputStream outputStreamToInputStream(OutputStream outputStream) {
        return new ByteArrayInputStream( ((ByteArrayOutputStream) outputStream).toByteArray());
    }
    
    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.startsWith("Windows");
    }
    
    private Utility() {}
}

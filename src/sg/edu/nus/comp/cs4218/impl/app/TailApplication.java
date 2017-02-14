package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Tail;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplication implements Tail {

	String[] lines;
	
	private static final String FILE_NOT_FOUND = "Specify a file which exists";
	private static final String INVALID_FORMAT = "Invalid command format";
	private static final String NUMBER_NOT_SPECIFIED = "Specify proper number with \"-n\" option";
	private static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	private static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
	private static final String ERROR_IO_READING = "IO ERROR WHEN READING FILE";
	
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		try {
			if( stdout == null ){
				throw new TailException(ERROR_EXP_INVALID_OUTSTREAM);
			}
			int noLines = 10;
			
			if(args!=null && args.length!=0){
				if(args.length==3){
					if(args[2].equals("-n")){
						throw new TailException(INVALID_FORMAT);
					}
					if(args[1].equals("-n")){
						if(isInteger(args[2])){
							noLines = Integer.parseInt(args[2]);
							if(noLines<0 || noLines>Integer.MAX_VALUE) throw new TailException(NUMBER_NOT_SPECIFIED);
							load(args[0]);
						}else{
							throw new TailException(NUMBER_NOT_SPECIFIED);
						}
					}else if(args[0].equals("-n")){
						if(isInteger(args[1])){
							noLines = Integer.parseInt(args[1]);
							if(noLines<0 || noLines>Integer.MAX_VALUE) throw new TailException(NUMBER_NOT_SPECIFIED);
							load(args[2]);
						}else{
							throw new TailException(NUMBER_NOT_SPECIFIED);
						}
					}else{
						throw new TailException(INVALID_FORMAT);
					}
				}else if(args.length==2){
					if(args[0].equals("-n")){
						if(isInteger(args[1])){
							noLines = Integer.parseInt(args[1]);
							if(noLines<0 || noLines>Integer.MAX_VALUE) throw new TailException(NUMBER_NOT_SPECIFIED);
							if(stdin==null) throw new TailException(ERROR_EXP_INVALID_INSTREAM);
							loadFromStdIn(stdin);
						}else{
							throw new TailException(NUMBER_NOT_SPECIFIED); 
						}
					}else{
						throw new TailException(INVALID_FORMAT);
					}
				}else if(args.length==1){
					if(args[0].equals("-n")){
						throw new TailException(NUMBER_NOT_SPECIFIED);
					}else{
						load(args[0]);
					}
				}else{
					throw new TailException(INVALID_FORMAT); //Invalid input
				}
			}else{
				if (stdin!=null)
					loadFromStdIn(stdin);
				else throw new TailException(ERROR_EXP_INVALID_INSTREAM);				
			}
			
			printLines(noLines);
		} catch (NumberFormatException e) {
			throw new TailException(NUMBER_NOT_SPECIFIED);
		} catch (IOException e) {
			throw new TailException(ERROR_IO_READING);
		}
	}
	
	

	private void printLines(int noLines) {
		int startIndex = lines.length - noLines;
		startIndex = startIndex < 0 ? 0 : startIndex;
		for(int i = startIndex; i<lines.length; i++){
			//if(noLines == i) <-- do this for head
			//	break;
			System.out.println(lines[i]);
		}
		
	}



	private void loadFromStdIn(InputStream stdin){
		String wholeText = convertStreamToString(stdin);
		lines = wholeText.split("[" + System.getProperty("line.separator") + "]");
	}
	
	private void load(String fileName) throws IOException, TailException {
		File workingFile = new File(fileName);
		Vector<String> linesVector; 
		
		if (workingFile.exists()) {
			linesVector = importFile(fileName);
			lines = linesVector.toArray(new String[linesVector.size()]);
			
		} else {
			throw new TailException(FILE_NOT_FOUND);
		}
	}	
	
	private Vector<String> importFile(String fileName) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				fileName));
		Vector<String> linesVector = new Vector<String>();
		
		try {
			String line = bufferedReader.readLine();

			while (line != null) {
				linesVector.add(line);
				line = bufferedReader.readLine();
			}
			
			return linesVector;
		} finally {
			bufferedReader.close();
		}
	}	
	
	static String convertStreamToString(InputStream stdin) {
		try(Scanner s = new Scanner(stdin).useDelimiter("\\A");)
		{
			return s.hasNext() ? s.next() : "";
		}
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
}

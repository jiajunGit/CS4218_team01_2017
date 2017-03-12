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
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplication implements Tail {
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	String[] lines;

	private static final String FILE_NOT_FOUND = "Specify a file which exists";
	private static final String INVALID_FORMAT = "Invalid command format";
	private static final String NUMBER_NOT_SPECIFIED = "Specify proper number with \"-n\" option";
	private static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	private static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
	private static final String ERROR_IO_READING = "IO ERROR WHEN READING FILE";

	private static final int TEMP_BUF_SZ = 10000;
	private static byte[] tempBuf = new byte[TEMP_BUF_SZ];

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		try {
			if( stdout == null ){
				throw new TailException(ERROR_EXP_INVALID_OUTSTREAM);
			}
			int noLines = 10; //default 10 lines
			if(args==null || args.length==0){
				loadFromStdIn(stdin);
			}
			else if (args.length==1){
				logicFor1Argument(args, stdin);
			}
			else if (args.length==2){
				noLines = logicFor2Arguments(args, stdin, noLines);
			}
			else if (args.length==3){ 
				noLines = logicFor3Arguments(args, stdin, noLines);
			}
			else{
				throw new TailException(INVALID_FORMAT);
			}
			printLines(noLines, stdout);
		} catch (NumberFormatException e) {
			throw new TailException(NUMBER_NOT_SPECIFIED);
		} catch (IOException e) {
			throw new TailException(ERROR_IO_READING);
		}
	}

	private void logicFor1Argument(String[] args, InputStream stdin) throws TailException, IOException {
		if(args[0].length()==0){
			loadFromStdIn(stdin); //stdin 10 lines
		}
		else{
			load(args[0]); //tail [file], tail [option]
		}

	}

	private int logicFor2Arguments(String[] args, InputStream stdin, int noLines) throws TailException, IOException {
		if(args[0].length()==0){
			if(args[1].length()==0){
				loadFromStdIn(stdin); //tail "",""
			}
			else{ //tail "",[number]/[file]
				load(args[1]);
			}
		}
		else if(args[0].equals("-n")){
			if( isNumeric(args[1]) ){ //tail -n, 10
				if(args[1].contains("-") || args[1].contains("+")){
					throw new TailException(NUMBER_NOT_SPECIFIED);
				}
				noLines=Integer.parseInt(args[1]);
				loadFromStdIn(stdin);
			}
			else{
				throw new TailException(NUMBER_NOT_SPECIFIED);
			}
		}
		else{
			throw new TailException(INVALID_FORMAT); //tail -nxx file
		}
		return noLines;
	}

	private int logicFor3Arguments(String[] args, InputStream stdin, int noLines) throws TailException, IOException {
		if(args[0].length()==0 && args[1].length()==0){
			if(args[2].length()==0){ //tail "" "" ""
				loadFromStdIn(stdin);
			}
			else{
				load(args[2]); //tail "" "" file
			}
		}
		else if (args[0].equals("-n")){ //tail -n x x
			if(isNumeric(args[1])){
				if(args[1].contains("-") || args[1].contains("+")){
					throw new TailException(NUMBER_NOT_SPECIFIED);
				}
				if(args[2].length()==0){ //tail -n 10 ""
					noLines=Integer.parseInt(args[1]);
					loadFromStdIn(stdin);
				}
				else{
					noLines=Integer.parseInt(args[1]); //tail -n 10 file
					load(args[2]);
				}
			}
			else{
				throw new TailException(NUMBER_NOT_SPECIFIED);
			}
		}
		else{
			throw new TailException(INVALID_FORMAT); //head -xxx 10 file
		}
		return noLines;
	}

	private void printLines(int noLines, OutputStream stdout ) throws IOException {
		int startIndex = lines.length - noLines;
		startIndex = startIndex < 0 ? 0 : startIndex;
		for(int i = startIndex; i<lines.length; i++){
			//if(noLines == i) <-- do this for head
			//	break;
			stdout.write(lines[i].getBytes());
			if(i!= lines.length-1){
				stdout.write(LINE_SEPARATOR.getBytes());
			}
		}

	}

	private void loadFromStdIn(InputStream stdin) throws TailException{
		if(stdin==null){
			throw new TailException(ERROR_EXP_INVALID_INSTREAM);
		}
		else{
			String wholeText = convertStreamToString(stdin);
			lines = wholeText.split(LINE_SEPARATOR);
		}
	}

	private Boolean isNumeric(String s){
		try{ 
			int i = Integer.parseInt(s); return true;
		}
		catch(NumberFormatException e){ 
			return false; 
		}
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

	static String convertStreamToString(InputStream stdin) throws TailException {
		int bytesRead = 0;
		StringBuilder content = new StringBuilder();
		while (true) {
			try {
				bytesRead = stdin.read(tempBuf);
				if (bytesRead <= -1) {
					break;
				}
				content.append(new String(tempBuf, 0, bytesRead));
			} catch (IOException e) {
				throw new TailException(ERROR_EXP_INVALID_INSTREAM);
			}
		}
		return content.toString();
	}


}

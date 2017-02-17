package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Head;
import sg.edu.nus.comp.cs4218.exception.HeadException;

/**
 * 
 * Description: Print first N lines of the file(or input stream). If there are less than N lines,
 * print existing lines without rising an exception
 * 
 * head [OPTIONS] [FILE], shell parses args as args{[OPTION],[NUMBER],[FILE]}
 * OPTIONS - "-n 15" means printing 15 lines, print first 10 lines if not specified
 * FILE - the name of the file. If not specified, use stdin
 */
public class HeadApplication implements Head {

	String[] lines;

	private static final String FILE_NOT_FOUND = "Specify a file which exists";
	private static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	private static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
	private static final String INVALID_FORMAT = "Invalid command format";
	private static final String ERROR_IO_READING = "IO ERROR WHEN READING FILE";
	private static final String NUMBER_NOT_SPECIFIED = "Specify proper number with \"-n\" option";

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws HeadException {
		try {
			if( stdout == null ){
				throw new HeadException(ERROR_EXP_INVALID_OUTSTREAM);
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
				throw new HeadException(INVALID_FORMAT);
			}
			printLines(noLines);
		} catch (NumberFormatException e) {
			throw new HeadException(NUMBER_NOT_SPECIFIED);
		} catch (IOException e) {
			throw new HeadException(ERROR_IO_READING);
		}
	}





	private void logicFor1Argument(String[] args, InputStream stdin) throws HeadException, IOException {
		if(args[0].length()==0){
			loadFromStdIn(stdin); //stdin 10 lines
		}
		else{
			load(args[0]); //head [file], head [option]
		}
	}

	private int logicFor2Arguments(String[] args, InputStream stdin, int noLines) throws HeadException, IOException {
		if(args[0].length()==0){
			if(args[1].length()==0){
				loadFromStdIn(stdin); //head "",""
			}
			else{ //head "",[number]/[file]
				load(args[1]);
			}
		}
		else if(args[0].equals("-n")){
			if( isNumeric(args[1]) ){ //head -n, 10
				if(args[1].contains("-") || args[1].contains("+")){
					throw new HeadException(NUMBER_NOT_SPECIFIED);
				}
				noLines=Integer.parseInt(args[1]);
				loadFromStdIn(stdin);
			}
			else{
				throw new HeadException(NUMBER_NOT_SPECIFIED);
			}
		}
		else{
			throw new HeadException(INVALID_FORMAT); //head -nxx file
		}
		return noLines;
	}


	private int logicFor3Arguments(String[] args, InputStream stdin, int noLines) throws HeadException, IOException {
		if(args[0].length()==0 && args[1].length()==0){
			if(args[2].length()==0){ //head "" "" ""
				loadFromStdIn(stdin);
			}
			else{
				load(args[2]); //head "" "" file
			}
		}
		else if (args[0].equals("-n")){ //head -n x x
			if(isNumeric(args[1])){
				if(args[1].contains("-") || args[1].contains("+")){
					throw new HeadException(NUMBER_NOT_SPECIFIED);
				}
				if(args[2].length()==0){ //head -n 10 ""
					noLines=Integer.parseInt(args[1]);
					loadFromStdIn(stdin);
				}
				else{
					noLines=Integer.parseInt(args[1]); //head -n 10 file
					load(args[2]);
				}
			}
			else{
				throw new HeadException(NUMBER_NOT_SPECIFIED);
			}
		}
		else{
			throw new HeadException(INVALID_FORMAT); //head -xxx 10 file
		}
		return noLines;
	}

	private void printLines(int noLines) {
		for(int i = 0; i<lines.length; i++){
			if(noLines == i)
				break;
			System.out.println(lines[i]);
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

	private void loadFromStdIn(InputStream stdin) throws HeadException{
		if(stdin==null){
			throw new HeadException(ERROR_EXP_INVALID_INSTREAM);
		}
		else{
			String wholeText = convertStreamToString(stdin);
			lines = wholeText.split("[" + System.getProperty("line.separator") + "]");
		}
	}

	private void load(String fileName) throws IOException, HeadException {
		File workingFile = new File(fileName);
		if(workingFile.exists()){
			Vector<String> linesVector; 
			linesVector = importFile(fileName);
			lines = linesVector.toArray(new String[linesVector.size()]);
		}
		else{
			throw new HeadException(FILE_NOT_FOUND);
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

}

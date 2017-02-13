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
 * head [OPTIONS] [FILE]
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

			if(args!=null && args.length!=0){
				if(args.length==1){
					if(args[0].length()==0){ 
						//empty arg
						loadFromStdIn(stdin);
					}
					else if(args[0].length()>0 && args[0].length()<=3){ 
						//load from file with short file names within 3char i.e head /ab
						load(args[0]);
					}
					else if(args[0].length()>3){
						if(args[0].substring(0, 3).equals("-n ")){ 
							// read from stdin with options
							noLines = Integer.parseInt(args[0].substring(3));
							if(noLines<0 || noLines>Integer.MAX_VALUE) {
								//Negative numbers and integer overflow
								throw new HeadException(NUMBER_NOT_SPECIFIED); 
							}else{
								loadFromStdIn(stdin);
							}
						}
						else{
							//load from file with long file names
							load(args[0]);
						}
					}
					else{
						//this should not happen
						throw new HeadException(INVALID_FORMAT); 
					}
				}
				else if(args.length==2){
					if(args[0].length()==0){
						if(args[1].length()==0){
							loadFromStdIn(stdin);
						}
						else{
							load(args[0]);
						}
					}
					else if(args[0].length()!=0){
						if(args[0].length()>0 && args[0].length()<=3){
							throw new HeadException(NUMBER_NOT_SPECIFIED); //head -n_ file
						}
						else if(args[0].length()>3){
							if(args[0].substring(0, 3).equals("-n ")){
								if(args[1].length()==0){
									noLines = Integer.parseInt(args[0].substring(3));
									if(noLines<0 || noLines>Integer.MAX_VALUE) {
										//Negative numbers and integer overflow
										throw new HeadException(NUMBER_NOT_SPECIFIED); 
									}else{
										loadFromStdIn(stdin);
									}
								}
								else{
									noLines = Integer.parseInt(args[0].substring(3));
									if(noLines<0 || noLines>Integer.MAX_VALUE) {
										//Negative numbers and integer overflow
										throw new HeadException(NUMBER_NOT_SPECIFIED); 
									}else{
										load(args[1]);
									}
								}
							}
							else{
								throw new HeadException(INVALID_FORMAT);
							}
						}

					}
				}
				else{
					throw new HeadException(INVALID_FORMAT); //Invalid input
				}

			}else{
				if (stdin!=null)
					loadFromStdIn(stdin);  //print from stdin
				else throw new HeadException(ERROR_EXP_INVALID_INSTREAM);
			}
			printLines(noLines);
		} catch (NumberFormatException e) {
			throw new HeadException(NUMBER_NOT_SPECIFIED);
		} catch (IOException e) {
			throw new HeadException(ERROR_IO_READING);
		}
	}



	private void printLines(int noLines) {
		for(int i = 0; i<lines.length; i++){
			if(noLines == i)
				break;
			System.out.println(lines[i]);
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

	private Boolean isFileExists(String fileName) throws IOException{ 
		File workingFile = new File(fileName);
		return workingFile.exists();
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

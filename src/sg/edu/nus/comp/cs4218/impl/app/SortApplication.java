package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplication implements Sort {
	
	private static final String ERROR_EXP_NULL_ARGS = "Null arguments";
	private static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	private static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
	private static final String ERROR_EXP_NULL_STRING = "Comparing empty string";
	private static final String FILE_NOT_FOUND = "Specify a file which exists";
	private static final String IO = "I/O exception";
	private static final String ERROR_IO_READING = "IO ERROR WHEN READING FILE";
	private static final String ERROR_IO_WRITING = "IO ERROR WHEN WRITING FILE";
	private static final String ERROR_IO_WRITING_OUTSTREAM = "IO ERROR WHEN WRITING TO OUTSTREAM";
	
	public static final int SECOND_STRING_GREATER = 2;
	public static final int FIRST_STRING_GREATER = 1;
	public static final int BOTH_STRING_EQUAL = 0;
	
	public static final int SECOND_CHAR_GREATER = 2;
	public static final int FIRST_CHAR_GREATER = 1;
	public static final int BOTH_CHAR_EQUAL = 0;
	
	private String[] lines;
	private String[] tempArr;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if( (args == null || args.length == 0) && stdin == null){
			throw new SortException(ERROR_EXP_NULL_ARGS);
		}
		if( stdout == null ){
			throw new SortException(ERROR_EXP_INVALID_OUTSTREAM);
		}
		
		if( args.length == 1 && !args[0].equals("-n")){
			load(args[0]);
		}else if(args.length == 1 && args[0].equals("-n")){
			loadFromStdIn(stdin);
		}else if((args == null || args.length == 0) && stdin !=null){
			loadFromStdIn(stdin);
		}else if(args.length == 2){
			load(args[1]);
		}
		
		sort();
		
		if((args.length == 1 && args[0].equals("-n")) 
				|| (args.length == 2 && args[0].equals("-n"))){
			sortN();
		}
		
		if(args.length == 1 && !args[0].equals("-n")){
			writeToFile(arrayToString(), args[0]);
		}
		
		if(args.length == 2){
			writeToFile(arrayToString(), args[1]);
		}
		
		
		try {
			stdout.write(arrayToString().getBytes());
			stdout.flush();
		} catch (IOException e) {
			throw new SortException(ERROR_IO_WRITING_OUTSTREAM);
		}		
	}
	
	private static void writeToFile(String newString, String fileName) throws SortException{
		FileWriter fw;
		try {
			clearFromFile(fileName);
			fw = new FileWriter(fileName, true);
			fw.write(newString);
			fw.close();
		} catch (IOException e) {
			throw new SortException(ERROR_IO_WRITING);
		}
	}
	
	private static void clearFromFile(String fileName) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(fileName));
		writer.print("");
		writer.close();
	}
	
	private String arrayToString(){
		StringBuilder strBuilder = new StringBuilder();
		
		for (int i = 0; i < lines.length; i++) {
		   strBuilder.append(lines[i]);
		   if(i != lines.length-1)
			   strBuilder.append(System.getProperty("line.separator"));
		}
		return strBuilder.toString();
	}
	
	private void sort() throws AbstractApplicationException {
		tempArr = new String[lines.length];
		mergeSort(0, lines.length -1);
	}

	private void sortN() throws AbstractApplicationException{
		int start = getFirstNumberLine();
		int end = getLastNumberLine();
		mergeSortN(start, end);
	}
	
	private int getLastNumberLine() {
		int found = -1;
		
		for (int i = lines.length -1; i >= 0; i--){
			if(Character.isDigit(lines[i].charAt(0))){
				found = i;
				break;
			}
		}
		return found;
	}

	private int getFirstNumberLine() {
		int found = -1;
		
		for (int i = 0; i < lines.length; i++){
			if(Character.isDigit(lines[i].charAt(0))){
				found = i;
				break;
			}
		}
		return found;
	}

	private void mergeSortN(int lo, int hi) throws AbstractApplicationException {
		if (lo < hi) {
			int mid = lo + (hi - lo)/2;
			mergeSortN(lo, mid);
			mergeSortN(mid+1, hi);
			mergeN(lo, mid, hi);
		}
	}

	private void mergeN(int lo, int mid, int hi) throws AbstractApplicationException {
        for (int i = lo; i <= hi; i++) {
            tempArr[i] = lines[i];
        }
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            if (compareNumber(tempArr[i], tempArr[j]) == SECOND_STRING_GREATER ||
            		compareNumber(tempArr[i], tempArr[j]) == BOTH_STRING_EQUAL) {
                lines[k] = tempArr[i];
                i++;
            } else {
                lines[k] = tempArr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            lines[k] = tempArr[i];
            k++;
            i++;
        }
	}	
	
	private int compareNumber(String a, String b) throws SortException {
		if(isNullOrEmpty(a) || isNullOrEmpty(b)){
			throw new SortException(ERROR_EXP_NULL_STRING);
		}
		
		if (isNumber(a) && isNumber(b)){
			if(Double.parseDouble(a.split(" ")[0]) > Double.parseDouble(b.split(" ")[0])){
				return FIRST_STRING_GREATER;
			}else if (Double.parseDouble(a.split(" ")[0]) < Double.parseDouble(b.split(" ")[0])){
				return SECOND_STRING_GREATER;
			}else{
				return BOTH_STRING_EQUAL;
			}
		}else if (isNumber(a)){
			return SECOND_STRING_GREATER;
		}else if (isNumber(b)){
			return FIRST_STRING_GREATER;
		}
		
		return BOTH_STRING_EQUAL;
	}

	private void mergeSort(int lo, int hi) throws AbstractApplicationException {
		if (lo < hi) {
			int mid = lo + (hi - lo)/2;
			mergeSort(lo, mid);
			mergeSort(mid+1, hi);
			merge(lo, mid, hi);
		}
		
	}

	private void merge(int lo, int mid, int hi) throws AbstractApplicationException {
        for (int i = lo; i <= hi; i++) {
            tempArr[i] = lines[i];
        }
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            if (compareStrings(tempArr[i], tempArr[j]) == SECOND_STRING_GREATER ||
            		compareStrings(tempArr[i], tempArr[j]) == BOTH_STRING_EQUAL) {
                lines[k] = tempArr[i];
                i++;
            } else {
                lines[k] = tempArr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            lines[k] = tempArr[i];
            k++;
            i++;
        }
	}

	private void loadFromStdIn(InputStream stdin){
		String wholeText = convertStreamToString(stdin);
		lines = wholeText.split("[" + System.getProperty("line.separator") + "]");
	}
	
	private void load(String fileName) throws SortException {
		File workingFile = new File(fileName);
		Vector<String> linesVector;
		
		if (workingFile.exists()) {
			try {
				linesVector = importFile(fileName);
			} catch (IOException e) {
				throw new SortException(ERROR_IO_READING);
			}
			lines = linesVector.toArray(new String[linesVector.size()]);
			
		} else {
			throw new SortException(FILE_NOT_FOUND);
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
	
	private boolean isSpecialChar(char ch){
		return !Character.isLetterOrDigit(ch);
	}
	
	private boolean isCapital(){
		return false;
	}
	
	private boolean isSmall(){
		return false;
	}
	
	private boolean isNumber(String a){
        try {
            Double.parseDouble(a.split(" ")[0]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}
	
	private int compareStrings(String a, String b) throws AbstractApplicationException{
		if(a == null || b == null){
			throw new SortException(ERROR_EXP_NULL_STRING);
		}
		
		if(a.isEmpty() || b.isEmpty()){
			if(!b.isEmpty()){
				return SECOND_STRING_GREATER;
			}else if(!a.isEmpty()){
				return FIRST_STRING_GREATER;
			}else{
				return BOTH_STRING_EQUAL;
			}
				
		}
		
		int result = BOTH_STRING_EQUAL;
		int index = 0;
		
		while(result == BOTH_STRING_EQUAL && index<a.length() && index<b.length()){
			result = compareChars(a.charAt(index), b.charAt(index));
			index ++;			
		}
		
		if(result == BOTH_STRING_EQUAL){
			if (index==a.length()-1 && index<b.length()-1){
				result = SECOND_STRING_GREATER;
			}else if(index<a.length()-1 && index==b.length()-1){
				result = FIRST_STRING_GREATER;
			}
		}
		
		return result;
	}
	
	private int compareChars(char a, char b){
		int result = 0;
		double res = 1;
		if (isSpecialChar(a) || isSpecialChar(b)){
			if(!isSpecialChar(b)){
				return SECOND_STRING_GREATER;
			}else if(!isSpecialChar(a)){
				return FIRST_STRING_GREATER;
			}else if (a == b){
				return BOTH_STRING_EQUAL;
			}else{
				return (int)a > (int)b ? FIRST_STRING_GREATER : SECOND_STRING_GREATER;
			}
		}
		
		if((int) a > (int)b){
			return FIRST_STRING_GREATER;
		}
		
		if((int) b > (int) a){
			return SECOND_STRING_GREATER;
		}
		
		return BOTH_STRING_EQUAL;
	}
	
	private boolean isNullOrEmpty(String str){
		if(str==null || str.isEmpty()){
		    return true;
		}
		
		return false;
	}

	@Override
	public String sortStringsSimple(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortStringsCapital(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

	@Override
	public String sortAll(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			this.run(arguments, null, new ByteArrayOutputStream());
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
		}
		return arrayToString();
	}

}

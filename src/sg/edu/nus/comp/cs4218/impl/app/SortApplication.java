package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplication implements Sort {
	
	private static final String ERROR_EXP_NULL = "Null arguments";
	private static final String ERR_INVALID_OUT = "OutputStream not provided";
	private static final String ERR_NULL_STRING = "Comparing empty string";
	private static final String FILE_NOT_FOUND = "Specify a file which exists";
	private static final String ERR_READ = "IO ERROR WHEN READING FILE";
	private static final String ERR_WRITE_OUT = "IO ERROR WHEN WRITING TO OUTSTREAM";
	private static final String ERR_NOTALL_NUM = "contents of file must start with numbers";
	
	public static final int STR_2_GREATER = 2;
	public static final int STR_1_GREATER = 1;
	public static final int BOTH_STR_EQ = 0;
	
	public static final int CHAR_2_GREATER = 2;
	public static final int CHAR_1_GREATER = 1;
	public static final int BOTH_CHAR_EQUAL = 0;
	
	private String[] lines;
	private String[] tempArr;
	
	private boolean loaded = false;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if( (args == null || args.length == 0) && stdin == null){
			throw new SortException(ERROR_EXP_NULL);
		}
		if( stdout == null ){
			throw new SortException(ERR_INVALID_OUT);
		}
		
		load(args, stdin);
		
		loaded = true;
		
		if((args.length == 1 && args[0].equals("-n")) 
				|| (args.length == 2 && args[0].equals("-n"))){
			sort();
			sortN();
		}else{
			filterAndSort(args);
		}
		
		try {
			stdout.write(arrayToString().getBytes());
			stdout.flush();
		} catch (IOException e) {
			throw new SortException(ERR_WRITE_OUT);
		}		
	}

	private void load(String[] args, InputStream stdin) throws SortException {
		if( args.length == 1 && !args[0].equals("-n")){
			load(args[0]);
		}else if(args.length == 1 && args[0].equals("-n")){
			loadFromStdIn(stdin);
		}else if((args == null || args.length == 0) && stdin !=null){
			loadFromStdIn(stdin);
		}else if(args.length == 2){
			load(args[1]);
		}
	}
	
	private String filterAndSort(String[] args) {
		boolean hasCapital = false;
		boolean hasSimple = false;
		boolean hasSpecial = false;
		boolean hasNumber = false;
		
		for(int i = 0; i< lines.length; i++){
			if(!hasCapital) {
				hasCapital = hasCapital(lines[i]);
			}
			if(!hasSimple) {
				hasSimple = hasSimple(lines[i]);
			}
			if(!hasSpecial) {
				hasSpecial = hasSpecial(lines[i]);
			}
			if(!hasNumber) {
				hasNumber = hasNumber(lines[i]);
			}
			
			if (hasCapital && hasSimple && hasSpecial && hasNumber) {
				break;
			}
		}
		
		return sort(args, hasSimple, hasCapital, hasNumber, hasSpecial);
	}

	private String sort(String[] args, boolean hasSimple, boolean hasCapital, boolean hasNumber, boolean hasSpecial) {
		String cmd = constructCommand(args);
		if(hasSimple && hasCapital && hasNumber && hasSpecial){
			sortAll(cmd);
		}else if(hasSimple && !hasCapital && !hasNumber && !hasSpecial){
			sortStringsSimple(cmd);
		}else if(!hasSimple && hasCapital && !hasNumber && !hasSpecial){
			sortStringsCapital(cmd);
		}else if(!hasSimple && !hasCapital && hasNumber && !hasSpecial){
			sortNumbers(cmd);
		}else if(!hasSimple && !hasCapital && !hasNumber && hasSpecial){
			sortSpecialChars(cmd);
		}else if(hasSimple && hasCapital && !hasNumber && !hasSpecial){
			sortSimpleCapital(cmd);
		}else if(hasSimple && !hasCapital && hasNumber && !hasSpecial){
			sortSimpleNumbers(cmd);
		}else if(hasSimple && !hasCapital && !hasNumber && hasSpecial){
			sortSimpleSpecialChars(cmd);
		}else if(!hasSimple && hasCapital && hasNumber && !hasSpecial){
			sortCapitalNumbers(cmd);
		}else if(!hasSimple && hasCapital && !hasNumber && hasSpecial){
			sortCapitalSpecialChars(cmd);
		}else if(!hasSimple && !hasCapital && hasNumber && hasSpecial){
			sortNumbersSpecialChars(cmd);
		}else if(hasSimple && hasCapital && hasNumber && !hasSpecial){
			sortSimpleCapitalNumber(cmd);
		}else if(hasSimple && hasCapital && !hasNumber && hasSpecial){
			sortSimpleCapitalSpecialChars(cmd);
		}else if(hasSimple && !hasCapital && hasNumber && hasSpecial){
			sortSimpleNumbersSpecialChars(cmd);
		}else if(!hasSimple && hasCapital && hasNumber && hasSpecial){
			sortCapitalNumbersSpecialChars(cmd);
		}
		
		return null;
	}

	private String constructCommand(String[] args) {
		String cmd = "sort";
		for (int i = 0; i < args.length; i++){
			cmd += " ";
			cmd += args[i];
		}
		return cmd;
	}

	private boolean hasNumber(String string) {
		for(int i = 0; i < string.length(); i++){
			if(Character.isDigit(string.charAt(i))){
				return true;
			}
		}
		return false;
	}

	private boolean hasSpecial(String string) {
		for(int i = 0; i < string.length(); i++){
			if(isSpecialChar(string.charAt(i))){
				return true;
			}
		}
		return false;
	}

	private boolean hasSimple(String string) {
		for(int i = 0; i < string.length(); i++){
			if(Character.isLowerCase(string.charAt(i))){
				return true;
			}
		}
		return false;
	}

	private boolean hasCapital(String string) {
		for(int i = 0; i < string.length(); i++){
			if(Character.isUpperCase(string.charAt(i))){
				return true;
			}
		}
		return false;
	}

//	private static void writeToFile(String newString, String fileName) throws SortException{
//		FileWriter fw;
//		try {
//			clearFromFile(fileName);
//			fw = new FileWriter(fileName, true);
//			fw.write(newString);
//			fw.close();
//		} catch (IOException e) {
//			throw new SortException(ERROR_IO_WRITING);
//		}
//	}
//	
//	private static void clearFromFile(String fileName) throws FileNotFoundException {
//		PrintWriter writer = new PrintWriter(new File(fileName));
//		writer.print("");
//		writer.close();
//	}
	
	private String arrayToString(){
		StringBuilder strBuilder = new StringBuilder();
		
		for (int i = 0; i < lines.length; i++) {
		   strBuilder.append(lines[i]);
		   if(i != lines.length-1){
			   strBuilder.append(System.getProperty("line.separator"));
		   }
		}
		return strBuilder.toString();
	}
	
	private void sort() throws AbstractApplicationException {
		tempArr = new String[lines.length];
		mergeSort(0, lines.length -1);
	}

	private void sortN() throws AbstractApplicationException{
		if(!isAllNumbers()){
			throw new SortException(ERR_NOTALL_NUM);
		}
		tempArr = new String[lines.length];		
		mergeSortN(0, lines.length -1);
	}

	private boolean isAllNumbers() {
		
		for (int i = 0; i < lines.length; i++){
			if(!lines[i].isEmpty() && !isNumber(lines[i].split(" ")[0])){
				return false;
			}
		}
		return true;
	}

	private void mergeSortN(int loIndex, int hiIndex) throws AbstractApplicationException {
		if (loIndex < hiIndex) {
			int mid = loIndex + (hiIndex - loIndex)/2;
			mergeSortN(loIndex, mid);
			mergeSortN(mid+1, hiIndex);
			mergeN(loIndex, mid, hiIndex);
		}
	}

	private void mergeN(int loIndex, int midIndex, int hiIndex) throws AbstractApplicationException {
        for (int i = loIndex; i <= hiIndex; i++) {
            tempArr[i] = lines[i];
        }
        int currLo = loIndex;
        int currMid = midIndex + 1;
        int newLo = loIndex;
        while (currLo <= midIndex && currMid <= hiIndex) {
            if (compareNumber(tempArr[currLo], tempArr[currMid]) == STR_2_GREATER ||
            		compareNumber(tempArr[currLo], tempArr[currMid]) == BOTH_STR_EQ) {
                lines[newLo] = tempArr[currLo];
                currLo++;
            } else {
                lines[newLo] = tempArr[currMid];
                currMid++;
            }
            newLo++;
        }
        while (currLo <= midIndex) {
            lines[newLo] = tempArr[currLo];
            newLo++;
            currLo++;
        }
	}	
	
	private int compareNumber(String str1, String str2) throws SortException {
		if(str1==null || str2==null){
			throw new SortException(ERR_NULL_STRING);
		}
		
		if(str1.isEmpty()){
			if(!str2.isEmpty()){
				return STR_2_GREATER;
			}
			if(str2.isEmpty()){
				return BOTH_STR_EQ;
			}
				
		}
		
		if(Double.parseDouble(str1.split(" ")[0]) > Double.parseDouble(str2.split(" ")[0])){
			return STR_1_GREATER;
		}else if (Double.parseDouble(str1.split(" ")[0]) < Double.parseDouble(str2.split(" ")[0])){
			return STR_2_GREATER;
		}else{
			return BOTH_STR_EQ;
		}
	}

	private void mergeSort(int loIndex, int hiIndex) throws AbstractApplicationException {
		if (loIndex < hiIndex) {
			int mid = loIndex + (hiIndex - loIndex)/2;
			mergeSort(loIndex, mid);
			mergeSort(mid+1, hiIndex);
			merge(loIndex, mid, hiIndex);
		}
		
	}

	private void merge(int loIndex, int midIndex, int hiIndex) throws AbstractApplicationException {
        for (int copyIndex = loIndex; copyIndex <= hiIndex; copyIndex++) {
            tempArr[copyIndex] = lines[copyIndex];
        }
        int currLo = loIndex;
        int currMid = midIndex + 1;
        int newLo = loIndex;
        while (currLo <= midIndex && currMid <= hiIndex) {
            if (compareStrings(tempArr[currLo], tempArr[currMid]) == STR_2_GREATER ||
            		compareStrings(tempArr[currLo], tempArr[currMid]) == BOTH_STR_EQ) {
                lines[newLo] = tempArr[currLo];
                currLo++;
            } else {
                lines[newLo] = tempArr[currMid];
                currMid++;
            }
            newLo++;
        }
        while (currLo <= midIndex) {
            lines[newLo] = tempArr[currLo];
            newLo++;
            currLo++;
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
				throw new SortException(ERR_READ);
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
		try(Scanner scanner = new Scanner(stdin).useDelimiter("\\A");)
		{
			return scanner.hasNext() ? scanner.next() : "";
		}
	}
	
	private boolean isSpecialChar(char toCheck){
		return !Character.isLetterOrDigit(toCheck);
	}
	
	private boolean isNumber(String toCheck){
        try {
            Double.parseDouble(toCheck.split(" ")[0]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}
	
	private int compareStrings(String str1, String str2) throws AbstractApplicationException{
		if(str1 == null || str2 == null){
			throw new SortException(ERR_NULL_STRING);
		}
		
		if(str1.isEmpty() || str2.isEmpty()){
			if(!str2.isEmpty()){
				return STR_2_GREATER;
			}
			if(!str1.isEmpty()){
				return STR_1_GREATER;
			}
			return BOTH_STR_EQ;
		}
		
		int result = BOTH_STR_EQ;
		int index = 0;
		
		while(result == BOTH_STR_EQ && index<str1.length() && index<str2.length()){
			result = compareChars(str1.charAt(index), str2.charAt(index));
			index ++;			
		}
		
		if(result == BOTH_STR_EQ){
			if (index==str1.length() && index<str2.length()){
				result = STR_2_GREATER;
			}else if(index<str1.length() && index==str2.length()){
				result = STR_1_GREATER;
			}
		}
		
		return result;
	}
	
	private int compareChars(char char1, char char2){
		if (isSpecialChar(char1) || isSpecialChar(char2)){
			if(!isSpecialChar(char2)){
				return STR_2_GREATER;
			}
			if(!isSpecialChar(char1)){
				return STR_1_GREATER;
			}
			if (char1 == char2){
				return BOTH_STR_EQ;
			}
			
			return (int)char1 > (int)char2 ? STR_1_GREATER : STR_2_GREATER;

		}
		
		if((int) char1 > (int)char2){
			return STR_1_GREATER;
		}
		
		if((int) char2 > (int) char1){
			return STR_2_GREATER;
		}
		
		return BOTH_STR_EQ;
	}

	@Override
	public String sortStringsSimple(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
			
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortStringsCapital(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	@Override
	public String sortAll(String toSort) {
		String[] split = toSort.split(" ");
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		try {
			sortFromInterface(arguments);
		} catch (AbstractApplicationException e) {
			return e.getClass().getName();
		}
		return arrayToString();
	}

	private void sortFromInterface(String[] arguments) throws SortException, AbstractApplicationException {
		if(!loaded){
			load(arguments, null);
			if((arguments.length == 1 && arguments[0].equals("-n")) 
					|| (arguments.length == 2 && arguments[0].equals("-n"))){
				sort();
				sortN();
			}else{
				sort();
			}
		}
		if (loaded){
			sort();
		}
	}

}

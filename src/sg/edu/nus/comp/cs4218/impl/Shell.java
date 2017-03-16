package sg.edu.nus.comp.cs4218.impl;

public class Shell {

	public static void main(String... args) {

		try {

			ShellImpl shell = new ShellImpl();
			String readLine = " echo 'hi'";
			shell.parseAndEvaluate(readLine, System.out);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

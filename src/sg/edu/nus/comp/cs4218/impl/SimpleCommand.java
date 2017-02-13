package sg.edu.nus.comp.cs4218.impl;

public class SimpleCommand {

    private String appName;
    private String stdinName;
    private String stdoutName;
    private String[] arguments;
    
    public SimpleCommand() {
        this( "", new String[0], "", "" );
    }
    
    public SimpleCommand( String appName, String[] arguments, String stdinName, String stdoutName ) {
        this.appName = appName;
        this.stdinName = stdinName;
        this.stdoutName = stdoutName;
        this.arguments = arguments;
    }
    
    public String getStdinName() {
        return stdinName;
    }
    
    public boolean hasStdin() {
        return (stdinName != null && !stdinName.isEmpty());
    }
    
    public String getStdoutName() {
        return stdoutName;
    }
    
    public boolean hasStdout() {
        return (stdoutName != null && !stdoutName.isEmpty());
    }
    
    public String[] getArguments() {
        return arguments;
    }
    
    public String getAppName() {
        return appName;
    }
    
    public boolean hasAppName() {
        return (appName != null && !appName.isEmpty());
    }
}

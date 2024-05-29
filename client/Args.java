package client;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "-t")
    private String requestType;
    @Parameter(names = "-k")
    private String key;
    @Parameter(names = "-v")
    private String value;
    @Parameter(names = "-in")
    private String inputFromFile;

    public String getRequestType() {
        return requestType;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getInputFromFile() {
        return inputFromFile;
    }
}

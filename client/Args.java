package client;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "-t")
    private String requestType;
    @Parameter(names = "-i")
    private int cellIndex;
    @Parameter(names = "-m")
    private String value;

    public String getRequestType() {
        return requestType;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public String getValue() {
        return value;
    }


}

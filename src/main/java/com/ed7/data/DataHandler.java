package com.ed7.data;

import com.ed7.source.SourceHandler;


public class DataHandler {

    private static SourceHandler source;

    public static SourceHandler getSource() {
        return source;
    }

    public static void setSource(SourceHandler source) {
        DataHandler.source = source;
    }
}

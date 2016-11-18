//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common;


public enum FileType {
    REPORT("/report");
//    USER("/user");

    private String filePath;

    private FileType(String filePath) {
        this.filePath = filePath;
    }


    public String getFilePath() {
        return filePath;
    }
}

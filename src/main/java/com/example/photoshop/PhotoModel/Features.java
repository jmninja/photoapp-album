package com.example.photoshop.PhotoModel;

import javafx.stage.FileChooser;

import java.util.HashMap;

public interface Features {
    void fileConversion( String x, String y);
    HashMap<Integer, Object> getInfo();
    FileChooser openImage();
}

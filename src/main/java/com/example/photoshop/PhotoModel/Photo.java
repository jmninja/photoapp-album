package com.example.photoshop.PhotoModel;

import javafx.scene.image.Image;
import com.example.photoshop.Controller;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Photo implements Features {

    public String file_path;
    public Image image;
    public static File image_loaded_file;
    public String file_extension;
    public ArrayList<String> format_list = new ArrayList<>(Arrays.asList(".GIF", ".JPG", ".JPEG", ".PNG", ".TIF", ".TIFF"));
    public static javaxt.io.Image javaxt_image = null;

    @Override
    public void fileConversion(String file_path, String new_format) {
        System.out.println("in changeformat variables");
        String new_file_format = file_path.substring(0, file_path.lastIndexOf(".")) + " " + new_format;
        new javaxt.io.Image(file_path).saveAs(new_file_format + "." + new_format);
    }

    @Override
    public HashMap<Integer, Object> getInfo() {

        HashMap<Integer, Object> exif = null;
        if (image_loaded_file != null) {
            javaxt.io.Image javaxt_image = new javaxt.io.Image(file_path);
            exif = javaxt_image.getExifTags();
        }
        return exif;
    }

    @Override
    public FileChooser openImage() {
        FileChooser file_chooser = new FileChooser();
        file_chooser.setTitle("Choose an Image");
        return file_chooser;
    }
}

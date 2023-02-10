package com.example.photoshop;

import com.example.photoshop.PhotoModel.Photo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Photo implements Initializable {
    @FXML
    public AnchorPane anchorBasePane;

    @FXML
    public Label error_label;

    @FXML
    public RadioButton image_GPS;

    @FXML
    public ComboBox<String> choose_Format;

    @FXML
    public Label current_format;

    @FXML
    public RadioButton image_camera;

    @FXML
    public RadioButton image_date;

    @FXML
    public Label image_details;

    @FXML
    public ToggleGroup image_dtails;

    @FXML
    public ImageView loadedImage_view;

    @FXML
    public RadioButton image_thumbnails;

    @FXML
    void radio_details(ActionEvent event) {
        //Getting camera details

        System.out.println(" the details are "+ image_thumbnails.isSelected());

        if (image_thumbnails.isSelected()) {
            if(image.getHeight()!=0  && image.getWidth()!=0) {
                image_details.setText(String.valueOf(image.getHeight()) + " X " + String.valueOf(image.getWidth()));
            }else{
                image_details.setText("value is not available");
            }
        } else if (image_date.isSelected()) {
            if (getInfo().get(0x0132)!= null) {
                image_details.setText((String) getInfo().get(0x0132));
            }else{
                image_details.setText("value is not available");
            }
        } else if (image_camera.isSelected()) {
            if (getInfo().get(0x0110)!= null) {
                image_details.setText((String) getInfo().get(0x0110));
            }else{
                image_details.setText("value is not available");
            }
        } else {
            double[] coord = javaxt_image.getGPSCoordinate();
            if (coord != null) {
                String location =  coord[0] + ", " + coord[1];
                image_details.setText(location);
            } else{
                image_details.setText(" please select the image");
            }
        }
    }

    @FXML
    void loadImage(ActionEvent event) throws FileNotFoundException {

        image_details.setText("");
        error_label.setText(" ");

        Stage stage_window = (Stage)anchorBasePane.getScene().getWindow();

        //Saving the file now
        image_loaded_file =  openImage().showSaveDialog(stage_window);

        if(image_loaded_file != null){
            //Getting absolute path of the image
            System.out.println("file path is :::: " + image_loaded_file.getAbsolutePath());
            file_path = image_loaded_file.getAbsolutePath();

            //Getting extension of the file
            int index = file_path.lastIndexOf(".");

            if(index != -1){
                System.out.println("check if contains " + format_list.contains(file_extension) + file_extension);
                file_extension = file_path.substring(index).toUpperCase();
                current_format.setText(file_extension);

                //Checking if given input is image or not
                if(format_list.contains(file_extension)) {
                    //Adding the input image to image view
                    image = new Image(file_path);
                    loadedImage_view.setImage(image);

                    //Making thumbnails active on image load => to get its details
                    image_thumbnails.setSelected(true);
                }
                else{
                    error_label.setText("invalid file type. Please select only Image formats" + "\n" + "need to be either formats" + format_list);
                    loadedImage_view.setImage(null);
                }
            }
        }
        else{
            error_label.setText("Please choose the file");
            loadedImage_view.setImage(null);
        }
    }

    @FXML
    void change_format(ActionEvent event) throws IOException {
        if (image_loaded_file != null) {
            System.out.println("in change format func");
            System.out.println("In " + choose_Format.getValue());
            fileConversion(file_path, choose_Format.getValue());

        } else {
            error_label.setText("Please choose the file");
            loadedImage_view.setImage(null);
        }

    }

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            ObservableList<String> output_format_list = FXCollections.observableArrayList(format_list);
            choose_Format.setItems(output_format_list);

        }

}

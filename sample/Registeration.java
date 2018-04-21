package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Registeration {

    static ArrayList<String> years,months,days;

    public static void display(String title){
        set_date_lists();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(5);

        Text lbl_title = new Text("Create an account");
        lbl_title.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
        TextField txt_fname = new TextField();
        txt_fname.setPromptText("First name");
        TextField txt_lname = new TextField();
        txt_lname.setPromptText("Last name");

        RadioButton rbtn_female = new RadioButton("Female");
        RadioButton rbtn_male = new RadioButton("Male");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(rbtn_female,rbtn_male);
        HBox hb_gender = new HBox(10);
        hb_gender.getChildren().addAll(rbtn_female,rbtn_male);

        HBox hb_date = new HBox(5);
        ComboBox<Integer> cbox_day = new ComboBox<>();
        ComboBox<Integer> cbox_month = new ComboBox<>();
        ComboBox<Integer> cbox_year = new ComboBox<>();
        hb_date.getChildren().addAll(cbox_day,cbox_month,cbox_year);

        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(txt_fname,0,1);
        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(lbl_title,0,0,2,1);


    }

    private void set_date_lists(){
        int this_year = LocalDate.now().getYear();
        years = new ArrayList<>(this_year - 1900);
        for(int i = this_year ; i > 1900 ; i++){
            years.add(Integer.toString(this_year));
        }
        months = new ArrayList<>(12);
        Collections.addAll(months,"January","February","March","April","May","June"
                        ,"July","August","September","Octorber","November","December");
    }
}
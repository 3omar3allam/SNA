package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static sample.User.*;
import static sample.usefulFunctions.*;
import static sample.usefulFunctions.*;

public class Registration {

    private static ObservableList<Integer> years,days;
    private static ObservableList<String> months;
    private static User user = null;

    public static User display(String title){

        int this_year = LocalDate.now().getYear();
        years = FXCollections.observableArrayList();
        for(int i = this_year ; i > 1900 ; i--){
            years.add(i);
        }
        months = FXCollections.observableArrayList();
        Collections.addAll(months,"January","February","March","April","May","June"
                ,"July","August","September","October","November","December");
        days = FXCollections.observableArrayList();
        for(int i = 1; i<= 31; i++){
            days.add(i);
        }
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        Stage window = new Stage();

        VBox container = new VBox(10);
        container.getStyleClass().add("registration_form");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(5);

        Text lbl_title = new Text("Create an account");
        lbl_title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-alignment: center-left; -fx-fill: aliceblue");
        TextField txt_username = new TextField();
        txt_username.setPromptText("Username");
        Label lbl_error = new Label();
        lbl_error.getStyleClass().add("registration_error_label");
        lbl_error.setAlignment(Pos.CENTER);

        TextField txt_fname = new TextField();
        txt_fname.setPromptText("First name");
        TextField txt_lname = new TextField();
        txt_lname.setPromptText("Last name");

        RadioButton rbtn_female = new RadioButton("Female");
        RadioButton rbtn_male = new RadioButton("Male");
        rbtn_female.setStyle("-fx-text-fill: aliceblue; -fx-font-weight: bold");
        rbtn_female.setUserData("female");
        rbtn_male.setStyle("-fx-text-fill: aliceblue; -fx-font-weight: bold");
        rbtn_male.setUserData("male");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.setUserData("");
        toggleGroup.getToggles().addAll(rbtn_female,rbtn_male);
        toggleGroup.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            toggleGroup.setUserData(newValue.getUserData().toString());
        }));
        HBox hb_gender = new HBox(10);
        hb_gender.getChildren().addAll(rbtn_female,rbtn_male);
        hb_gender.setAlignment(Pos.CENTER);

        HBox hb_date = new HBox(5);

        Label lbl_date = new Label("Birth Date");
        lbl_date.setStyle("-fx-text-fill: aliceblue;-fx-font-weight: bold; -fx-alignment: bottom-left");
        ComboBox<Integer> cbox_year = new ComboBox<>(years);
        cbox_year.setPromptText("Year");
        ComboBox<String> cbox_month = new ComboBox<>(months);
        cbox_month.setPromptText("Month");
        ComboBox<Integer> cbox_day = new ComboBox<>(days);
        cbox_day.setPromptText("Day");
        hb_date.getChildren().addAll(lbl_date,cbox_year,cbox_month,cbox_day);

        Button btn_confirm = new Button("Register");
        Button btn_cancel = new Button("Cancel");
        btn_cancel.setAlignment(Pos.CENTER_RIGHT);
        btn_cancel.setOnAction(e-> {
            user = null;
            window.close();
        });
        window.setOnCloseRequest(e->{
            user = null;
        });
        GridPane.setConstraints(lbl_title,0,0,2,1);
        GridPane.setConstraints(txt_username,0,1);
        GridPane.setConstraints(txt_fname,0,2);
        GridPane.setConstraints(txt_lname,1,2);
        GridPane.setConstraints(hb_gender,0,3,2,1);
        GridPane.setConstraints(hb_date,0,4,2,1);
        GridPane.setConstraints(lbl_error,0,5,2,1);

        grid.getChildren().addAll(lbl_title,txt_username,lbl_error,txt_fname,txt_lname,hb_gender,hb_date);

        txt_username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                grid.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        AnchorPane buttons = new AnchorPane();
        AnchorPane.setLeftAnchor(btn_confirm,30.0);
        AnchorPane.setBottomAnchor(btn_confirm,20.0);
        AnchorPane.setRightAnchor(btn_cancel,30.0);
        AnchorPane.setBottomAnchor(btn_cancel,20.0);
        buttons.getChildren().addAll(btn_confirm,btn_cancel);
        container.getChildren().addAll(grid,buttons);
        Scene scene = new Scene(container,400,340);
        scene.getStylesheets().add("style/style.css");

        recover_handled_errors(txt_username,lbl_error,txt_fname,txt_lname,toggleGroup,rbtn_male,rbtn_female,cbox_day,cbox_month,cbox_year);

        btn_confirm.setOnAction(e -> {
            boolean done = true;
            String username = txt_username.getText();
            if(username.equals("")){
                txt_username.getStyleClass().add("error_registration_text");
                done = false;
            }
            String fname = txt_fname.getText();
            if (fname.equals("")){
                txt_fname.getStyleClass().add("error_registration_text");
                done = false;
            }
            String lname = txt_lname.getText();
            if (lname.equals("")){
                txt_lname.getStyleClass().add("error_registration_text");
                done = false;
            }
            int year=0,month=0,day=0;
            try{
                year = cbox_year.getValue();

            }catch(Exception ex){
                cbox_year.getStyleClass().add("error_registration_cbox");
                done = false;
            }
            String month_name = cbox_month.getValue();
            if(month_name == null) {
                cbox_month.getStyleClass().add("error_registration_cbox");
                done = false;
            }else{
                month = months.indexOf(month_name)+1;
            }
            try{
                day = cbox_day.getValue();
            }catch(Exception ex){
                cbox_day.getStyleClass().add("error_registration_cbox");
                done = false;
            }
            if(cbox_day.getStyleClass().indexOf("error_registration_cbox") == -1 && wrong_date(day,month,year)){
                cbox_day.getStyleClass().add("error_registration_cbox");
                done = false;
            }
            String gender = toggleGroup.getUserData().toString();
            if(gender.equals("")){
                rbtn_male.getStyleClass().add("error_registration_radio");
                rbtn_female.getStyleClass().add("error_registration_radio");
                done = false;
            }
            if(done){
                try{
                    user = new User(username,fname,lname,gender,LocalDate.of(year,month,day));
                    window.close();
                }catch (UsernameException e1){
                    lbl_error.setVisible(true);
                    lbl_error.setText(e1.getMessage());
                    txt_username.getStyleClass().add("error_registration_text");
                } catch (AgeException e2) {
                    cbox_year.getStyleClass().add("error_registration_cbox");
                    lbl_error.setVisible(true);
                    lbl_error.setText(e2.getMessage());
                }catch (NameException e3){
                    String cause = e3.getCause().getMessage();
                    lbl_error.setVisible(true);
                    lbl_error.setText(e3.getMessage()+cause);
                    if(cause.equals("first name")) txt_fname.getStyleClass().add("error_registration_text");
                    else if(cause.equals("last name")) txt_lname.getStyleClass().add("error_registration_text");
                } catch (Exception ignored) { } //just because it is causing errors
            }
        });

        window.setScene(scene);
        window.showAndWait();
        return user;
    }
    private static void recover_handled_errors(TextField txt_username,Label lbl_error , TextField txt_fname,TextField txt_lname,
                                               ToggleGroup toggleGroup, RadioButton rbtn_male, RadioButton rbtn_female,
                                               ComboBox<Integer> cbox_year, ComboBox<String> cbox_month, ComboBox<Integer> cbox_day){

        txt_username.textProperty().addListener((ov,oldValue,newValue)->{
            int index = txt_username.getStyleClass().indexOf("error_registration_text");
            if(index != -1) txt_username.getStyleClass().remove(index);
            lbl_error.setText("");
            lbl_error.setVisible(false);
        });
        txt_fname.textProperty().addListener((ov,oldValue,newValue)->{
            int index = txt_fname.getStyleClass().indexOf("error_registration_text");
            if(index != -1) txt_fname.getStyleClass().remove(index);
        });
        txt_lname.textProperty().addListener((ov,oldValue,newValue)->{
            int index = txt_lname.getStyleClass().indexOf("error_registration_text");
            if(index != -1) txt_lname.getStyleClass().remove(index);
        });
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->{
            int index = rbtn_male.getStyleClass().indexOf("error_registration_radio");
            if(index != -1) {
                rbtn_male.getStyleClass().remove(index);
                rbtn_female.getStyleClass().remove(index);
            }
        });
        cbox_day.valueProperty().addListener( (ov,oldValue,newValue)->{
            int index = cbox_day.getStyleClass().indexOf("error_registration_cbox");
            if(index != -1){
                cbox_day.getStyleClass().remove(index);
            }
        });
        cbox_month.valueProperty().addListener( (ov,oldValue,newValue)->{
            int index = cbox_month.getStyleClass().indexOf("error_registration_cbox");
            if(index != -1){
                cbox_month.getStyleClass().remove(index);
            }
        });
        cbox_year.valueProperty().addListener( (ov,oldValue,newValue)->{
            int index = cbox_year.getStyleClass().indexOf("error_registration_cbox");
            if(index != -1){
                cbox_year.getStyleClass().remove(index);
            }
        });
    }
    private static boolean wrong_date(int day,int month,int year){
        if(month == 2 && day > 29) return true;
        else if(month == 2){
            boolean leap = (year % 4 == 0) && (year % 400 == 0 || year % 100 != 0);
            return !leap && day > 28;
        }
        else if((month < 8 && month % 2 == 0) || (month > 7 && month % 2 == 1)) return day > 30;
        return false;
    }
}
package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static sample.usefulFunctions.*;

public class CreateGroup {
    private static boolean success = false;
    public static boolean display(String title,User admin) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        Stage window = new Stage();

        GridPane container = new GridPane();
        container.setVgap(10);
        container.setHgap(50);
        container.getStyleClass().add("group_registration_form");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label lbl_title = new Label("Create new group");
        lbl_title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;-fx-text-fill: aliceblue;-fx-alignment: center-left");
        TextField txt_groupname = new TextField();
        txt_groupname.setPromptText("group name");

        Label lbl_error = new Label("");
        lbl_error.getStyleClass().add("group_registration_error_label");
        lbl_error.setAlignment(Pos.CENTER);

        ArrayList<User> members = new ArrayList<>();
        members.add(admin);

        if(admin.getNoFriends() != 0){
            ListView<CheckBox> lst_friends= new ListView<>();
            lst_friends.getStyleClass().add("chbox_friends");
            ObservableList<CheckBox> ov_members = FXCollections.observableArrayList();
            Label lbl_selected_friends = new Label();
            lbl_selected_friends.setStyle("-fx-font-weight: bold;-fx-text-fill: aliceblue");
            lbl_selected_friends.setText("Select Friends to add ("+Integer.toString(members.size()-1)+" selected)");
            for(User friend: admin.getFriends()) ov_members.add(list_item(friend,members,lbl_selected_friends));
            lst_friends.setItems(ov_members);

            VBox vb_list = new VBox(2);
            vb_list.getChildren().addAll(lbl_selected_friends,lst_friends);
            GridPane.setConstraints(vb_list,1,1,1,2);
            container.getChildren().add(vb_list);
        }


        Button btn_confirm = new Button("Create Group");
        Button btn_cancel = new Button("Cancel");

        AnchorPane buttons = new AnchorPane();
        buttons.setStyle("-fx-alignment: center;-fx-min-width: 200;");
        AnchorPane.setLeftAnchor(btn_confirm,5.0);
        AnchorPane.setBottomAnchor(btn_confirm,0.0);
        AnchorPane.setRightAnchor(btn_cancel,5.0);
        AnchorPane.setBottomAnchor(btn_cancel,0.0);
        AnchorPane.setTopAnchor(btn_confirm,30.0);
        AnchorPane.setTopAnchor(btn_cancel,30.0);
        buttons.getChildren().addAll(btn_confirm,btn_cancel);

        container.add(lbl_title,0,0,2,1);
        container.add(txt_groupname,0,1);
        container.add(lbl_error,0,2);
        container.add(buttons,0,3,2,1);

        recover_handled_errors(txt_groupname,lbl_error);

        btn_confirm.setOnAction(e -> {
            boolean done = true;
            String groupname = txt_groupname.getText();
            if (groupname.equals("")) {
                txt_groupname.getStyleClass().add("error_registration_text");
                done = false;
            }
            if(done){
                try{
                    new Group(groupname,members);
                    success = true;
                    window.close();
                }catch (GroupNameException e1){
                    lbl_error.setVisible(true);
                    lbl_error.setText(e1.getMessage());
                    txt_groupname.getStyleClass().add("error_registration_text");


                } catch (Exception ignored) { } //just because it is causing errors
            }
        });
        btn_cancel.setOnAction(e->{
            success = false;
            window.close();
        });
        window.setOnCloseRequest(e-> success=false);

        txt_groupname.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                container.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        Scene scene = new Scene(container);
        scene.getStylesheets().add("style/style.css");
        window.setMinWidth(350);
        window.setScene(scene);
        window.showAndWait();
        return success;
    }
    private static void recover_handled_errors(TextField txt_groupname,Label lbl_error )
    {
        txt_groupname.textProperty().addListener((ov,oldValue,newValue)->{
            int index = txt_groupname.getStyleClass().indexOf("error_registration_text");
            if(index != -1) txt_groupname.getStyleClass().remove(index);
            lbl_error.setText("");
            lbl_error.setVisible(false);
        });


    }
    private static CheckBox list_item(User friend,ArrayList<User> members,Label lbl_selected_friends){
        String name = friend.getName();
        CheckBox checkBox = new CheckBox(name);
        checkBox.selectedProperty().addListener((ov,oldValue,newValue)->{
            if(newValue){
                int index = 0;
                try {
                    index = name_index(members,0,members.size(),friend);
                } catch (Exception ignored) {}
                members.add(index,friend);
            }
            else{
                members.remove(friend);
            }
            int no_selected = members.size()-1;
            lbl_selected_friends.setText("Select Friends to add ("+Integer.toString(no_selected)+" selected)");
        });
        return checkBox;
    }


}


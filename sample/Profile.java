package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Random;

import static sample.Group.noGroups;
import static sample.User.noUsers;
import static sample.usefulFunctions.*;
import static sample.User.*;
import static sample.Group.*;
import static sample.Interface.*;

public class Profile {
    private Stage window;
    private Scene scene,loader_scene;
    private BorderPane layout;
    private User user;
    private Label lbl_users,lbl_groups;

    Profile(Stage same_window, User active_user,Scene loader_scene){
        noUsers = allUsersID.size();
        noGroups = allGroupsID.size();

        this.window = same_window;
        this.user = active_user;
        this.loader_scene = loader_scene;
//
//        scene = new Scene(get_profile_layout(lbl_users,lbl_groups),650,400);
//        scene.getStylesheets().add("style/style.css");
//        window.setScene(scene);
    }
    public BorderPane get_profile_layout(Label lbl_users,Label lbl_groups){
        this.lbl_users = lbl_users;
        this.lbl_groups = lbl_groups;
        layout = new BorderPane();
        layout.setTop(set_header());
        layout.setRight(set_timeline());
        layout.setBottom(set_footer(lbl_users,lbl_groups));
        return layout;
    }
    private AnchorPane set_header() {
        AnchorPane header = new AnchorPane();
        header.getStyleClass().add("header");

        Label lbl_title = new Label("Social-Networks");
        lbl_title.setStyle("-fx-text-fill: aliceblue;    -fx-font-size: 20;    -fx-font-weight: bold;");
        Hyperlink lnk_profile = new Hyperlink(user.getFirstName().toUpperCase());
        lnk_profile.getStyleClass().add("headerlink");
        lnk_profile.setOnAction(e ->window.setScene(scene));
        Hyperlink lnk_logout = new Hyperlink("Log Out");
        lnk_logout.getStyleClass().add("headerlink");
        lnk_logout.setOnAction(e->{
            lnk_logout.getScene().setRoot(get_home_layout());
        });
        HBox hb_header_links = new HBox(2);
        hb_header_links.getChildren().addAll(lnk_profile,lnk_logout);
        AnchorPane.setLeftAnchor(lbl_title,0.0);
        AnchorPane.setTopAnchor(lbl_title,0.0);
        AnchorPane.setRightAnchor(hb_header_links,0.0);
        AnchorPane.setTopAnchor(hb_header_links,0.0);
        header.getChildren().addAll(lbl_title,hb_header_links);
        return header;
    }
    private HBox set_timeline(){
        VBox timeline = new VBox(20);

        HBox hBox = new HBox();

        timeline.getStyleClass().add("timeline");
        TextArea txt_new_post = new TextArea();
        txt_new_post.getStyleClass().add("new_post");




        AnchorPane buttons = new AnchorPane();
        Button btn_post = new Button("Post");
        btn_post.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-min-width: 70px");
        Button btn_clear = new Button("Clear");
        btn_clear.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-min-width: 70px");
        btn_post.setOnAction(e-> this.user.addPost(txt_new_post));
        btn_clear.setOnAction(e-> txt_new_post.clear());
        AnchorPane.setTopAnchor(btn_clear,0.0);
        AnchorPane.setLeftAnchor(btn_clear,0.0);
        AnchorPane.setTopAnchor(btn_post,0.0);
        AnchorPane.setRightAnchor(btn_post,0.0);
        buttons.getChildren().addAll(btn_clear,btn_post);
        timeline.getChildren().addAll(txt_new_post,buttons);



        HBox.setHgrow(timeline,Priority.ALWAYS);
        hBox.getChildren().add(timeline);
        return hBox;
    }
    private AnchorPane set_footer(Label lbl_users,Label lbl_groups){
        AnchorPane footer = new AnchorPane();

        HBox population = new HBox(2);
        if(noUsers<2)lbl_users.setText(Integer.toString(noUsers) + " user");
        else lbl_users.setText(Integer.toString(noUsers) + " users");
        if(noGroups<2) lbl_groups.setText(Integer.toString(noGroups) + " group");
        else lbl_groups.setText(Integer.toString(noGroups) + " groups");
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        population.getChildren().addAll(lbl_users, sep, lbl_groups);

        Hyperlink lnk_delete_acc = new Hyperlink("Delete Account");
        lnk_delete_acc.getStyleClass().add("link_delete_account");
        lnk_delete_acc.setOnAction(e->{
            if(ConfirmDeleteAccount.display()){
                user.delete();
                lnk_delete_acc.getScene().setRoot(get_home_layout());
            }
        });

        AnchorPane.setLeftAnchor(population,25.0);
        AnchorPane.setBottomAnchor(population,20.0);
        AnchorPane.setRightAnchor(lnk_delete_acc,25.0);
        AnchorPane.setBottomAnchor(lnk_delete_acc,20.0);
        footer.getChildren().addAll(population,lnk_delete_acc);
        return footer;
    }
}

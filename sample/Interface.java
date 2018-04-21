package sample;

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

import static sample.usefulFunctions.*;
import static sample.User.*;
import static sample.Group.*;

public class Interface extends Application {

    private Stage window;
    private Scene Home,User,Group;
    private FileChooser fileChooser;
    private File data;
    private BorderPane layout;
    private int noGroups;
    ObservableList<String> Names = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        init_Lists();
        noGroups = 0;
        window = primaryStage;
        window.setTitle("Social-Networks");
        set_layout();
        set_header();
        set_search_field();
        set_login_form();
        set_footer();
        Home = new Scene(layout,650,400);
        Home.getStylesheets().add("style/style.css");
        window.setScene(Home);
        window.show();
        /*window.setOnCloseRequest( e -> {
            e.consume();
            int answer = ConfirmBox.display("Close Project","Do you want to save changes?");
            if(answer == 1){
                save();
                window.close();
            }
            else if(answer == 0){
                window.close();
            }
        });*/
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void import_data() throws IOException {
        /*
        BufferedReader in = new BufferedReader(new FileReader(data));
        String line;
        while((line = in.readLine()) != null)  read(line);
        */
    }
    private void read(String line){
        System.out.println(line);
    }
    private void save(){
        System.out.println("saved");
    }
    private void set_layout(){
        layout = new BorderPane();
        layout.setCenter(null);
    }
    private void set_header(){
        AnchorPane header = new AnchorPane();
        header.getStyleClass().add("header");

        Label lbl_title = new Label("Social-Networks");
        lbl_title.setStyle("-fx-text-fill: aliceblue;    -fx-font-size: 20;    -fx-font-weight: bold;");
        HBox buttons = new HBox();
        set_home_buttons(buttons);
        header.getChildren().addAll(lbl_title,buttons);
        AnchorPane.setTopAnchor(lbl_title,0.0);
        AnchorPane.setTopAnchor(buttons,0.0);
        AnchorPane.setLeftAnchor(lbl_title,0.0);
        AnchorPane.setRightAnchor(buttons,0.0);

        layout.setTop(header);
    }
    private void set_home_buttons(HBox buttons){
        buttons.setSpacing(7);
        Hyperlink lnk_home = new Hyperlink("HOME");
        lnk_home.setOnAction(e -> {
            try {
                start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Hyperlink lnk_profile = new Hyperlink();
        lnk_profile.setVisible(false);
        buttons.getChildren().addAll(lnk_profile,lnk_home);
        lnk_home.getStyleClass().add("headerlink");
        lnk_profile.getStyleClass().add("headerlink");
    }
    private void set_footer() {
        AnchorPane footer = new AnchorPane();

        Button btn_import = new Button("Import Data");
        btn_import.setOnAction(e -> {
            //fileChooser = new FileChooser();
            //data = fileChooser.showOpenDialog(window);
            try {
                import_data();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        AnchorPane.setLeftAnchor(btn_import, 20.0);
        AnchorPane.setBottomAnchor(btn_import, 15.0);

        HBox population = new HBox(2);
        Label lbl_users = new Label();
        lbl_users.setText(Integer.toString(allUsersID.size()) + " users");
        Label lbl_groups = new Label();
        lbl_groups.setText(Integer.toString(noGroups) + " groups");
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        population.getChildren().addAll(lbl_users, sep, lbl_groups);

        AnchorPane.setRightAnchor(population, 15.0);
        AnchorPane.setBottomAnchor(population, 15.0);
        footer.getChildren().addAll(btn_import, population);
        layout.setBottom(footer);
    }
    private void set_search_field(){
        GridPane search_layout = new GridPane();
        search_layout.setVgap(10);
        search_layout.setHgap(5);
        search_layout.getStyleClass().add("search_form");

        Label lbl_search_user = new Label("Find Friends");
        lbl_search_user.setStyle("-fx-font-weight: bold; -fx-alignment: center-left");
        TextField txt_search_user = new TextField();
        txt_search_user.setPromptText("Search...");
        txt_search_user.getStyleClass().add("search_field");

        Label lbl_search_group = new Label("Find Groups");
        lbl_search_group.setStyle("-fx-font-weight: bold; -fx-alignment: center-left");
        TextField txt_search_group = new TextField();
        txt_search_group.setPromptText("Search...");
        txt_search_group.getStyleClass().add("search_field");

        ListView<User> lst_results_user = new ListView<>();
        lst_results_user.setVisible(false);
        lst_results_user.getStyleClass().add("search_results");

        txt_search_user.textProperty().addListener((ov,oldValue,newValue) -> {
            ObservableList<User> matching_names = FXCollections.observableArrayList();
            lst_results_user.setItems(matching_names);
            if(newValue.equals("")) {
                matching_names.clear();
                lst_results_user.setVisible(false);
            }
            else {
                lst_results_user.setVisible(true);
                for (User user : allUsersName){
                    int length = newValue.length();
                    if((length <= user.getFirstName().length() && user.getFirstName().toLowerCase().substring(0,length).equals(newValue.toLowerCase()))
                    || (length <= user.getFirstName().length() && user.getLastName().toLowerCase().substring(0,length).equals(newValue.toLowerCase())))
                        matching_names.add(user);
                }

                if(matching_names.isEmpty())lst_results_user.setVisible(false);
                else {
                    lst_results_user.setCellFactory(e -> new ListCell<User>() {
                        @Override
                        protected void updateItem(User item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null || item.getName() == null) {
                                setText(null);
                            } else {
                                setText(item.getName());
                            }
                        }
                    });
                }
            }
        });

        ListView<Group> lst_results_group = new ListView<>();
        lst_results_group.setVisible(false);
        lst_results_group.getStyleClass().add("search_results");

        txt_search_group.textProperty().addListener((ov,oldValue,newValue) -> {
            ObservableList<Group> matching_groups = FXCollections.observableArrayList();
            lst_results_group.setItems(matching_groups);
            if(newValue.equals("")) {
                matching_groups.clear();
                lst_results_group.setVisible(false);
            }
            else {
                lst_results_group.setVisible(true);
                for (Group group : allGroupsName)
                    if (group.getName().toLowerCase().contains(newValue.toLowerCase())) matching_groups.add(group);
                lst_results_group.setCellFactory( e -> new ListCell<Group>(){
                    @Override
                    protected void updateItem(Group item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getName() == null) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                });
            }
        });

        lst_results_user.setOnMouseClicked( e->{
            User selected_user = lst_results_user.getSelectionModel().getSelectedItem();
            visit_profile(selected_user);
        });

        lst_results_group.setOnMouseClicked( e->{
            Group selected_group = lst_results_group.getSelectionModel().getSelectedItem();
            visit_group(selected_group);
        });

        GridPane.setConstraints(lbl_search_user,0,0);
        GridPane.setConstraints(lbl_search_group,1,0);
        GridPane.setConstraints(txt_search_user,0,1);
        GridPane.setConstraints(txt_search_group,1,1);
        GridPane.setConstraints(lst_results_user,0,2);
        GridPane.setConstraints(lst_results_group,1,2);

        search_layout.getChildren().addAll(lbl_search_user,lbl_search_group,txt_search_user,txt_search_group,lst_results_user,lst_results_group);
        layout.setLeft(search_layout);
    }
    private void set_login_form(){
        VBox login_layout = new VBox(10);

        HBox hbox = new HBox(5);

        TextField txt_username = new TextField();
        txt_username.setPromptText("enter your username");
        txt_username.setMinWidth(50);
        txt_username.getStyleClass().add("login_field");

        Button btn_login = new Button("Login");
        btn_login.setOnAction( e -> {
            String username = txt_username.getText();
            if(login(username)){
                if(login_layout.getChildren().size()>3){
                    txt_username.getStyleClass().remove("login_error_field");
                    login_layout.getChildren().remove(3);
                }
            }
            else login_error(login_layout,txt_username);
        });

        hbox.getChildren().addAll(txt_username,btn_login);

        Text text = new Text("OR");
        text.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-size: 15");

        Button btn_create = new Button("Create new account");
        btn_create.setOnAction( e -> createAccount());
        btn_create.setMaxWidth(Integer.MAX_VALUE);

        login_layout.getChildren().addAll(hbox,text,btn_create);
        login_layout.setAlignment(Pos.TOP_CENTER);
        login_layout.getStyleClass().add("login_form");
        layout.setRight(login_layout);
    }
    private boolean login(String username){
        int index = userNameBinarySearch(allUsersName,0,allUsersName.size(),username);
        if(index == -1) return false;
        else {
            currentID = allUsersName.get(index).getID();
            return true;
        }
    }
    private void createAccount(){

    }
    private void login_error(VBox layout,TextField txtID){
        if(layout.getChildren().size()<4){
            Text error = new Text("invalid username");
            error.setStyle("-fx-alignment: center; -fx-fill: red");
            layout.getChildren().add(error);
            txtID.getStyleClass().add("login_error_field");
        }
    }
    private void init_Lists() throws Exception {
        allUsersID = new ArrayList<>(0);
        allUsersName = new ArrayList<>(0);
        availableIDs = new LinkedList<>();
        allGroupsID = new ArrayList<>(0);
        allGroupsName = new ArrayList<>(0);
        names_generator();
    }
    private void visit_profile(User profile){

    }
    private void visit_group(Group group){

    }
    private void names_generator() throws Exception {
        Random rand = new Random();
        for(int i = 0; i<100000;i++){
            int numChar = 1 + rand.nextInt(10);
            StringBuilder name_builder = new StringBuilder();
            for (int j = 0; j<numChar; j++){
                int c = 97 + rand.nextInt(25);
                name_builder.append((char)c);
            }
            String name = name_builder.toString();
            int index = name_index(allUsersName,0,allUsersName.size(), name);
            if(index != -1)allUsersName.add(index,new User(name,name,name));
        }
    }
}
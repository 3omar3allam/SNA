package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public class Interface extends Application {

    private Stage window;
    private Scene Home,User,Group;
    private FileChooser fileChooser;
    private File data;
    private BorderPane layout;
    private VBox login;
    public ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    public   ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    int noUsers,noGroups;


    @Override
    public void start(Stage primaryStage) {
        noUsers = noGroups = 0;
        window = primaryStage;
        window.setTitle("Social-Networks");
        set_layout();
        set_header();
        set_search_field();
        set_login_form();
        set_footer();
        Home = new Scene(layout,600,400);
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
        noUsers++;
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
        lnk_home.setOnAction(e -> start(window));
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
        lbl_users.setText(Integer.toString(noUsers) + " users");
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

    }
    private void set_login_form(){
        login = new VBox(10);

        HBox hbox = new HBox(5);

        Label lbl_ID = new Label("user ID");
        lbl_ID.setStyle("-fx-font-weight: bold");
        TextField txt_ID = new TextField();
        txt_ID.setPromptText("enter your ID");
        txt_ID.setMinWidth(50);
        txt_ID.setStyle("-fx-alignment: center");

        Button btn_login = new Button("Login");
        btn_login.setOnAction( e -> {
            try{
                int ID = Integer.parseInt(txt_ID.getText());
                login(ID);
                if(login.getChildren().size()>4){
                    if(txt_ID.getStyleClass().size()>1)txt_ID.getStyleClass().remove(1);
                    login.getChildren().remove(3);
                }
            }catch(NumberFormatException ex){
                login_error(login,txt_ID);
            }
        });

        hbox.getChildren().addAll(txt_ID,btn_login);

        Text text = new Text("OR");
        text.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-size: 15");

        Button btn_create = new Button("Create new account");
        btn_create.setOnAction( e -> createAccount());
        btn_create.setMaxWidth(Integer.MAX_VALUE);

        login.getChildren().addAll(hbox,text,btn_create);
        login.setAlignment(Pos.TOP_CENTER);
        login.getStyleClass().add("login_form");
        layout.setRight(login);
    }
    private void login(int ID){

    }
    private void createAccount(){

    }
    private void login_error(VBox layout,TextField txtID){
        if(layout.getChildren().size()<4){
            Text error = new Text("invalid UserID");
            error.setStyle("-fx-alignment: center; -fx-fill: red");
            layout.getChildren().add(error);
            txtID.getStyleClass().add("error_field");
        }
    }
}

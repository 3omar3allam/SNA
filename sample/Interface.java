package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static sample.Group.*;
import static sample.PostsDataBase.ReadPost;
import static sample.PostsDataBase.WritePosts;
import static sample.User.*;
import static sample.UsersDataBase.ReadFromFile;
import static sample.UsersDataBase.WriteUsers;
import static sample.usefulFunctions.addToList;
import static sample.usefulFunctions.userNameBinarySearch;


public class Interface extends Application {
    private static Stage window;
    private static Scene scene;
    private static FileChooser fileChooser;
    private static File data;
    private static BorderPane layout;
    private static Label lbl_users,lbl_groups;
    private static User current_user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Social-Networks");
        scene = new Scene(get_home_layout(),650,400);
        scene.getStylesheets().add("style/style.css");
        window.setScene(scene);
        init_Lists();
        window.show();
        window.setOnCloseRequest( e -> {
            e.consume();
            int answer = ConfirmClose.display("Close Project","Do you want to save changes?");
            if(answer == 1){
                save();
                window.close();
            }
            else if(answer == 0){
                window.close();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);

    }
    private static void import_data() throws IOException {
        /*
        BufferedReader in = new BufferedReader(new FileReader(data));
        String line;
        while((line = in.readLine()) != null)  read(line);
        */
    }
    private static void read(String line){
        System.out.println(line);
    }
    private static void save(){
        System.out.println("saved");
        WriteUsers();
        WritePosts();

    }
    static BorderPane get_home_layout(){
        window.setMinWidth(650);
        window.setMaximized(false);
        layout = new BorderPane();
        layout.setCenter(null);
        layout.setTop(set_header());
        layout.setLeft(set_search_field());
        layout.setRight(set_login_form());
        layout.setBottom(set_footer());
        return layout;
    }
    static AnchorPane set_header(){
        AnchorPane header = new AnchorPane();
        header.getStyleClass().add("header");

        Label lbl_title = new Label("Social-Networks");
        lbl_title.setStyle("-fx-text-fill: aliceblue;    -fx-font-size: 20;    -fx-font-weight: bold;");
        Hyperlink lnk_home = new Hyperlink("HOME");
        lnk_home.getStyleClass().add("headerlink");
        lnk_home.setOnAction(e ->lnk_home.getScene().setRoot(get_home_layout()));
        header.getChildren().addAll(lbl_title,lnk_home);
        AnchorPane.setTopAnchor(lbl_title,0.0);
        AnchorPane.setTopAnchor(lnk_home,0.0);
        AnchorPane.setLeftAnchor(lbl_title,0.0);
        AnchorPane.setRightAnchor(lnk_home,0.0);
        return header;
    }
    static AnchorPane set_footer() {
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
        AnchorPane.setRightAnchor(btn_import, 25.0);
        AnchorPane.setBottomAnchor(btn_import, 20.0);

        HBox population = new HBox(2);
        lbl_users = new Label();
        if(noUsers<2)lbl_users.setText(Integer.toString(noUsers) + " user");
        else lbl_users.setText(Integer.toString(noUsers) + " users");
        lbl_groups = new Label();
        if(noGroups<2) lbl_groups.setText(Integer.toString(noGroups) + " group");
        else lbl_groups.setText(Integer.toString(noGroups) + " groups");
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        population.getChildren().addAll(lbl_users, sep, lbl_groups);

        AnchorPane.setLeftAnchor(population, 25.0);
        AnchorPane.setBottomAnchor(population, 20.0);
        footer.getChildren().addAll(btn_import, population);
        return footer;
    }
    static GridPane set_search_field(){
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
                    if((length <= user.getName().length() && user.getName().toLowerCase().substring(0,length).equals(newValue.toLowerCase()))
                    || (length <= user.getLastName().length() && user.getLastName().toLowerCase().substring(0,length).equals(newValue.toLowerCase())))
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
                for (Group group : allGroupsName){
                    int length = newValue.length();
                    if (length <= group.getName().length() && group.getName().toLowerCase().substring(0,length).equals(newValue.toLowerCase())) matching_groups.add(group);
                }
                if(matching_groups.isEmpty()) lst_results_group.setVisible(false);

                else lst_results_group.setCellFactory( e -> new ListCell<Group>(){
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
        return search_layout;
    }
    static VBox set_login_form(){
        VBox login_layout = new VBox(10);

        HBox hbox = new HBox(5);

        TextField txt_username = new TextField();
        txt_username.setPromptText("enter your username");
        txt_username.setMinWidth(50);
        txt_username.getStyleClass().add("login_field");

        Label lbl_error = new Label("invalid username");
        lbl_error.setStyle("-fx-text-alignment: center; -fx-text-fill: red");
        lbl_error.setVisible(false);

        Button btn_login = new Button("Login");
        btn_login.setOnAction( e -> {
            String username = txt_username.getText();
            if(login(username)){
                if(txt_username.getStyleClass().indexOf("login_error_field") != -1){
                    txt_username.getStyleClass().remove("login_error_field");
                    lbl_error.setVisible(false);
                }
                btn_login.getScene().setRoot(new Profile(current_user).get_page_layout());
                window.setMaximized(true);
                txt_username.setText("");
                current_user = null;
            }
            else {
                txt_username.getStyleClass().add("login_error_field");
                lbl_error.setVisible(true);
            }
        });
        txt_username.textProperty().addListener((ov, oldValue,newValue)->{
            if (newValue != null && txt_username.getStyleClass().indexOf("login_error_field")!=-1) {
                txt_username.getStyleClass().remove("login_error_field");
                lbl_error.setVisible(false);
            }
        });

        hbox.getChildren().addAll(txt_username,btn_login);

        Text text = new Text("OR");
        text.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-size: 15");

        Button btn_create = new Button("Create new account");
        btn_create.setOnAction( e -> createAccount());
        btn_create.setMaxWidth(Integer.MAX_VALUE);

        login_layout.getChildren().addAll(hbox,text,btn_create,lbl_error);
        login_layout.setAlignment(Pos.TOP_CENTER);
        login_layout.getStyleClass().add("login_form");
        return(login_layout);
    }
    private static boolean login(String username){
        int index = userNameBinarySearch(allUsersName,0,allUsersName.size(),username);
        if(index == -1) return false;
        else {
            current_user = allUsersName.get(index);
            return true;
        }
    }
    private static void createAccount(){
        User new_user = Registration.display("Register");
        if(new_user != null) {
            scene.setRoot(new Profile(new_user).get_page_layout());
            window.setMaximized(true);
        }
    }
    private static void init_Lists() throws Exception {
        allUsersID = new ArrayList<>(0);
        allUsersName = new ArrayList<>(0);
        User.availableIDs = new LinkedList<>();
        Group.availableIDs = new LinkedList<>();
        allGroupsID = new ArrayList<>(0);
        allGroupsName = new ArrayList<>(0);
//        try{
//            new User("3omar3allam","Omar","Allam","male",LocalDate.of(1996,7,7));
//            new User("mohamed","Mohamed","Abd El Salam","male",LocalDate.of(1995,5,1));
//        }catch(Exception ignored){}
       // names_generator();
          ReadFromFile();
         ReadPost();

    }
    private static void visit_profile(User profile){
        window.setMinWidth(800);
        scene.setRoot(new Profile(null).get_page_layout(profile));
    }
    private static void visit_group(Group group){
        scene.setRoot(new GroupPage(group).get_page_layout());
    }
    private static void names_generator() {
        Random rand = new Random();
        for(int i = 0; i<100000;i++){
            int numChar = 1 + rand.nextInt(5);
            StringBuilder name_builder = new StringBuilder();
            for (int j = 0; j<numChar; j++){
                int c = 97 + rand.nextInt(25);
                name_builder.append((char)c);
            }
            String name = name_builder.toString();
            try{
                addToList(new User(name,name,name,"male",LocalDate.of(1996,7,7)));
            }catch(Exception ignored){}
        }
        scene.setRoot(get_home_layout());
    }
}
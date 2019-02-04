package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;

import static sample.Interface.*;
import static sample.Profile.*;
import static sample.User.*;
import static sample.Group.*;
import static sample.usefulFunctions.*;

public class GroupPage extends Page {
    private User online_user;
    private Group group;
    private Page calling_page; // el page elly nadahet el group di 3ashan arga3lah 3la tol

    GroupPage(Group group){
        this.group = group;
        this.calling_page = null;
    }
    GroupPage(Group group,Page calling_page){
        this.group = group;
        this.calling_page = calling_page;
    }
    BorderPane get_page_layout(){
        return get_page_layout(null);
    }
    BorderPane get_page_layout(User user){
        this.online_user = user;
        BorderPane layout = new BorderPane();
        /** temporary for testing **/
        Button btn_return = new Button("return");
        btn_return.setOnAction(e->{
            if(calling_page == null) btn_return.getScene().setRoot(get_home_layout());
            else btn_return.getScene().setRoot(this.calling_page.get_page_layout());
        });
        layout.setTop(set_header());
        layout.setLeft(set_friends_and_groups());
        layout.setRight(set_timeline());
        layout.setBottom(set_footer(user));
        return layout;
    }
    private AnchorPane set_header() {
        AnchorPane header = new AnchorPane();
        header.getStyleClass().add("header");
        Label lbl_title = new Label("Social-Networks");
        lbl_title.setStyle("-fx-text-fill: aliceblue;    -fx-font-size: 20;    -fx-font-weight: bold;");

        Hyperlink lnk_profile = new Hyperlink();
        if(this.online_user!=null)lnk_profile.setText(this.online_user.getFirstName().toUpperCase());
        else lnk_profile.setVisible(false);
        lnk_profile.getStyleClass().add("headerlink");
        lnk_profile.setOnAction(e ->lnk_profile.getScene().setRoot(calling_page.get_page_layout(this.online_user)));

        Hyperlink lnk_logout = new Hyperlink("Log Out");
        lnk_logout.getStyleClass().add("headerlink");
        lnk_logout.setOnAction(e-> lnk_logout.getScene().setRoot(get_home_layout()));

        HBox hb_header_links = new HBox(2);
        hb_header_links.getChildren().addAll(lnk_profile,lnk_logout);
        AnchorPane.setLeftAnchor(lbl_title,0.0);
        AnchorPane.setTopAnchor(lbl_title,0.0);
        AnchorPane.setRightAnchor(hb_header_links,0.0);
        AnchorPane.setTopAnchor(hb_header_links,0.0);
        header.getChildren().addAll(lbl_title,hb_header_links);
        return header;
    }
    private AnchorPane set_footer(User user){
        AnchorPane footer = new AnchorPane();

        HBox population = new HBox(2);
        Label lbl_users = new Label();
        if(noUsers<2)lbl_users.setText(Integer.toString(noUsers) + " user");
        else lbl_users.setText(Integer.toString(noUsers) + " users");
        Label lbl_groups = new Label();
        if(noGroups<2) lbl_groups.setText(Integer.toString(noGroups) + " group");
        else lbl_groups.setText(Integer.toString(noGroups) + " groups");
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        population.getChildren().addAll(lbl_users, sep, lbl_groups);
        AnchorPane.setLeftAnchor(population,25.0);
        AnchorPane.setBottomAnchor(population,20.0);
        footer.getChildren().add(population);

        if(user == this.group.getAdmin()) {
            Hyperlink lnk_delete_acc = new Hyperlink("Delete Group");
            lnk_delete_acc.getStyleClass().add("link_delete_account");
            lnk_delete_acc.setOnAction(e -> {
                if (ConfirmDelete.display("Delete The Group","Are you sure you want to permanently delete this Group?")) {

                    group.delete();
                    lnk_delete_acc.getScene().setRoot(this.calling_page.get_page_layout());
                }
            });
            AnchorPane.setRightAnchor(lnk_delete_acc, 25.0);
            AnchorPane.setBottomAnchor(lnk_delete_acc, 20.0);
            footer.getChildren().add(lnk_delete_acc);
        }
        else
        {
            Hyperlink lnk_delete_acc = new Hyperlink("Leave Group");
            lnk_delete_acc.getStyleClass().add("link_delete_account");
            lnk_delete_acc.setOnAction(e -> {
                if (ConfirmDelete.display("Leave The Group","Are you sure you want to leave this Group?")) {

                    group.removeMember(online_user);
                    lnk_delete_acc.getScene().setRoot(this.calling_page.get_page_layout());
                }
            });
            AnchorPane.setRightAnchor(lnk_delete_acc, 25.0);
            AnchorPane.setBottomAnchor(lnk_delete_acc, 20.0);
            footer.getChildren().add(lnk_delete_acc);
        }

        return footer;
    }
    private Node set_timeline(){
        if(online_user != null && !group.isMember(online_user)) {
            Button btn_join = new Button("Join Group");
            btn_join.setStyle("-fx-min-width: 200px;-fx-min-height: 20px;-fx-font-weight: bold");
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(btn_join,100.0);
            AnchorPane.setRightAnchor(btn_join,75.0);
            anchorPane.getChildren().add(btn_join);
            btn_join.setOnAction(e->{
                group.addMember(online_user);
                btn_join.getScene().setRoot(get_page_layout(online_user));
            });
            return anchorPane;
        }
        else{
            VBox timeline = new VBox(20);
            HBox hBox = new HBox();
            hBox.getStyleClass().add("timeline");

            ObservableList<VBox> array_of_posts = FXCollections.observableArrayList();
            for (Post post : group.getPosts()) array_of_posts.add(0, list_item(post, array_of_posts));
            ListView<VBox> lst_posts = new ListView<>();
            lst_posts.getStyleClass().add("list_of_posts");
            lst_posts.setItems(array_of_posts);


            TextArea txt_new_post = new TextArea();
            txt_new_post.getStyleClass().add("new_post");
            if (online_user != null)
                txt_new_post.setPromptText(String.format("What's on your mind, %s?", this.online_user.getFirstName()));
            else txt_new_post.setPromptText("What's on your mind?");

            AnchorPane buttons = new AnchorPane();
            Button btn_post = new Button("Post");
            btn_post.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-min-width: 70px");
            Button btn_clear = new Button("Clear");
            btn_clear.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-min-width: 70px");
            btn_post.setOnAction(e -> {
                Post post = group.addPost(txt_new_post.getText(), this.online_user);
                array_of_posts.add(0, list_item(post, array_of_posts));
                txt_new_post.clear();
            });
            btn_clear.setOnAction(e -> txt_new_post.clear());
            AnchorPane.setTopAnchor(btn_clear, 0.0);
            AnchorPane.setLeftAnchor(btn_clear, 0.0);
            AnchorPane.setTopAnchor(btn_post, 0.0);
            AnchorPane.setRightAnchor(btn_post, 0.0);
            buttons.getChildren().addAll(btn_clear, btn_post);

            timeline.getChildren().addAll(txt_new_post, buttons, lst_posts);


            HBox.setHgrow(timeline, Priority.ALWAYS);
            hBox.getChildren().add(timeline);
            return hBox;
        }
    }
    private VBox list_item(Post post,ObservableList array_of_posts){
        Label owner =new Label(post.getOwner().getName());
        owner.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-text-fill: BLUE;");
        VBox vBox = new VBox(2);
        vBox.setStyle("-fx-border-width: 2px;-fx-border-radius:3px;-fx-background-color: transparent;-fx-border-color: grey");
        Label content = new Label(post.toString());
        content.setStyle("-fx-padding: 10px");

        HBox buttons = new HBox();
        Label lbl_nolikes = new Label();
        lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator2.setOrientation(Orientation.VERTICAL);
        if((this.online_user == group.getAdmin() && this.online_user!=post.getOwner())){
            Hyperlink lnk_delete = new Hyperlink("Delete");
            lnk_delete.getStyleClass().add("post_link");
            lnk_delete.setOnAction(e-> {
                array_of_posts.remove(vBox);
                group.deletePost(post);
            });
            Hyperlink lnk_like = new Hyperlink("Like");
            lnk_like.getStyleClass().add("post_link");
            if(post.getLikers().contains(this.online_user)) lnk_like.setText("Unlike");
            lnk_like.setOnAction(e->{
                if(lnk_like.getText().equals("Like")){
                    group.likePost(post,this.online_user);
                    lnk_like.setText("Unlike");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
                else if(lnk_like.getText().equals("Unlike")){
                    group.unlikePost(post,this.online_user);
                    lnk_like.setText("Like");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
            });
            buttons.getChildren().addAll(lnk_like,separator1,lbl_nolikes,separator2,lnk_delete);
        }
        else if(this.online_user==post.getOwner() || this.online_user == group.getAdmin() ){
            Hyperlink lnk_delete = new Hyperlink("Delete");
            lnk_delete.getStyleClass().add("post_link");
            lnk_delete.setOnAction(e-> {
                array_of_posts.remove(vBox);
                group.deletePost(post);
            });
            buttons.getChildren().addAll(lnk_delete,separator1,lbl_nolikes);
        }
        else
        {
            Hyperlink lnk_like = new Hyperlink("Like");
            lnk_like.getStyleClass().add("post_link");
            if(post.getLikers().contains(this.online_user)) lnk_like.setText("Unlike");
            lnk_like.setOnAction(e->{
                if(lnk_like.getText().equals("Like")){
                    group.likePost(post,this.online_user);
                    lnk_like.setText("Unlike");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
                else if(lnk_like.getText().equals("Unlike")){
                    group.unlikePost(post,this.online_user);
                    lnk_like.setText("Like");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
            });
            buttons.getChildren().addAll(lnk_like,separator1,lbl_nolikes);
        }

        DateTimeFormatter formatter;
        if(Period.between(LocalDate.from(post.getTime()),LocalDate.now()).getDays() < 1)formatter = DateTimeFormatter.ofPattern("HH:mm");
        else if (Period.between(LocalDate.from(post.getTime()),LocalDate.now()).getYears() < 1)formatter = DateTimeFormatter.ofPattern("MMM dd at HH:mm");
        else formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy at HH:mm");

        Label lbl_date = new Label(formatter.format(post.getTime()));
        lbl_date.setStyle("-fx-text-fill: dimgrey");
        AnchorPane.setLeftAnchor(lbl_date,10.0);
        AnchorPane.setTopAnchor(lbl_date,0.0);
        AnchorPane.setRightAnchor(buttons,10.0);
        AnchorPane.setTopAnchor(buttons,0.0);
        AnchorPane post_details = new AnchorPane();
        post_details.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: darkslategrey");
        post_details.getChildren().addAll(lbl_date,buttons);
        vBox.getChildren().addAll(owner,content,post_details);


        return vBox;
    }


    private VBox set_friends_and_groups(){


        VBox container = new VBox(20);
        container.setPadding(new Insets(25));

        GridPane members_container = new GridPane();
        members_container.setVgap(10);
        members_container.setHgap(15);
        members_container.getStyleClass().add("friends_container");
        Label lbl_title = new Label(String.format("Welcome to %s", group.getName()));
        lbl_title.setStyle("-fx-text-fill: grey;    -fx-font-size: 20;    -fx-font-weight: bold;");
        Label lbl_members = new Label();
        lbl_members.setText(String.format("Members (%s)",group.getNoMembers()));
        lbl_members.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        members_container.add(lbl_members,0,0);
        if(group.getNoMembers() != 0) {
            String[] colors = {"grey","lightblue"};
            ObservableList<Hyperlink> ov_members = FXCollections.observableArrayList();
            int index = 0;
            for (User member : group.getMembers()) {
                Hyperlink link ;
                if(group.isAdmin(member)){ link = new Hyperlink(String.format("%s\n(ADMIN)",member.getName()));}
                else { link = new Hyperlink(member.getName());}
                String color = colors[index % 2];
                link.getStyleClass().add("friend_in_list");
                link.setStyle(String.format("-fx-background-color: %s;",color));
                link.setOnAction(e-> {
                    if(this.online_user == group.getAdmin()) {
                        int answer = PromptMemberSelection.display("What do you want to do", member, group);
                        if (answer == 1)
                            link.getScene().setRoot(new Profile(this.online_user, this).get_page_layout(member));
                        else if (answer == 2) {
                            group.removeMember(member);
                            link.getScene().setRoot(get_page_layout(online_user));
                        }
                    }
                    else link.getScene().setRoot(new Profile(this.online_user, this).get_page_layout(member));
                });
                ov_members.add(link);
                index++;
            }
            ListView<Hyperlink> lst_friends = new ListView<>();
            lst_friends.getStyleClass().add("friends");
            lst_friends.setOrientation(Orientation.HORIZONTAL);
            lst_friends.setItems(ov_members);
            members_container.add(lst_friends,0,1,10,1);
        }

        container.getChildren().addAll(lbl_title,members_container);


        /////////////////////////////////////////////////////////////////////////////////////////////
        GridPane friends_container = new GridPane();
        friends_container.setVgap(10);
        friends_container.setHgap(15);
        friends_container.getStyleClass().add("friends_container");

        if(online_user!=null) {
            Label lbl_friends = new Label();
            lbl_friends.setText("Invite Friends");
            lbl_friends.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
            friends_container.add(lbl_friends, 0, 0);
            if (this.online_user.getNoFriends() != 0) {
                String[] colors = {"grey", "lightblue"};
                ObservableList<Hyperlink> ov_friends = FXCollections.observableArrayList();
                int index = 0;
                boolean allFriendsAreIn = true;
                for (User friend : this.online_user.getFriends()) {
                    if (!group.isMember(friend)) {
                        allFriendsAreIn = false;
                        Hyperlink link = new Hyperlink(friend.getName());
                        String color = colors[index % 2];
                        link.getStyleClass().add("friend_in_list");
                        link.setStyle(String.format("-fx-background-color: %s;", color));
                        link.setOnAction(e -> {
                            group.addMember(friend);
                            link.getScene().setRoot(get_page_layout(online_user));
                        });
                        ov_friends.add(link);
                        index++;
                    }
                }
                if (allFriendsAreIn) {
                    Label lbl_null_friends = new Label();
                    lbl_null_friends.setText("All your friends are already members ");
                    lbl_null_friends.setStyle("-fx-alignment: center;-fx-font-weight: bold");
                    friends_container.add(lbl_null_friends, 0, 2, 2, 1);
                } else {
                    ListView<Hyperlink> lst_friends = new ListView<>();
                    lst_friends.getStyleClass().add("friends");
                    lst_friends.setOrientation(Orientation.HORIZONTAL);
                    lst_friends.setItems(ov_friends);
                    friends_container.add(lst_friends, 0, 1, 2, 1);
                }
            } else {
                Label lbl_null_friends = new Label();
                lbl_null_friends.setText("you have no friends ");
                lbl_null_friends.setStyle("-fx-alignment: center;-fx-font-weight: bold");
                friends_container.add(lbl_null_friends, 0, 2, 2, 1);
            }
            container.getChildren().add(friends_container);
        }
        /////////////////////////////////////////////////////////////////
        GridPane search_layout = new GridPane();
        search_layout.setVgap(10);
        search_layout.setHgap(5);
        search_layout.getStyleClass().add("search_form");

        Label lbl_search_user = new Label("Find members");
        lbl_search_user.setStyle("-fx-font-weight: bold; -fx-alignment: center-left");
        TextField txt_search_user = new TextField();
        txt_search_user.setPromptText("Search...");
        txt_search_user.getStyleClass().add("search_field");

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
                search_group_members(online_user,group, matching_names, newValue);
                if (matching_names.isEmpty()) lst_results_user.setVisible(false);
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
        lst_results_user.setOnMouseClicked( e->{
            User selected_user = lst_results_user.getSelectionModel().getSelectedItem();
            if(this.online_user == group.getAdmin()) {
                int answer = PromptMemberSelection.display("What do you want to do", selected_user, group);
                if (answer == 1)
                    lst_results_user.getScene().setRoot(new Profile(this.online_user, this).get_page_layout(selected_user));
                else if (answer == 2) {
                    group.removeMember(selected_user);
                    lst_results_user.getScene().setRoot(get_page_layout(online_user));
                }
            }
            else lst_results_user.getScene().setRoot(new Profile(this.online_user, this).get_page_layout(selected_user));
        });
        GridPane.setConstraints(lbl_search_user,0,0);
        GridPane.setConstraints(txt_search_user,0,1);
        GridPane.setConstraints(lst_results_user,0,2);
        search_layout.getChildren().addAll(lbl_search_user,txt_search_user,lst_results_user);

///////////////////////////////////////////////////////////////////////////////////
        GridPane search_layout2 = new GridPane();
        search_layout2.setVgap(10);
        search_layout2.setHgap(5);
        search_layout2.getStyleClass().add("search_form");

        Label lbl_search_user2 = new Label("Add friend to the group");
        lbl_search_user2.setStyle("-fx-font-weight: bold; -fx-alignment: center-left");
        TextField txt_search_user2 = new TextField();
        txt_search_user2.setPromptText("Search...");
        txt_search_user2.getStyleClass().add("search_field");

        ListView<User> lst_results_user2 = new ListView<>();
        lst_results_user2.setVisible(false);
        lst_results_user2.getStyleClass().add("search_results");

        txt_search_user2.textProperty().addListener((ov,oldValue,newValue) -> {
            ObservableList<User> matching_names = FXCollections.observableArrayList();
            lst_results_user2.setItems(matching_names);
            if(newValue.equals("")) {
                matching_names.clear();
                lst_results_user2.setVisible(false);
            }
            else {
                lst_results_user2.setVisible(true);
                search_user_friends(this.online_user, matching_names, newValue,group);
                if (matching_names.isEmpty()) lst_results_user2.setVisible(false);
                else {
                    lst_results_user2.setCellFactory(e -> new ListCell<User>() {
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
        lst_results_user2.setOnMouseClicked( e->{
            User selected_user = lst_results_user2.getSelectionModel().getSelectedItem();
            group.addMember(selected_user);
            lst_results_user2.getScene().setRoot(get_page_layout(online_user));
        });
        GridPane.setConstraints(lbl_search_user2,0,0);
        GridPane.setConstraints(txt_search_user2,0,1);
        GridPane.setConstraints(lst_results_user2,0,2);
        search_layout2.getChildren().addAll(lbl_search_user2,txt_search_user2,lst_results_user2);
        container.getChildren().addAll(search_layout2);
        HBox AllSearch =new HBox(3);
        AllSearch.getChildren().addAll(search_layout,search_layout2);
        container.getChildren().addAll(AllSearch);
        return container;
    }

}
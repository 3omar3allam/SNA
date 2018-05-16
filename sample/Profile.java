package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static sample.Group.noGroups;
import static sample.User.noUsers;
import static sample.usefulFunctions.*;
import static sample.Interface.*;
import static sample.GroupPage.*;

class Profile extends Page {
    private User online_user;
    private Page calling_page; //el page elly nadahet el profile da (law hasal ya3ni) 3ashan arga3laha 3la tol

    Profile(User online_user){
        this.online_user = online_user;
        InitMutualFriendsList(online_user);
        this.calling_page = null;
    }
    Profile(User online_user, Page calling_page){
        this.online_user = online_user;
        InitMutualFriendsList(online_user);
        this.calling_page = calling_page;
    }
    BorderPane get_page_layout(){
        return get_page_layout(online_user);
    }
    BorderPane get_page_layout(User user){
        BorderPane layout = new BorderPane();
        if(online_user!=null){
            layout.setTop(set_header(user));
            layout.setRight(set_timeline(user));
            layout.setBottom(set_footer(user));
            layout.setLeft(set_search(user));
            layout.setCenter(set_friends_and_groups(user));
        }
        else{
            layout.setTop(Interface.set_header());
            layout.setRight(Interface.set_login_form());
            layout.setBottom(Interface.set_footer());
            layout.setLeft(set_timeline(user));
            layout.setCenter(null);
        }
        return layout;
    }
    private AnchorPane set_header(User user) {
        AnchorPane header = new AnchorPane();
        header.getStyleClass().add("header");
        Label lbl_title = new Label("Social-Networks");
        lbl_title.setStyle("-fx-text-fill: aliceblue;    -fx-font-size: 20;    -fx-font-weight: bold;");

        Hyperlink lnk_profile = new Hyperlink(this.online_user.getFirstName().toUpperCase());
        lnk_profile.getStyleClass().add("headerlink");
        lnk_profile.setOnAction(e ->lnk_profile.getScene().setRoot(get_page_layout()));

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

    private HBox set_timeline(User user){
        VBox timeline = new VBox(20);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("timeline");

        ObservableList<VBox> array_of_posts = FXCollections.observableArrayList();
        for(Post post:user.getPosts()) array_of_posts.add(0,list_item(post,user,array_of_posts));
        ListView<VBox> lst_posts = new ListView<>();
        lst_posts.getStyleClass().add("list_of_posts");
        lst_posts.setItems(array_of_posts);

        /** This section unlocks only if it is the logged in user's profile **/
        if(this.online_user == user) {
            TextArea txt_new_post = new TextArea();
            txt_new_post.getStyleClass().add("new_post");
            txt_new_post.setPromptText("What's on your mind, " + this.online_user.getFirstName()+"?");

            AnchorPane buttons = new AnchorPane();
            Button btn_post = new Button("Post");
            btn_post.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-min-width: 70px");
            Button btn_clear = new Button("Clear");
            btn_clear.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-min-width: 70px");
            btn_post.setOnAction(e -> {
                Post post = this.online_user.addPost(txt_new_post.getText());
                array_of_posts.add(0, list_item(post,user,array_of_posts));
                txt_new_post.clear();
            });
            btn_clear.setOnAction(e -> txt_new_post.clear());
            AnchorPane.setTopAnchor(btn_clear, 0.0);
            AnchorPane.setLeftAnchor(btn_clear, 0.0);
            AnchorPane.setTopAnchor(btn_post, 0.0);
            AnchorPane.setRightAnchor(btn_post, 0.0);
            buttons.getChildren().addAll(btn_clear, btn_post);

            timeline.getChildren().addAll(txt_new_post, buttons, lst_posts);
        }
        else {
            GridPane info = new GridPane();
            info.setHgap(10);
            info.setVgap(5);
            Label lbl_name = new Label(user.getName());
            lbl_name.getStyleClass().add("profile_name");
            Label lbl_gender_and_age = new Label(user.getGender_string()+" ("+user.getAge()+")");
            lbl_gender_and_age.setStyle("-fx-font-weight: bold;-fx-text-alignment: center;-fx-alignment: center");
            Label lbl_no_friends = new Label();
            if(user.getNoFriends()<2) lbl_no_friends.setText(Integer.toString(user.getNoFriends())+" friend");
            else lbl_no_friends.setText(Integer.toString(user.getNoFriends())+" friends");
            Label lbl_no_groups;
            if(user.getGroups().size()<2) lbl_no_groups = new Label("member in "+Integer.toString(user.getGroups().size())+" group");
            else lbl_no_groups = new Label("member in "+Integer.toString(user.getGroups().size())+" groups");

            GridPane.setConstraints(lbl_name,0,0,2,1);
            GridPane.setConstraints(lbl_gender_and_age,0,1,2,1);
            GridPane.setConstraints(lbl_no_friends,0,2);
            GridPane.setConstraints(lbl_no_groups,1,2);
            info.getChildren().addAll(lbl_name,lbl_gender_and_age,lbl_no_friends,lbl_no_groups);

            if(this.online_user != null && this.online_user.isFriend(user)){
                Button btn_remove_friend = new Button("Remove Friend");
                btn_remove_friend.setOnAction(e->{
                    if(ConfirmDelete.display("Remove Friend","Are you sure you want to remove "+user.getFirstName()+"?")){
                        this.online_user.deleteFriend(user);
                        btn_remove_friend.getScene().setRoot(get_page_layout(user));
                    }
                });
                GridPane.setConstraints(btn_remove_friend,0,3);
                info.getChildren().add(btn_remove_friend);
            }
            else{
                lst_posts.setVisible(false);
                Button btn_add_friend = new Button("Add Friend");
                btn_add_friend.setOnAction(e->{
                    try {
                        this.online_user.addFriendSpecial(user);
                    } catch (Exception ignored) {}
                    btn_add_friend.getScene().setRoot(new Profile(this.online_user,this).get_page_layout(user));
                });
                GridPane.setConstraints(btn_add_friend,0,3);
                info.getChildren().add(btn_add_friend);
                if(this.online_user == null){
                    timeline.getStyleClass().remove("timeline");
                    timeline.getStyleClass().add("timeline_for_non_users");
                    btn_add_friend.setVisible(false);
                }
            }
            timeline.getChildren().addAll(info,lst_posts);
        }
        HBox.setHgrow(timeline,Priority.ALWAYS);
        hBox.getChildren().add(timeline);
        return hBox;
    }
    private VBox list_item(Post post,User user,ObservableList array_of_posts){
        VBox vBox = new VBox(2);
        vBox.setStyle("-fx-border-width: 2px;-fx-border-radius:3px;-fx-background-color: transparent;-fx-border-color: grey");
        Label content = new Label(post.toString());
        content.setStyle("-fx-padding: 10px");

        HBox buttons = new HBox();
        Label lbl_nolikes = new Label();
        lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        if(user == this.online_user){
            Hyperlink lnk_delete = new Hyperlink("Delete");
            lnk_delete.getStyleClass().add("post_link");
            lnk_delete.setOnAction(e-> {
                array_of_posts.remove(vBox);
                user.deletePost(post);
            });
            buttons.getChildren().addAll(lbl_nolikes,separator,lnk_delete);
        }
        else{
            Hyperlink lnk_like = new Hyperlink("Like");
            lnk_like.getStyleClass().add("post_link");
            if(post.getLikers().contains(user)) lnk_like.setText("Unlike");
            lnk_like.setOnAction(e->{
                if(lnk_like.getText().equals("Like")){
                    this.online_user.likePost(post);
                    lnk_like.setText("Unlike");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
                else if(lnk_like.getText().equals("Unlike")){
                    this.online_user.unlikePost(post);
                    lnk_like.setText("Like");
                    lbl_nolikes.setText(Integer.toString(post.getLikes())+" liked this post");
                }
            });
            buttons.getChildren().addAll(lnk_like,separator,lbl_nolikes);
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
        vBox.getChildren().addAll(content,post_details);

        return vBox;
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

        if(user == this.online_user) {
            Hyperlink lnk_delete_acc = new Hyperlink("Delete Account");
            lnk_delete_acc.getStyleClass().add("link_delete_account");
            lnk_delete_acc.setOnAction(e -> {
                if (ConfirmDelete.display("Delete Account","Are you sure you want to permanently delete this account?")) {
                    user.delete();
                    lnk_delete_acc.getScene().setRoot(get_home_layout());
                }
            });
            AnchorPane.setRightAnchor(lnk_delete_acc, 25.0);
            AnchorPane.setBottomAnchor(lnk_delete_acc, 20.0);
            footer.getChildren().add(lnk_delete_acc);
        }
        return footer;
    }
    private GridPane set_search(User user){
        GridPane search_layout = new GridPane();
        search_layout.setVgap(10);
        search_layout.setHgap(5);
        search_layout.getStyleClass().add("search_form");

        Label lbl_search_user = new Label("Search Friends");
        lbl_search_user.setStyle("-fx-font-weight: bold; -fx-alignment: center-left");
        TextField txt_search_user = new TextField();
        txt_search_user.setPromptText("Search...");
        txt_search_user.getStyleClass().add("search_field");

        Label lbl_search_group = new Label("Search Groups");
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
                ArrayList<Integer> mutualFriends=new ArrayList<>();
                user_graph_search(this.online_user,matching_names,mutualFriends,newValue);

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

                ArrayList<Integer> noFriendsInGroup=new ArrayList<>();
                group_graph_search(this.online_user,matching_groups,noFriendsInGroup,newValue);
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
            lst_results_user.getScene().setRoot(new Profile(this.online_user,this).get_page_layout(selected_user));
        });

        lst_results_group.setOnMouseClicked( e->{
            Group selected_group = lst_results_group.getSelectionModel().getSelectedItem();
            lst_results_group.getScene().setRoot(new GroupPage(selected_group,this).get_page_layout(this.online_user));
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
    private VBox set_friends_and_groups(User user){
        VBox container = new VBox(20);
        container.setPadding(new Insets(25));

        GridPane friends_container = new GridPane();
        friends_container.setVgap(10);
        friends_container.setHgap(15);
        friends_container.getStyleClass().add("friends_container");

        Label lbl_friends = new Label();
        lbl_friends.setText("Friends ("+user.getNoFriends()+")");
        lbl_friends.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        friends_container.add(lbl_friends,0,0);
        if(user.getNoFriends() != 0) {
            String[] colors = {"grey","lightblue"};
            ObservableList<Hyperlink> ov_friends = FXCollections.observableArrayList();
            int index = 0;
            for (User friend : user.getFriends()) {
                Hyperlink link = new Hyperlink(friend.getName());
                String color = colors[index % 2];
                link.getStyleClass().add("friend_in_list");
                link.setStyle("-fx-background-color: " + color + ";");
                link.setOnAction(e-> link.getScene().setRoot(new Profile(this.online_user,this).get_page_layout(friend)));
                ov_friends.add(link);
                index++;
            }
            ListView<Hyperlink> lst_friends = new ListView<>();
            lst_friends.getStyleClass().add("friends");
            lst_friends.setOrientation(Orientation.HORIZONTAL);
            lst_friends.setItems(ov_friends);
            friends_container.add(lst_friends,0,1,2,1);
        }
        else{
            Label lbl_null_friends = new Label();
            if(user == this.online_user)lbl_null_friends.setText("Your friend list is still empty.");
            else lbl_null_friends.setText(user.getFirstName()+"'s friend list is still empty.");
            lbl_null_friends.setStyle("-fx-alignment: center;-fx-font-weight: bold");
            friends_container.add(lbl_null_friends,0,1,2,1);
        }
        container.getChildren().add(friends_container);

        GridPane groups_container = new GridPane();
        groups_container.setVgap(10);
        groups_container.setHgap(15);
        groups_container.getStyleClass().add("friends_container");

        Label lbl_groups = new Label();
        lbl_groups.setText("Groups ("+user.getNoGroups()+")");
        lbl_groups.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        groups_container.add(lbl_groups,0,0);

        if(user.getNoGroups() != 0) {
            String[] colors = {"grey","lightblue"};
            ObservableList<Hyperlink> ov_groups = FXCollections.observableArrayList();
            int index = 0;
            for (Group group : user.getGroups()) {
                Hyperlink link = new Hyperlink(group.getName());
                String color = colors[index % 2];
                link.getStyleClass().add("friend_in_list");
                link.setStyle("-fx-background-color: " + color + ";");
                link.setOnAction(e-> link.getScene().setRoot(new GroupPage(group,this).get_page_layout(this.online_user)));
                ov_groups.add(link);
                index++;
            }
            ListView<Hyperlink> lst_groups = new ListView<>();
            lst_groups.getStyleClass().add("friends");
            lst_groups.setOrientation(Orientation.HORIZONTAL);
            lst_groups.setItems(ov_groups);
            groups_container.add(lst_groups,0,1,2,1);

            if(user == this.online_user) {
                Button btn_create_group = new Button("Create new group");
                btn_create_group.setStyle("-fx-alignment: center;");
                btn_create_group.setOnAction(e -> {
                    Group new_group = CreateGroup.display("Create group", this.online_user);
                    if (new_group != null) {
                        btn_create_group.getScene().setRoot(new GroupPage(new_group,this).get_page_layout(this.online_user));
                    }
                });
                groups_container.add(btn_create_group,0,2);
            }
        }
        else{
            Label lbl_null_groups = new Label();
            if(user == this.online_user)lbl_null_groups.setText("You haven't joined any groups yet.");
            else lbl_null_groups.setText(user.getFirstName()+" hasn't joined any groups yet.");
            lbl_null_groups.setStyle("-fx-alignment: center;-fx-font-weight: bold");
            groups_container.add(lbl_null_groups,0,1,2,1);

            if(user == this.online_user) {
                Button btn_create_group = new Button("Create new group");
                btn_create_group.setStyle("-fx-alignment: center");
                btn_create_group.setOnAction(e -> {
                    Group new_group = CreateGroup.display("Create group", this.online_user);
                    if (new_group != null) {
                        btn_create_group.getScene().setRoot(new GroupPage(new_group,this).get_page_layout(this.online_user));
                    }
                });
                groups_container.add(btn_create_group,0,2,2,1);
            }
        }
        container.getChildren().add(groups_container);


        if(user == this.online_user) {
            GridPane user_recommendation_container = new GridPane();
            user_recommendation_container.setVgap(10);
            user_recommendation_container.setHgap(15);
            user_recommendation_container.getStyleClass().add("friends_container");

            Label lbl_user_recommendations = new Label();
            lbl_user_recommendations.setText("People you may know");
            lbl_user_recommendations.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
            user_recommendation_container.add(lbl_user_recommendations, 0, 0);

            ArrayList<User> recommended_friends = new ArrayList<>();
            ArrayList<Integer> mutual_friends = new ArrayList<>();
            get_friends_recommendations(user, recommended_friends, mutual_friends);

            lbl_user_recommendations.setText(String.format("%s (%d)",lbl_user_recommendations.getText(),recommended_friends.size()));

            if (recommended_friends.size() != 0) {
                String[] colors = {"grey", "lightblue"};
                ObservableList<Hyperlink> ov_friends = FXCollections.observableArrayList();
                int index = 0;
                for (User friend : recommended_friends) {
                    Hyperlink link = new Hyperlink();
                    int mutual = mutual_friends.get(index);
                    if(mutual == 1)link.setText(friend.getName() + "\n(one mutual friend)");
                    else link.setText(friend.getName() + "\n(" + mutual_friends.get(index) + " mutual friends)");
                    String color = colors[index % 2];
                    link.getStyleClass().add("friend_in_list");
                    link.setStyle("-fx-background-color: " + color + ";");
                    link.setOnAction(e -> link.getScene().setRoot(new Profile(this.online_user, this).get_page_layout(friend)));
                    ov_friends.add(link);
                    index++;
                }
                ListView<Hyperlink> lst_friends = new ListView<>();
                lst_friends.getStyleClass().add("friends");
                lst_friends.setOrientation(Orientation.HORIZONTAL);
                lst_friends.setItems(ov_friends);
                user_recommendation_container.add(lst_friends, 0, 1, 2, 1);
                container.getChildren().add(user_recommendation_container);
            }

            GridPane group_recommendation_container = new GridPane();
            group_recommendation_container.setVgap(10);
            group_recommendation_container.setHgap(15);
            group_recommendation_container.getStyleClass().add("friends_container");

            Label lbl_group_recommendations = new Label();
            lbl_group_recommendations.setText("Groups you may be interested");
            lbl_group_recommendations.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
            group_recommendation_container.add(lbl_group_recommendations, 0, 0);

            ArrayList<Group> recommended_groups = new ArrayList<>();
            mutual_friends = new ArrayList<>();
            get_groups_recommendations(user, recommended_groups, mutual_friends);
            lbl_group_recommendations.setText(lbl_group_recommendations.getText()+" ("+recommended_groups.size()+")");

            if (recommended_groups.size() != 0) {
                String[] colors = {"grey", "lightblue"};
                ObservableList<Hyperlink> ov_groups = FXCollections.observableArrayList();
                int index = 0;
                for (Group group : recommended_groups) {
                    Hyperlink link = new Hyperlink();
                    int mutual = mutual_friends.get(index);
                    if(mutual == 1)link.setText(group.getName() + "\n(one friend)");
                    else link.setText(group.getName() + "\n(" + mutual_friends.get(index) + " friends)");
                    String color = colors[index % 2];
                    link.getStyleClass().add("friend_in_list");
                    link.setStyle("-fx-background-color: " + color + ";");
                    link.setOnAction(e -> link.getScene().setRoot(new GroupPage(group, this).get_page_layout(this.online_user)));
                    ov_groups.add(link);
                    index++;
                }
                ListView<Hyperlink> lst_friends = new ListView<>();
                lst_friends.getStyleClass().add("friends");
                lst_friends.setOrientation(Orientation.HORIZONTAL);
                lst_friends.setItems(ov_groups);
                group_recommendation_container.add(lst_friends, 0, 1, 2, 1);
                container.getChildren().add(group_recommendation_container);
            }
        }
        return container;
    }
}
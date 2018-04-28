package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

import static sample.Interface.*;
import static sample.Profile.*;
import static sample.User.*;
import static sample.Group.*;

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
        layout.setCenter(btn_return);
        return layout;
    }
}

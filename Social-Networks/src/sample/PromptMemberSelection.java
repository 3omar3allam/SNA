package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PromptMemberSelection {
    static int answer = 0;
    public static int display(String title,User member,Group group){
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        AnchorPane container = new AnchorPane();
        container.setMinWidth(250);
        container.setMinHeight(100);
        Button btn_visit = new Button(String.format("Visit %s", member.getFirstName()));
        Button btn_remove = new Button(String.format("Remove %s", member.getFirstName()));
        if(member == group.getAdmin()) btn_remove.setDisable(true);
        AnchorPane.setLeftAnchor(btn_visit,15.0);
        AnchorPane.setRightAnchor(btn_remove, 15.0);
        AnchorPane.setTopAnchor(btn_visit,35.0);
        AnchorPane.setTopAnchor(btn_remove,35.0);
        container.getChildren().addAll(btn_visit,btn_remove);

        btn_visit.setOnAction(e->{
            answer = 1;
            window.close();
        });
        btn_remove.setOnAction(e->{
            answer = 2;
            window.close();
        });
        window.setOnCloseRequest(e->answer = 0);

        window.setScene(new Scene(container));
        window.showAndWait();
        return answer;
    }
}

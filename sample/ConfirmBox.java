package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    private static int answer;

    public static int display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        VBox scene_layout = new VBox(20);
        scene_layout.setPadding(new Insets(25));
        scene_layout.setAlignment(Pos.CENTER);
        Label label = new Label(message);

        HBox buttons_layout = new HBox(20);
        Button btn_yes = new Button("save and close");
        Button btn_no = new Button("close without saving");
        Button btn_cancel = new Button("cancel");
        buttons_layout.getChildren().addAll(btn_yes,btn_no,btn_cancel);

        scene_layout.getChildren().addAll(label,buttons_layout);

        btn_yes.setOnAction( e-> {
            answer = 1;
            window.close();
        });
        btn_no.setOnAction( e-> {
            answer = 0;
            window.close();
        });
        btn_cancel.setOnAction( e-> {
            answer = -1;
            window.close();
        });

        window.setOnCloseRequest( e-> answer = -1);

        Scene scene = new Scene(scene_layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}

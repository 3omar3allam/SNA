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

public class ConfirmDelete {
    static boolean answer;
    public static boolean display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        VBox scene_layout = new VBox(20);
        scene_layout.setPadding(new Insets(25));
        scene_layout.setAlignment(Pos.CENTER);
        Label label = new Label(message);

        HBox buttons_layout = new HBox(60);
        Button btn_yes = new Button("Yes");
        Button btn_no = new Button("No");
        buttons_layout.getChildren().addAll(btn_yes,btn_no);
        buttons_layout.setAlignment(Pos.CENTER);
        scene_layout.getChildren().addAll(label,buttons_layout);

        btn_yes.setOnAction( e-> {
            answer = true;
            window.close();
        });
        btn_no.setOnAction( e-> {
            answer = false;
            window.close();
        });
        window.setOnCloseRequest( e-> answer = false);

        Scene scene = new Scene(scene_layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}

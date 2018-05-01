package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

abstract public class Page {
    abstract Pane get_page_layout();
    abstract Pane get_page_layout(User user);
}

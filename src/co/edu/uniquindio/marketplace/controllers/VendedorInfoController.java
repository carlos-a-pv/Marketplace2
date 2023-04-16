package co.edu.uniquindio.marketplace.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VendedorInfoController {
    @FXML
    private Pane fotoUsuario;
    @FXML
    void initialize(){
        Image img = new Image("/resources/hacia-atras.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);

    }
}

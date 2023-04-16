package co.edu.uniquindio.marketplace.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdministradorViewController {
    ModelFactoryController modelFactoryController;
    @FXML
    private Button btnVolver;
    @FXML
    void initialize() {
        Image img = new Image("/resources/hacia-atras.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        btnVolver.setGraphic(view);
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public void OnVolverClick(ActionEvent actionEvent) {
    }

    public void onCrearVendedorClick(ActionEvent actionEvent) {
    }

    public void onBuscarClick(ActionEvent actionEvent) {
    }
}

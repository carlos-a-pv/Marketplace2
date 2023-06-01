package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Comentario;
import co.edu.uniquindio.marketplace.model.Vendedor;
import com.sun.webkit.Timer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ChatViewController {
    ModelFactoryController modelFactoryController;
    @FXML
    private TableView tbAmigos;
    @FXML
    private TableColumn<Vendedor, String> colNombre;
    @FXML
    private Label lbNombreChat;
    @FXML
    private TextField tfMensaje;
    @FXML
    private Button btnEnviar;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        Image img = new Image("/resources/enviar-mensaje.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        view.setPreserveRatio(true);
        btnEnviar.setGraphic(view);

        llenarTabla(modelFactoryController.getVendedorLogeado().getVendedoresAliados());
    }
    public void onClickEnviar(ActionEvent actionEvent) {
    }

    public void llenarTabla(List<Vendedor> amigos) {
        tbAmigos.setItems(FXCollections.observableArrayList(amigos));
        tbAmigos.refresh();
    }
}

package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VendedorInfoController {
    ModelFactoryController modelFactoryController;
    Vendedor vendedorSeleccionado;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfCedula;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPassword;
    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        vendedorSeleccionado = modelFactoryController.getMarketplace().getVendedorSeleccionado();
        tfNombre.setText(vendedorSeleccionado.getNombre());
        tfApellido.setText(vendedorSeleccionado.getApellido());
        tfCedula.setText(vendedorSeleccionado.getCedula());
        tfDireccion.setText(vendedorSeleccionado.getDireccion());
        tfUser.setText(vendedorSeleccionado.getUser().getNombre());
        tfPassword.setText(vendedorSeleccionado.getContra());


    }

    public void onEliminarVendedorClick(ActionEvent actionEvent) {
    }

    public void onActualizarVendedorClick(ActionEvent actionEvent) {
    }
}

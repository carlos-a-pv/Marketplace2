package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Optional;

public class ProductoInfoController {

    ModelFactoryController modelFactoryController;
    CrudProductoViewController crudProductoViewController;
    Producto productoSeleccionado;
    Vendedor vendedorSeleccionado;
    Vendedor vendedorLogeado;

    @FXML
    private Pane contentImgProduct;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfCategory;
    @FXML
    private TextField tfDisponibility;
    @FXML
    private Button btnRemove;

    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        crudProductoViewController = new CrudProductoViewController(modelFactoryController);
        productoSeleccionado = modelFactoryController.getMarketplace().getProductoSeleccionado();
        vendedorSeleccionado = modelFactoryController.getVendedorSeleccionado();
        vendedorLogeado = modelFactoryController.getVendedorLogeado();
        tfName.setText(productoSeleccionado.getNombre());
        tfPrice.setText(productoSeleccionado.getPrecio());
        tfCategory.setText(String.valueOf(productoSeleccionado.getCategoria()));
        tfDisponibility.setText(String.valueOf(productoSeleccionado.getEstado()));

        Image img = new Image("/resources/producto.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(200);
        view.setFitWidth(200);
        view.setPreserveRatio(true);
        contentImgProduct.getChildren().add(view);
    }


    public void onEliminarClick(ActionEvent actionEvent) {
        if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar el producto?") == true){
            if(crudProductoViewController.eliminarProducto(productoSeleccionado.getIdProducto())){
                mostrarMensaje("INFO","Eliminación","se ha eliminado el producto", Alert.AlertType.INFORMATION);
                Stage stage = new Stage();
                stage.initOwner(btnRemove.getScene().getWindow());
                btnRemove.getScene().getWindow().hide();
            }else{

            }
        }
    }
    public void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
    private boolean mostrarMensajeConfirmacion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();

        if (((Optional<?>) action).get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}

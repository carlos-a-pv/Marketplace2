
package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Categoria;
import co.edu.uniquindio.marketplace.model.Disponibilidad;
import co.edu.uniquindio.marketplace.model.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormularioProductoViewController {
    ModelFactoryController modelFactoryController;
    CrudProductoViewController crudProductoViewController;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPrecio;
    @FXML
    private Button btnPublicar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        crudProductoViewController = new CrudProductoViewController(modelFactoryController);
        cbCategoria.getItems().setAll(Categoria.values());

    }


    public void onPublicarClick(ActionEvent actionEvent) throws IOException {
        String nombre = tfNombre.getText();
        String precio = tfPrecio.getText();
        Categoria categoria = cbCategoria.getValue();

        if(validarDatos(nombre, precio, categoria)){
            Producto producto = crudProductoViewController.crearProducto(nombre, precio, categoria);
            if(producto != null){
                mostrarMensaje("Creacion de producto", "Operacion existosa", "se ha creado el prodcuto con exito", Alert.AlertType.CONFIRMATION);
            }
        }else{
            mostrarMensaje("Datos invalidos", "Error en los datos","llene todos los campos", Alert.AlertType.INFORMATION);
        }

        Stage stage = new Stage();
        stage.initOwner(btnPublicar.getScene().getWindow());
        btnPublicar.getScene().getWindow().hide();
    }

    private boolean validarDatos(String nombre, String precio, Categoria categoria) {
        String mensaje = "";

        if(nombre == null || nombre.equals(""))
            mensaje += "El nombre es invalido \n" ;

        if(precio == null || precio.equals(""))
            mensaje += "El precio es es invalido \n" ;

        if(categoria == null){
            mensaje += "La categoria es invalida \n";
        }

        if(mensaje.equals("")){
            return true;
        }else{
            //mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }

    }

    public void onCancelarClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initOwner(btnCancelar.getScene().getWindow());
        btnCancelar.getScene().getWindow().hide();

    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}

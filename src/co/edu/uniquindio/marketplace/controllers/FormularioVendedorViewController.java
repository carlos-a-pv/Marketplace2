package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormularioVendedorViewController {
    ModelFactoryController modelFactoryController;
    CrudVendedorViewController crudVendedorViewController;
    AdministradorViewController administradorViewController;

    @FXML
    private Button btnCrearVendedor;
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

    Vendedor vendedorCreado;

    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        crudVendedorViewController = new CrudVendedorViewController(modelFactoryController);
        administradorViewController = new AdministradorViewController(modelFactoryController);
    }
    @FXML
    public void onCrearVendedorClick(ActionEvent actionEvent) throws IOException {
        String nombre = tfNombre.getText();
        String apellido = tfApellido.getText();
        String cedula = tfCedula.getText();
        String direccion = tfDireccion.getText();
        String user = tfUser.getText();
        String password = tfPassword.getText();

        if(datosValidos(nombre, apellido, cedula, direccion, user, password)== true){
            Vendedor vendedor = null;
            vendedor = crudVendedorViewController.crearVendedor(nombre,apellido,cedula,direccion, user, password);
            //administradorViewController.llenarTabla(modelFactoryController.getMarketplace().getVendedores());
            if(vendedor != null){
                //administradorViewController.listaVendedoresData.add(vendedor);
                //vendedorCreado = vendedor;
                //administradorViewController.getListaVendedoresData().add(vendedor);
                modelFactoryController.getMarketplace().getVendedores().add(vendedor);
                //administradorViewController.getTbVendedores().refresh();
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                //limpiarCamposEmpleado();
            }else{
                vendedorCreado = null;
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.INFORMATION);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

        //FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/co/edu/uniquindio/marketplace/views/administrador-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        Stage stage = new Stage();
       // stage.setTitle("ADMINISTRADOR");
       //stage.setScene(scene);
        stage.initOwner(btnCrearVendedor.getScene().getWindow());
        btnCrearVendedor.getScene().getWindow().hide();
        //stage.show();
        //administradorViewController.llenarTabla(crudVendedorViewController.obtenerVendedores());
        //administradorViewController.llenarTabla(crudVendedorViewController.obtenerVendedores());
        //stage.show();

    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfCedula.setText("");
        tfDireccion.setText("");
        tfUser.setText("");
        tfPassword.setText("");
    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean datosValidos(String nombre, String apellido, String cedula,  String direccion, String user, String password) {

        String mensaje = "";


        if(nombre == null || nombre.equals(""))
            mensaje += "El nombre es invalido \n" ;

        if(apellido == null || apellido.equals(""))
            mensaje += "El apellido es invalido \n" ;

        if(cedula == null || cedula.equals(""))
            mensaje += "El documento es invalido \n" ;

        if(direccion == null || direccion.equals(""))
            mensaje += "La direccion es invalida \n" ;

        if(user == null || direccion.equals(""))
            mensaje += "La usuario es invalida \n" ;

        if(password == null || direccion.equals(""))
            mensaje += "La contraseña es invalida \n" ;


        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }
    public Vendedor getVendedorCreado(){
        return this.vendedorCreado;
    }

}

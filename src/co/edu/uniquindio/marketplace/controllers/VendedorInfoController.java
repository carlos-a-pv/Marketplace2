package co.edu.uniquindio.marketplace.controllers;

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

import java.util.Optional;

public class VendedorInfoController {
    ModelFactoryController modelFactoryController;
    CrudVendedorViewController crudVendedorViewController;
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
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;


    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        crudVendedorViewController = new CrudVendedorViewController(modelFactoryController);
        vendedorSeleccionado = modelFactoryController.getMarketplace().getVendedorSeleccionado();
        tfNombre.setText(vendedorSeleccionado.getNombre());
        tfApellido.setText(vendedorSeleccionado.getApellido());
        tfCedula.setText(vendedorSeleccionado.getCedula());
        tfDireccion.setText(vendedorSeleccionado.getDireccion());
        tfUser.setText(vendedorSeleccionado.getUser().getNombre());
        tfPassword.setText(vendedorSeleccionado.getContra());
    }
    public void onEliminarVendedorClick(ActionEvent actionEvent) {
        eliminarEmpleado();

    }
    private void eliminarEmpleado(){

        boolean vendedorEliminado = false;
        if(vendedorSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al empleado?") == true){
                vendedorEliminado = crudVendedorViewController.eliminarVendedor(vendedorSeleccionado.getCedula());
                if(vendedorEliminado == true){
                    modelFactoryController.getMarketplace().getVendedores().remove(vendedorSeleccionado);
                    modelFactoryController.registrarAccionesSistema("Se ha eliminado el usuario con cedula"+vendedorSeleccionado.getCedula(), 1,"Elimininacion vendedor");
                    vendedorSeleccionado = null;
                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
        }
        Stage stage = new Stage();
        stage.initOwner(btnEliminar.getScene().getWindow());
        btnEliminar.getScene().getWindow().hide();
    }
    @FXML
    public void onActualizarVendedorClick(ActionEvent actionEvent) {
        actualizarVendedor();
    }
    private void actualizarVendedor() {
        //1. Capturar los datos
        String nombre = tfNombre.getText();
        String apellido = tfApellido.getText();
        String cedula = tfCedula.getText();
        String direccion = tfDireccion.getText();
        String user = tfUser.getText();
        String password = tfPassword.getText();
        boolean vendedorActualizado = false;

        //2. verificar el empleado seleccionado
        if(vendedorSeleccionado != null){
            //3. Validar la información
            if(datosValidos(nombre, apellido, cedula, direccion, user, password)== true){

                vendedorActualizado = crudVendedorViewController.actualizarVendedor(vendedorSeleccionado.getCedula(), nombre, apellido, cedula, direccion, user, password);

                if(vendedorActualizado == true){
                    //tableEmpleados.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    modelFactoryController.registrarAccionesSistema("Se ha actualizado el vendedor con cedula"+vendedorSeleccionado.getCedula(), 1, "Actualizacion vendedor");
                    //limpiarCamposEmpleado();
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
        Stage stage = new Stage();
        stage.initOwner(btnActualizar.getScene().getWindow());
        btnActualizar.getScene().getWindow().hide();
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
}

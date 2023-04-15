package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.Administrador;
import co.edu.uniquindio.marketplace.model.Empleado;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginViewController {

    ModelFactoryController modelFactoryController;
    @FXML
    private javafx.scene.control.TextField tfUser;
    @FXML
    private javafx.scene.control.TextField tfPassword;

    @FXML
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    public void onLoginClick() throws IOException {
        //1. Capturar los datos
        String user = tfUser.getText();
        String password = tfPassword.getText();

        if (datosValidos(user, password) == true){
        //2. Validar la información
            Empleado empleadoIniciado = modelFactoryController.marketplace.autenticar(user, password);

            if (empleadoIniciado instanceof Administrador){
                Parent parent = FXMLLoader.load(MainApp.class.getResource("administrador-view.fxml"));
                Scene scene = new Scene(parent, 900, 700);
                Stage stage = new Stage();
                stage.setTitle("ADMINISTRADOR");
                stage.setScene(scene);
                stage.show();
            }else if(empleadoIniciado instanceof Vendedor){

            }else{
                tfUser.setText("");
                tfPassword.setText("");
            }
        }
    }

    private boolean datosValidos(String user, String password) {
        String mensaje = "";

        if(user == null || user.equals(""))
            mensaje += "El usuario es invalido \n" ;

        if(password == null || password.equals(""))
            mensaje += "La contraseña es invalida \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
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
}

package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Comentario;
import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;

public class CommentViewController {
    ModelFactoryController modelFactoryController;
    Vendedor vendedorLogeado;
    Producto productoSeleccionado;

    @FXML
    private TableView tbComentarios;
    @FXML
    private TableColumn<Comentario, String> colFecha;
    @FXML
    private TableColumn<Comentario, String> colAutor;
    @FXML
    private TableColumn<Comentario, String> colComentario;


    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();

        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        llenarTabla(modelFactoryController.getMarketplace().getProductoSeleccionado().getComentarios());
        tbComentarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
        });

        vendedorLogeado = modelFactoryController.getVendedorLogeado();
        productoSeleccionado = modelFactoryController.getMarketplace().getProductoSeleccionado();
    }

    public void llenarTabla(List<Comentario> comentarios) {
        tbComentarios.setItems(FXCollections.observableArrayList(comentarios));
        tbComentarios.refresh();
    }

    public void onComentarClick(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        dialog.setTitle("Nuevo campo de texto");
        dialog.setMaxWidth(400);
        dialog.setMaxHeight(400);

        TextField textField = new TextField("Ingrese su texto aquí");
        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        HBox botones = new HBox(btnGuardar, btnCancelar);
        botones.setSpacing(25);

        // Agregar el campo de texto a la ventana modal
        VBox vbox = new VBox(textField, botones);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        dialog.setScene(new Scene(vbox));

        // Mostrar la ventana modal
        dialog.show();

        //
        btnCancelar.setOnMouseClicked(event1 -> {
            dialog.close();
        });

        btnGuardar.setOnMouseClicked(event -> {
            String mensaje = textField.getText();

            modelFactoryController.getMarketplace().getProductoSeleccionado().getComentarios()
                    .add(new Comentario(cargarFechaSistema(),vendedorLogeado.getNombre(),mensaje));

            dialog.close();

            llenarTabla(modelFactoryController.getMarketplace().getProductoSeleccionado().getComentarios());
        });
    }

    private String cargarFechaSistema() {

        String diaN = "";
        String mesN = "";
        String añoN = "";

        Calendar cal1 = Calendar.getInstance();
        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
        int año = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR);
        int minuto = cal1.get(Calendar.MINUTE);


        if(dia < 10){
            diaN+="0"+dia;
        }
        else{
            diaN+=""+dia;
        }
        if(mes < 10){
            mesN+="0"+mes;
        }
        else{
            mesN+=""+mes;
        }
        return hora+":"+minuto+" "+año+"-"+mesN+"-"+diaN;
    }

}

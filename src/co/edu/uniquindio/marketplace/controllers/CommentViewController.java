package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Comentario;
import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CommentViewController {
    ModelFactoryController modelFactoryController;

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
    }

    public void llenarTabla(List<Comentario> comentarios) {
        tbComentarios.setItems(FXCollections.observableArrayList(comentarios));
        tbComentarios.refresh();
    }
}

package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Estado;
import co.edu.uniquindio.marketplace.model.Solicitud;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SolicitudViewController {
    ModelFactoryController modelFactoryController;
    ObservableList<Solicitud> listaSolicitudesEnviadasData = FXCollections.observableArrayList();
    ObservableList<Solicitud> listaSolicitudesRecibidasData = FXCollections.observableArrayList();
    ObservableList<Vendedor> listaVendedoresAliadosData = FXCollections.observableArrayList();
    Solicitud solicitudSeleccionada;
    @FXML
    private Tab tabEnviadas;
    @FXML
    private Tab tabRecibidas;
    @FXML
    private TableView tbEnviadas;
    @FXML
    private TableView tbRecibidas;
    @FXML
    private TableColumn<Vendedor, String> colUserRecibidas;
    @FXML
    private TableColumn<Vendedor, String> colUserEnviadas;
    @FXML
    private TableColumn<Vendedor, Estado> colEstadoRecibidas;
    @FXML
    private TableColumn<Vendedor,Estado> colEstadoEnviadas;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnRechazar;
    @FXML
    private TableView tbVendedoresAliados;
    @FXML
    private TableColumn<Vendedor, String> colNombre;
    @FXML
    private TableColumn<Vendedor, String> colApellido;
    @FXML
    private Button btnEliminarAmigo;

    @FXML
    void initialize( ){
        modelFactoryController = modelFactoryController.getInstance();
        tbEnviadas.setItems(getListaSolicitudesEnviadasData());
        tbRecibidas.setItems(getListaSolicitudesRecibidasData());
        tbVendedoresAliados.setItems(getListaVendedoresAliadosData());
        this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.colUserEnviadas.setCellValueFactory(new PropertyValueFactory<>("receptor"));
        this.colEstadoEnviadas.setCellValueFactory(new PropertyValueFactory<>("estado"));
        this.colUserRecibidas.setCellValueFactory(new PropertyValueFactory<>("emisor"));
        this.colEstadoRecibidas.setCellValueFactory(new PropertyValueFactory<>("estado"));

        Image img = new Image("/resources/aceptar.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setPreserveRatio(true);
        btnAceptar.setGraphic(view);

        Image img1 = new Image("/resources/rechazar.png");
        ImageView view1 = new ImageView(img1);
        view1.setFitHeight(27);
        view1.setPreserveRatio(true);
        btnRechazar.setGraphic(view1);

        Image img2 = new Image("/resources/eliminar.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(27);
        view2.setPreserveRatio(true);
        btnEliminarAmigo.setGraphic(view2);


        tbRecibidas.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newValue) -> {
            solicitudSeleccionada = (Solicitud) newValue;
        });
    }
    public void onAceptarSolicitudClick(ActionEvent actionEvent) {
        Solicitud solicitud = null;
        if(solicitudSeleccionada != null){
            solicitud = modelFactoryController.aceptarSolicitud(solicitudSeleccionada);
        }else{
            modelFactoryController.mostrarMensaje("ADVERTENCIA","SELECCION DE SOLICITUD","Seleccione una solicitud!", Alert.AlertType.INFORMATION);
        }

        if(solicitud != null){
            modelFactoryController.getVendedorLogeado().getVendedoresAliados().add(solicitud.getEmisor());
            solicitud.getEmisor().getVendedoresAliados().add(solicitud.getReceptor());
            modelFactoryController.mostrarMensaje("INFO","Solicitud aceptada","", Alert.AlertType.CONFIRMATION);
        }

        tbVendedoresAliados.getItems().clear();
        tbVendedoresAliados.setItems(getListaVendedoresAliadosData());
        tbVendedoresAliados.refresh();

        tbRecibidas.getItems().clear();
        tbRecibidas.setItems(getListaSolicitudesRecibidasData());
        tbRecibidas.refresh();
    }

    public void onRechazarSolicitudClick(ActionEvent actionEvent) {
    }
    public ObservableList<Solicitud> getListaSolicitudesEnviadasData() {
        listaSolicitudesEnviadasData.addAll(modelFactoryController.getVendedorLogeado().getSolicitudesEnviadas());
        return listaSolicitudesEnviadasData;
    }
    public ObservableList<Solicitud> getListaSolicitudesRecibidasData() {
        listaSolicitudesRecibidasData.addAll(modelFactoryController.getVendedorLogeado().getSolicitudesRecibidas());
        return listaSolicitudesRecibidasData;
    }
    public ObservableList<Vendedor> getListaVendedoresAliadosData() {
        listaVendedoresAliadosData.addAll(modelFactoryController.getVendedorLogeado().getVendedoresAliados());
        return listaVendedoresAliadosData;
    }
}

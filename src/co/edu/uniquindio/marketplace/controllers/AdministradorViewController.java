package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.Usuario;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class
AdministradorViewController {
    ModelFactoryController modelFactoryController;
    CrudVendedorViewController crudVendedorViewController;
        ObservableList<Vendedor> listaVendedoresData = FXCollections.observableArrayList();

    public AdministradorViewController( ){}
    public AdministradorViewController( ModelFactoryController modelFactoryController) {
        this.modelFactoryController = modelFactoryController;
    }

    @FXML
    private Button btnVolver;
    @FXML
    private TableView tbVendedores;
    @FXML
    private TableColumn<Vendedor, String> colNombre;
    @FXML
    private TableColumn<Vendedor, String> colApellido;
    @FXML
    private TableColumn<Vendedor, String> colCedula;
    @FXML
    private TableColumn<Vendedor, String> colDireccion;
    @FXML
    private TableColumn<Vendedor, String> colUser;
    @FXML
    private TableColumn<Vendedor, String> colPassword;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearVendedor;

    Vendedor vendedorSeleccionado;


    @FXML
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        crudVendedorViewController = new CrudVendedorViewController(modelFactoryController);
        //tbVendedores.getItems().clear();
        tbVendedores.setItems(getListaVendedoresData());
        this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory<>("contra"));
        //---------------------------------------------------------------------------
        tbVendedores.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        abrirVentanaInfo((Vendedor) newValue);
                        tbVendedores.getSelectionModel().clearSelection();
                    } catch (IOException e) {
                        modelFactoryController.registrarAccionesSistema("Seleccion de usuaario", 1, "Seleccion de usuario");
                        //throw new RuntimeException(e);
                    }
                    //tbVendedores.getSelectionModel().clearSelection();
                });
        //-----------------------------------------------------------------------------
        Image img = new Image("/resources/hacia-atras.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        btnVolver.setGraphic(view);
    }

    @FXML
    public void OnVolverClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/co/edu/uniquindio/marketplace/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        Stage stage = new Stage();
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.initOwner(btnVolver.getScene().getWindow());
        btnVolver.getScene().getWindow().hide();
        stage.show();

        modelFactoryController.registrarAccionesSistema("Se ha cerrado la sesion",1,"Cierre de la sesion");
    }
    @FXML
    public void onCrearVendedorClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/formulario-vendedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        Stage stage = new Stage();
        stage.setTitle("CREAR VENDEDOR");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tbVendedores.getItems().clear();
        tbVendedores.setItems(getListaVendedoresData());
        tbVendedores.refresh();

    }

    public void onBuscarClick(ActionEvent actionEvent) {
    }
    public void llenarTabla(List<Vendedor> vendedores) {
        tbVendedores.setItems(FXCollections.observableArrayList(vendedores));
        tbVendedores.refresh();
    }
    @FXML
    private void abrirVentanaInfo(Vendedor vendedor) throws IOException {
         modelFactoryController.getMarketplace().setVendedorSeleccionado(vendedor);
        if (vendedor != null) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/vendedor-info.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 350);
            Stage stage = new Stage();
            stage.setTitle("INFOR VENDEDOR");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tbVendedores.getItems().clear();
            tbVendedores.setItems(getListaVendedoresData());
            tbVendedores.refresh();
        }
        //tbVendedores.getSelectionModel().clearSelection();
        tbVendedores.getSelectionModel().clearSelection();
    }

    public ObservableList<Vendedor> getListaVendedoresData() {
        listaVendedoresData.addAll(crudVendedorViewController.obtenerVendedores());
        return listaVendedoresData;
    }
    public TableView getTbVendedores() {
        return tbVendedores;
    }


}

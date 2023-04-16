package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.Usuario;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdministradorViewController {
    ModelFactoryController modelFactoryController;
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
        //inicializar datos en el tabla
        llenarTabla(modelFactoryController.getMarketplace().getVendedores());
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        //-----------------------------------------------------------------------------
        Image img = new Image("/resources/hacia-atras.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        btnVolver.setGraphic(view);

    }

    public void OnVolverClick(ActionEvent actionEvent) {
    }

    public void onCrearVendedorClick(ActionEvent actionEvent) {
    }

    public void onBuscarClick(ActionEvent actionEvent) {
    }
    private void llenarTabla(List<Vendedor> vendedores) {
        tbVendedores.setItems(FXCollections.observableArrayList(vendedores));
        tbVendedores.refresh();
    }
    private void abrirVentanaInfo(Vendedor vendedor) throws IOException {
        modelFactoryController.getMarketplace().setVendedorSeleccionado(vendedor);
        if (vendedor != null) {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/co/edu/uniquindio/marketplace/views/vendedor-info.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 350);
            Stage stage = new Stage();
            stage.setTitle("INFORMARCION VENDEDOR");
            stage.setScene(scene);
            stage.show();
        }
    }

}

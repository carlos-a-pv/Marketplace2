package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class VendedorViewController {
    ModelFactoryController modelFactoryController;
    CrudProductoViewController crudProductoViewController;
    ObservableList<Producto> listaProductosData = FXCollections.observableArrayList();
    Vendedor vendedorSeleccionado;
    @FXML
    private TabPane tabPane;

    @FXML
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        crudProductoViewController = new CrudProductoViewController(modelFactoryController);
        Vendedor vendedorLogeado = modelFactoryController.getVendedorLogeado();

        /*Image img2 = new Image("/resources/hacia-atras.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(20);
        view2.setPreserveRatio(true);
        btnVolver1.setGraphic(view2);*/

        int cantidadVendedores = modelFactoryController.obtenerVendedores().size();
        for (int i=0; i<cantidadVendedores; i++){

            //Creaccion de componentes
            Tab tab = new Tab("Vendedor:"+(i+1));
            tab.setId(String.valueOf(i+1));
            VBox content = new VBox();
            Label nombre = new Label(modelFactoryController.obtenerVendedores().get(i).getNombre());
            ImageView fotoUsuario = new ImageView();
            Image image = new Image(getClass().getResourceAsStream("/resources/usuario.png"));
            Button btnCambiarImagen = new Button("Cambiar imagen");
            Button btnPublicar = new Button("publicar");
            Button btnEditar = new Button();
            Button btnVolver = new Button("");
            Button btnAddVendedor = new Button("Añadir a tus contactos");
            Button btnSolicitud = new Button();
            TableView<Producto> productos = new TableView<>();
            TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
            TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
            TableColumn<Producto, Categoria> colCategoria = new TableColumn<>("categoria");
            TableColumn<Producto, Disponibilidad> colEstado = new TableColumn<>("Estado");
            TableColumn<Producto, Disponibilidad> colId = new TableColumn<>("ID");
            TextArea descripcion2 = new TextArea(modelFactoryController.obtenerVendedores().get(i).getDescripcion());
            HBox hbox = new HBox(btnVolver,fotoUsuario, nombre,  descripcion2, btnEditar, btnSolicitud);


            //Estilos
            content.setPadding(new Insets(20,20,20,20));
            content.setAlignment(Pos.CENTER);
            fotoUsuario.setImage(image);
            content.setSpacing(30);
            hbox.setSpacing(30);
            btnCambiarImagen.setAlignment(Pos.BOTTOM_LEFT);
            descripcion2.setPrefWidth(400);
            descripcion2.setPrefHeight(500);
            descripcion2.setDisable(true);
            //VBox.setMargin(button, new Insets(10));
            //Setting
            productos.setItems(convert(modelFactoryController.obtenerVendedores().get(i).getProductos()));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

            productos.getColumns().addAll(colNombre,colPrecio, colCategoria,colEstado, colId);

            Image img = new Image("/resources/edit.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(20);
            view.setPreserveRatio(true);
            btnEditar.setGraphic(view);

            Image img1 = new Image("/resources/cerrar-sesion.png");
            ImageView view1 = new ImageView(img1);
            view1.setFitHeight(20);
            view1.setPreserveRatio(true);
            btnVolver.setGraphic(view1);

            Image img3 = new Image("/resources/amigos.png");
            ImageView view3 = new ImageView(img3);
            view3.setFitHeight(20);
            view3.setPreserveRatio(true);
            btnSolicitud.setGraphic(view3);

            //Add de componentes
            content.getChildren().addAll(hbox,btnAddVendedor,  btnCambiarImagen, productos, btnPublicar);
            tab.setContent(content);
            tabPane.getTabs().add(tab);

            //acciones
            btnPublicar.setOnMouseClicked((event -> {
                try {
                    publicarProducto();
                    productos.getItems().clear();
                    productos.setItems(getListaProductoData());
                    productos.refresh();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            btnVolver.setOnMouseClicked((event -> {
                try {
                    volverAtras(btnVolver);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            btnAddVendedor.setOnMouseClicked((event -> {
                if(modelFactoryController.buscarVendedorAliado(modelFactoryController.getVendedorLogeado(), vendedorSeleccionado)){
                    modelFactoryController.mostrarMensaje("INFO","Ya eres aliado de este vendedor","", Alert.AlertType.INFORMATION);
                    btnAddVendedor.setDisable(true);
                }else{
                    agregarVendedor();
                    mostrarMensaje("SOLICITUD DE AMISTAD","INFO","Se ha enviado la solictud", Alert.AlertType.INFORMATION);
                    btnAddVendedor.setText("Pendiente...");
                    btnAddVendedor.setDisable(true);
                }
            }));

            btnEditar.setOnMouseClicked( event ->{
                Stage dialog = new Stage();
                //dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Nuevo campo de texto");
                dialog.setMaxWidth(400);
                dialog.setMaxHeight(400);

                // Crear el campo de texto
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

                btnGuardar.setOnMouseClicked(event1 -> {
                    if(!textField.getText().isEmpty()){
                        descripcion2.setText(textField.getText());
                        dialog.close();
                    }
                });

            });

            btnSolicitud.setOnMouseClicked((event -> {
                try {
                    abrirVentanaSolicitud();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            if(!modelFactoryController.obtenerVendedores().get(i).equals(vendedorLogeado)){
                btnCambiarImagen.setDisable(true);
                btnEditar.setDisable(true);
                btnPublicar.setDisable(true);
                productos.setDisable(true);
                btnSolicitud.setDisable(true);
                btnVolver.setDisable(true);

            }else{
                btnAddVendedor.setVisible(false);
            }

            productos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                try {
                    abrirVentanaInfo(newValue);
                    //  productos.getSelectionModel().clearSelection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        }

        //Funcionalidad para posicionar el focus directamente en el vendedor logeado
        int indice = buscarVendedorLogeado(vendedorLogeado);
        tabPane.getSelectionModel().select(indice+1);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab.equals("tabMarketplace")){
                //SE HA SELECCIONADO EL TAB DEL MARKETPLACE
            }else {
                try {
                    int index = Integer.parseInt(newTab.getId())-1;
                    this.vendedorSeleccionado = modelFactoryController.obtenerVendedores().get(index);
                    System.out.println(vendedorSeleccionado.getNombre());
                } catch (NumberFormatException e) {

                }
            }
        });

    }

    private void agregarVendedor() {
        Solicitud newSolicitud = new Solicitud(modelFactoryController.getVendedorLogeado(),vendedorSeleccionado);
        vendedorSeleccionado.getSolicitudesRecibidas().add(newSolicitud);
        modelFactoryController.getVendedorLogeado().getSolicitudesEnviadas().add(newSolicitud);
    }

    private void abrirVentanaSolicitud() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/solicitudes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 420);
        Stage stage = new Stage();
        stage.setTitle("SOLICITUDES");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void volverAtras(Button btn) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/co/edu/uniquindio/marketplace/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        Stage stage = new Stage();
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.initOwner(btn.getScene().getWindow());
        btn.getScene().getWindow().hide();
        stage.show();
    }

    private void abrirVentanaInfo(Producto x) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/producto-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        Stage stage = new Stage();
        stage.setTitle("SOLICITUDES");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private void publicarProducto() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/formulario-producto.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        Stage stage = new Stage();
        stage.setTitle("CREAR PRODUCTO");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void OnCambiarFotoClick(ActionEvent actionEvent) {
    }

    public ObservableList<Producto> getListaProductoData() throws IOException {
        listaProductosData.addAll(crudProductoViewController.obtenerProductos());
        return listaProductosData;
    }

    public static <Prducto> ObservableList<Producto> convert(ArrayList<Producto> arrayList) {
        ObservableList<Producto> observableList = FXCollections.observableArrayList(arrayList);
        return observableList;

    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private int buscarVendedorLogeado(Vendedor vendedorLogeado){
        ArrayList<Vendedor> vendedores = modelFactoryController.obtenerVendedores();

        for(Vendedor vende : vendedores){
            if(vende.equals(vendedorLogeado)){
                return vendedores.indexOf(vende);
            }
        }
        return 0;
    }
}

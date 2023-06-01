package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.MainApp;
import co.edu.uniquindio.marketplace.model.*;
import co.edu.uniquindio.marketplace.persistence.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VendedorViewController {
    ModelFactoryController modelFactoryController;
    CrudProductoViewController crudProductoViewController;
    ObservableList<Producto> listaProductosData = FXCollections.observableArrayList();
    Vendedor vendedorSeleccionado;
    String idProducto;
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane contentAll;
    @FXML
    private Tab tabMarketplace;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contentBox;

    ArrayList<Producto> producCargados;
    ArrayList<Producto> productosVendedor;

    @FXML
    void initialize() throws IOException {
        modelFactoryController = ModelFactoryController.getInstance();
        crudProductoViewController = new CrudProductoViewController(modelFactoryController);
        Vendedor vendedorLogeado = modelFactoryController.getVendedorLogeado();

        /*Image img2 = new Image("/resources/hacia-atras.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(20);
        view2.setPreserveRatio(true);
        btnVolver1.setGraphic(view2);*/

        //INICIAR EL TAB DEL MARKETPLACE
        //contentBox.setPadding(new Insets(0, 0, 0, 0));
        contentBox.setSpacing(50);

        producCargados = modelFactoryController.obtenerProductos();
        if(producCargados != null){
            int cantidadProductos = producCargados.size();
            System.out.println(cantidadProductos);
            if(cantidadProductos != 0){
                for (int i = 0; i < cantidadProductos; i++) {
                    Producto producto = producCargados.get(i);
                    String infoProducto = producto.toString();
                    addNewItem(infoProducto, producCargados.get(i));
                }
            }
        }


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
            Button btnPublicar = new Button("PUBLICAR");
            Button btnEditar = new Button();
            Button btnVolver = new Button("");
            Button btnAddVendedor = new Button();
            Button btnSolicitud = new Button();
            Button btnChat = new Button();
            TableView<Producto> productos = new TableView<>();
            TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
            TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
            TableColumn<Producto, Categoria> colCategoria = new TableColumn<>("categoria");
            TableColumn<Producto, Disponibilidad> colEstado = new TableColumn<>("Estado");
            TableColumn<Producto, Disponibilidad> colId = new TableColumn<>("ID");
            TextArea descripcion2 = new TextArea(modelFactoryController.obtenerVendedores().get(i).getDescripcion());

            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(nombre, fotoUsuario, btnCambiarImagen);

            HBox hbox = new HBox(btnVolver, vBox,descripcion2,btnEditar, btnSolicitud, btnChat);


            //Estilos
            content.setPadding(new Insets(10,20,10,20));
            content.setAlignment(Pos.CENTER);
            fotoUsuario.setImage(image);
            //fotoUsuario.setFitHeight(100);
            //fotoUsuario.setFitWidth(100);
            content.setSpacing(15);
            hbox.setSpacing(30);
            btnCambiarImagen.setAlignment(Pos.BOTTOM_LEFT);
            descripcion2.setPrefWidth(400);
            descripcion2.setPrefHeight(350);
            descripcion2.setDisable(true);

            btnAddVendedor.setPrefWidth(50);
            btnAddVendedor.setPrefHeight(50);

            nombre.setPrefWidth(200);
            nombre.setFont(new Font(25));
            nombre.setAlignment(Pos.CENTER);
            nombre.setText(nombre.getText().toUpperCase());

            hbox.setPrefHeight(350);
            //content.setPrefHeight(700);

            contentAll.setTopAnchor(tabPane, 0.0);
            contentAll.setBottomAnchor(tabPane, 0.0);
            contentAll.setLeftAnchor(tabPane, 0.0);
            contentAll.setRightAnchor(tabPane, 0.0);
            //VBox.setMargin(button, new Insets(10));
            vBox.setSpacing(20);
            vBox.setAlignment(Pos.CENTER);

            btnPublicar.setStyle("-fx-min-width: 45px; -fx-min-height: 45px;");
            btnCambiarImagen.setStyle("-fx-min-width: 30px; -fx-min-height: 30;");
            btnPublicar.setFont(new Font(15));
            btnCambiarImagen.setFont(new Font(15));

            productos.setPrefHeight(350);
            //Setting

            if(modelFactoryController.getVendedorLogeado().equals(modelFactoryController.obtenerVendedores().get(i))){
                productos.setItems(getListaProductoData(modelFactoryController.obtenerVendedores().get(i)));
            }

            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

            productos.getColumns().addAll(colNombre,colPrecio, colCategoria,colEstado, colId);

            Image img = new Image("/resources/edit.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(30);
            view.setFitWidth(30);
            view.setPreserveRatio(true);
            btnEditar.setGraphic(view);
            btnEditar.setPrefHeight(30);
            btnEditar.setPrefWidth(30);

            Image img1 = new Image("/resources/cerrar-sesion.png");
            ImageView view1 = new ImageView(img1);
            view1.setFitHeight(30);
            view1.setFitWidth(30);
            view1.setPreserveRatio(true);
            btnVolver.setGraphic(view1);
            btnVolver.setPrefWidth(30);
            btnVolver.setPrefHeight(30);

            Image img3 = new Image("/resources/solicitud-de-amistad.png");
            ImageView view3 = new ImageView(img3);
            view3.setFitHeight(30);
            view3.setFitWidth(30);
            view3.setPreserveRatio(true);
            btnSolicitud.setGraphic(view3);
            btnSolicitud.setPrefHeight(30);
            btnSolicitud.setPrefWidth(30);


            Image img4 = new Image("/resources/agregar-usuario.png");
            ImageView view4 = new ImageView(img4);
            view4.setFitHeight(30);
            view4.setFitWidth(30);
            view4.setPreserveRatio(true);
            btnAddVendedor.setGraphic(view4);
            btnAddVendedor.setPrefWidth(30);
            btnAddVendedor.setPrefHeight(30);


            Image img5 = new Image("/resources/chat.png");
            ImageView view5 = new ImageView(img5);
            view5.setFitHeight(30);
            view5.setFitWidth(30);
            view5.setPreserveRatio(true);
            btnChat.setGraphic(view5);
            btnChat.setPrefHeight(30);
            btnChat.setPrefWidth(30);

            //Add de componentes
            content.getChildren().addAll(hbox,btnAddVendedor, productos, btnPublicar);
            tab.setContent(content);
            tabPane.getTabs().add(tab);

            //acciones
            btnPublicar.setOnMouseClicked((event -> {
                try {
                    publicarProducto();
                    productos.getItems().clear();
                    productos.setItems(getListaProductoData(modelFactoryController.getVendedorLogeado()));
                    productos.refresh();
                    refreshTab();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            btnVolver.setOnMouseClicked((event -> {
                try {
                    volverAtras(btnVolver);
                    modelFactoryController.guardarResourceXML();
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

            btnChat.setOnMouseClicked(event -> {
                try {
                    mostrarChat();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            if(!modelFactoryController.obtenerVendedores().get(i).equals(vendedorLogeado)){
                btnCambiarImagen.setDisable(true);
                btnEditar.setDisable(true);
                btnPublicar.setDisable(true);
                productos.setDisable(true);
                btnSolicitud.setDisable(true);
                btnVolver.setDisable(true);
                btnChat.setDisable(true);

            }else{
                btnAddVendedor.setVisible(false);
            }

            productos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                try {
                    abrirVentanaInfo(newValue);
                    productos.getItems().clear();
                    productos.setItems(getListaProductoData(modelFactoryController.getVendedorLogeado()));
                    productos.refresh();
                    refreshTab();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

            productosVendedor = null;
        }

        //Funcionalidad para posicionar el focus directamente en el vendedor logeado
        int indice = buscarVendedorLogeado(vendedorLogeado);
        tabPane.getSelectionModel().select(indice+1);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            try {
                int index = Integer.parseInt(newTab.getId())-1;
                this.vendedorSeleccionado = modelFactoryController.obtenerVendedores().get(index);
                modelFactoryController.setVendedorSeleccionado(vendedorSeleccionado);
                System.out.println(vendedorSeleccionado);
            } catch (NumberFormatException e) {

            }
        });

    }



    private void llenarTabla(Vendedor vendedor) throws IOException {
        ArrayList<Producto> productosCargados = modelFactoryController.obtenerProductos();
        /*if(productosCargados != null){
            productosVendedor = (ArrayList<Producto>) productosCargados.stream().filter(producto -> producto.getIdVendedor().equals(vendedor.getCedula())).collect(Collectors.toList());
        }*/
        vendedor.setProductos(productosVendedor);
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
        modelFactoryController.getMarketplace().setProductoSeleccionado(x);
        if (x != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/producto-info.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 350);
            Stage stage = new Stage();
            stage.setTitle("INFO PRODUCTO");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            refreshTab();
        }
    }
    private void mostrarChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 770, 520);
        Stage stage = new Stage();
        stage.setTitle("CHAT");
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

    public ObservableList<Producto> getListaProductoData(Vendedor vendedor) throws IOException {
        if(vendedor.getProductos() != null){
            listaProductosData.addAll(vendedor.getProductos());
            return listaProductosData;
        }
        return null;
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

    private void addNewItem(String info, Producto p) {
        Pane itemPane = createItemPane(info, p);
        contentBox.getChildren().add(itemPane);
    }
    private Pane createItemPane(String info, Producto p) {
        VBox itemPane = new VBox();
        itemPane.setAlignment(Pos.CENTER);
        itemPane.setPadding(new Insets(10));
        itemPane.setStyle("-fx-background-color: #b1d5e0");

        itemPane.setMinWidth(400); // Ancho mínimo del VBox
        itemPane.setMaxWidth(700); // Ancho máximo del VBox
        itemPane.setMinHeight(400); // Altura mínima del VBox
        itemPane.setMaxHeight(400   );

        HBox buttonContent = new HBox(10);
        buttonContent.setPadding(new Insets(10));
        //itemPane.setStyle("-fx-background-color: #42cbf5");

        HBox infoContent = new HBox(10);
        //infoContent.setPadding(new Insets(10));
        infoContent.setSpacing(75);
        //infoContent.setStyle("-fx-background-color: #42cbf5");

        buttonContent.setAlignment(Pos.CENTER);
        infoContent.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image("/resources/producto.png"));
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);

        imageView.setPreserveRatio(true);

        Label cantidadLikes = new Label(String.valueOf(p.getLikes()));
        cantidadLikes.setFont(new Font(18));
        Button likeButton = new Button();
        Image img = new Image("/resources/like.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setPreserveRatio(true);
        likeButton.setGraphic(view);
        likeButton.setPrefWidth(50);
        likeButton.setPrefHeight(50);

        Button commentButton = new Button();
        Image img2 = new Image("/resources/comentarios.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(30);
        view2.setPreserveRatio(true);
        commentButton.setGraphic(view2);
        commentButton.setPrefHeight(50);
        commentButton.setPrefWidth(50);

        Button buyButton = new Button();
        Image img3 = new Image("/resources/comprar.png");
        ImageView view3 = new ImageView(img3);
        view3.setFitHeight(30);
        view3.setPreserveRatio(true);
        buyButton.setGraphic(view3);
        buyButton.setPrefWidth(50);
        buyButton.setPrefHeight(50);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefColumnCount(20);
        descriptionArea.setWrapText(true);
        String contenido = "";
        contenido += info.split(",")[0]+"\n";
        contenido += info.split(",")[1]+"\n";
        contenido += info.split(",")[2]+"\n";
        contenido += info.split(",")[3]+"\n";
        contenido += info.split(",")[4]+"\n";
        contenido += info.split(",")[5]+"\n";
        contenido += info.split(",")[6]+"\n";
        descriptionArea.setText(contenido);
        descriptionArea.setDisable(true);

        Label nombre = new Label(info.split(",")[0]);
        Label precio = new Label("$"+info.split(",")[1]);
        Label categoria = new Label(info.split(",")[2]);
        Label estado = new Label(info.split(",")[3]);
        Label fecha = new Label(info.split(",")[6]);

        nombre.setPrefWidth(100);
        nombre.setPrefHeight(100);
        nombre.setFont(new Font(20));

        precio.setPrefWidth(100);
        precio.setPrefHeight(100);
        precio.setFont(new Font(20));

        categoria.setPrefWidth(300);
        categoria.setPrefHeight(100);
        categoria.setFont(new Font(20));

        estado.setPrefWidth(300);
        estado.setPrefHeight(100);
        estado.setFont(new Font(20));

        fecha.setPrefHeight(100);
        fecha.setPrefWidth(300);
        fecha.setFont(new Font(20));

        VBox infoText = new VBox();
        infoText.getChildren().addAll(nombre, precio, categoria, estado, fecha);
        infoText.setSpacing(15);

        buttonContent.getChildren().addAll(cantidadLikes,likeButton, commentButton, buyButton);
        infoContent.getChildren().addAll(imageView, infoText);

        itemPane.getChildren().addAll( infoContent, buttonContent);

        likeButton.setOnMouseClicked(event -> {
            darLike(likeButton, cantidadLikes);
            likeButton.setDisable(true);
        });

        commentButton.setOnMouseClicked(event -> {
            try {
                obtenerIdProducto(commentButton);
                modelFactoryController.getMarketplace().setProductoSeleccionado(idProducto);
                abrirVentanaComentarios();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Evento cuando el mouse entra en el botón
        likeButton.setOnMouseEntered(event -> {
            likeButton.setScaleX(1.2); // Aumentar el tamaño horizontalmente
            likeButton.setScaleY(1.2); // Aumentar el tamaño verticalmente
            likeButton.setCursor(Cursor.HAND);
        });

        // Evento cuando el mouse sale del botón
        likeButton.setOnMouseExited(event -> {
            likeButton.setScaleX(1.0); // Restaurar el tamaño horizontal original
            likeButton.setScaleY(1.0); // Restaurar el tamaño vertical original
        });
        // Evento cuando el mouse entra en el botón
        commentButton.setOnMouseEntered(event -> {
            commentButton.setScaleX(1.2); // Aumentar el tamaño horizontalmente
            commentButton.setScaleY(1.2); // Aumentar el tamaño verticalmente
            commentButton.setCursor(Cursor.HAND);
        });

        // Evento cuando el mouse sale del botón
        commentButton.setOnMouseExited(event -> {
            commentButton.setScaleX(1.0); // Restaurar el tamaño horizontal original
            commentButton.setScaleY(1.0); // Restaurar el tamaño vertical original
        });
        // Evento cuando el mouse entra en el botón
        buyButton.setOnMouseEntered(event -> {
            buyButton.setScaleX(1.2); // Aumentar el tamaño horizontalmente
            buyButton.setScaleY(1.2); // Aumentar el tamaño verticalmente
            buyButton.setCursor(Cursor.HAND);
        });

        // Evento cuando el mouse sale del botón
        buyButton.setOnMouseExited(event -> {
            buyButton.setScaleX(1.0); // Restaurar el tamaño horizontal original
            buyButton.setScaleY(1.0); // Restaurar el tamaño vertical original
        });

        return itemPane;
    }

    private void abrirVentanaComentarios() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/views/comment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("LISTA DE COMENTARIOS");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void darLike(Button likeButton, Label cantidadLikes) {
        HBox buttonContent = (HBox) likeButton.getParent();
        VBox parentContent = (VBox) buttonContent.getParent();
        HBox infoContent = (HBox) parentContent.getChildren().get(0);
        VBox infoText = (VBox) infoContent.getChildren().get(1);

        Label id = (Label) infoText.getChildren().get(0);
        idProducto = id.getText();

        Producto productoSeleccionado = producCargados.stream().filter(producto -> producto.getIdProducto().equals(id.getText())).findFirst().orElse(null);

        if(productoSeleccionado != null){
            productoSeleccionado.setLikes(1);
        }
        cantidadLikes.setText(String.valueOf(productoSeleccionado.getLikes()));
    }

    private void refreshTab() throws IOException {
        contentBox.getChildren().clear();
        producCargados = modelFactoryController.obtenerProductos();
        int cantidadProductos = producCargados.size();

        for (int i = 0; i < cantidadProductos; i++) {
            Producto producto = producCargados.get(i);
            String infoProducto = producto.toString();
            addNewItem(infoProducto, producCargados.get(i));
        }

        scrollPane.setContent(contentBox);
    }

    public void obtenerIdProducto(Button boton){
        HBox buttonContent = (HBox) boton.getParent();
        VBox parentContent = (VBox) buttonContent.getParent();
        HBox infoContent = (HBox) parentContent.getChildren().get(0);
        VBox infoText = (VBox) infoContent.getChildren().get(1);

        Label id = (Label) infoText.getChildren().get(0);
        idProducto = id.getText();
    }
}
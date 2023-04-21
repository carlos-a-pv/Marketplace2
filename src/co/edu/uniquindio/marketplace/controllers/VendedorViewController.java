package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Categoria;
import co.edu.uniquindio.marketplace.model.Disponibilidad;
import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VendedorViewController {
    ModelFactoryController modelFactoryController;
    CrudProductoViewController crudProductoViewController;
    ObservableList<Producto> listaProductosData = FXCollections.observableArrayList();
    @FXML
    private TabPane tabPane;



    @FXML
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        crudProductoViewController = new CrudProductoViewController(modelFactoryController);

        int cantidadVendedores = modelFactoryController.obtenerVendedores().size();
        for (int i=0; i<cantidadVendedores; i++){

            //Creaccion de componentes
            Tab tab = new Tab("Tab"+i);
            VBox content = new VBox();
            Label nombre = new Label(modelFactoryController.obtenerVendedores().get(i).getNombre());
            ImageView fotoUsuario = new ImageView();
            Image image = new Image(getClass().getResourceAsStream("/resources/usuario.png"));
            Button btnCambiarImagen = new Button("Cambiar imagen");
            Button btnPulicar = new Button("publicar");
            Button btnEditar = new Button();
            TableView<Producto> productos = new TableView<>();
            TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
            TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
            TableColumn<Producto, Disponibilidad> colEstado = new TableColumn<>("Estado");
            TextArea descripcion2 = new TextArea(modelFactoryController.obtenerVendedores().get(i).getDescripcion());
            HBox hbox = new HBox(fotoUsuario, nombre,  descripcion2, btnEditar);

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
            //this.colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

            productos.getColumns().addAll(colNombre,colPrecio, colEstado);

            Image img = new Image("/resources/edit.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(20);
            view.setPreserveRatio(true);
            btnEditar.setGraphic(view);

            //Add de componentes
            content.getChildren().addAll( hbox,  btnCambiarImagen, productos, btnPulicar);
            tab.setContent(content);
            tabPane.getTabs().add(tab);

            //acciones
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


        }
/*
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabVendedor1) {
                //inicializa la tabla en Tab1
                //por ejemplo:
                //
                System.out.println("SELECCION TAB 1");
            } else if (newTab == tabVendedor2) {
                //inicializa la tabla en Tab2
                //por ejemplo:
                //table2.setItems(getDataForTable2());
                System.out.println("Seleccion tab 2");
            }
        });*/
    }

    public void OnCambiarFotoClick(ActionEvent actionEvent) {
    }

    public ObservableList<Producto> getListaProductoData() {
        listaProductosData.addAll(crudProductoViewController.obtenerProductos());
        return listaProductosData;
    }

    public static <Prducto> ObservableList<Producto> convert(ArrayList<Producto> arrayList) {
        ObservableList<Producto> observableList = FXCollections.observableArrayList(arrayList);
        return observableList;

    }
}

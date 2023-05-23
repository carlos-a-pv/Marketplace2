package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;

public class ProductoInfoController {

    ModelFactoryController modelFactoryController;
    CrudVendedorViewController crudVendedorViewController;
    Producto productoSeleccionado;
    Vendedor vendedorSeleccionado;
    Vendedor vendedorLogeado;

    @FXML
    private Pane contentImgProduct;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfCategory;
    @FXML
    private TextField tfDisponibility;
    @FXML
    private Button btnLike;
    @FXML
    private Button btnComment;
    @FXML
    private Button btnBuy;
    @FXML
    private Button btnRemove;

    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        crudVendedorViewController = new CrudVendedorViewController(modelFactoryController);
        productoSeleccionado = modelFactoryController.getMarketplace().getProductoSeleccionado();
        vendedorSeleccionado = modelFactoryController.getVendedorSeleccionado();
        vendedorLogeado = modelFactoryController.getVendedorLogeado();
        tfName.setText(productoSeleccionado.getNombre());
        tfPrice.setText(productoSeleccionado.getPrecio());
        tfCategory.setText(String.valueOf(productoSeleccionado.getCategoria()));
        tfDisponibility.setText(String.valueOf(productoSeleccionado.getEstado()));

        Image img = new Image("/resources/producto.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(200);
        view.setFitWidth(200);
        view.setPreserveRatio(true);
        contentImgProduct.getChildren().add(view);

        if(vendedorSeleccionado == null){
            //no se ha cambiado de pestañas.
            btnLike.setDisable(true);
            btnBuy.setDisable(true);
            btnComment.setDisable(true);
        }else{
            if(vendedorSeleccionado.equals(vendedorLogeado)){
                btnLike.setDisable(true);
                btnBuy.setDisable(true);
                btnComment.setDisable(true);
            }else{
                btnRemove.setDisable(true);
            }
        }
    }
}

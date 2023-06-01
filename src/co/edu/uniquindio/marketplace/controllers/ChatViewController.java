package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Chat;
import co.edu.uniquindio.marketplace.model.Comentario;
import co.edu.uniquindio.marketplace.model.Mensaje;
import co.edu.uniquindio.marketplace.model.Vendedor;
import com.sun.webkit.Timer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ChatViewController {
    ModelFactoryController modelFactoryController;
    Vendedor vendedorLogeado;
    @FXML
    private TableView tbAmigos;
    @FXML
    private TableColumn<Vendedor, String> colNombre;
    @FXML
    private Label lbNombreChat;
    @FXML
    private TextField tfMensaje;
    @FXML
    private Button btnEnviar;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contentMessages;

    Vendedor amigo = new Vendedor();
    @FXML
    void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
        vendedorLogeado = modelFactoryController.getVendedorLogeado();
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        inicializarChats();

        Image img = new Image("/resources/enviar-mensaje.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        view.setPreserveRatio(true);
        btnEnviar.setGraphic(view);
        llenarTabla(modelFactoryController.getVendedorLogeado().getVendedoresAliados());

        tbAmigos.getSelectionModel().selectedItemProperty().addListener((observable, olValue, newValue)->{
            amigo = (Vendedor) newValue;
            lbNombreChat.setText(amigo.getNombre());
            leerMensaje();
        });
        contentMessages.setSpacing(10);
        contentMessages.setPadding(new Insets(10));

        Label mensaje = createMessageLabel("hola");
        contentMessages.getChildren().add(mensaje);
    }

    private void leerMensaje() {
        Chat chat = vendedorLogeado.encontrarChat(amigo.getCedula(), vendedorLogeado);
        if(chat != null){
            chat.getMensajes().forEach(contenido ->{
                Label mensajeChat = new Label(contenido.getContenido());
                addMessage(mensajeChat.getText(), !contenido.isBandera());
                //contentMessages.getChildren().add(mensajeChat);
            });
        }
    }

    public void onClickEnviar(ActionEvent actionEvent) {
        String mensaje = tfMensaje.getText();

        if(mensaje.equals(null) || mensaje.equals("")){
            mostrarMensaje("CHAT","Error enviando mensaje","Ingrese un texto para enviar", Alert.AlertType.INFORMATION);
        }else{
            //Chat chat = modelFactoryController.getVendedorLogeado().enviarMensaje(mensaje, amigo.getCedula(), vendedorLogeado);
            Mensaje contenido = modelFactoryController.getVendedorLogeado().enviarMensaje(mensaje, amigo.getCedula(), vendedorLogeado);
            Label mensajeChat = new Label(contenido.getContenido());
            addMessage(mensajeChat.getText(), contenido.isBandera());
            tfMensaje.setText("");
        }
    }

    public void llenarTabla(List<Vendedor> amigos) {
        tbAmigos.setItems(FXCollections.observableArrayList(amigos));
        tbAmigos.refresh();
    }

    public void inicializarChats(){
        int cantidadAmigos = vendedorLogeado.getVendedoresAliados().size();

        for (int i = 0; i <cantidadAmigos ; i++) {
            Chat  chat= new Chat();
            chat.setVendedor1(vendedorLogeado);
            chat.setVendedor2(vendedorLogeado.getVendedoresAliados().get(i));
            vendedorLogeado.getChats().add(chat);
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private Label createMessageLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-background-color: #e2e2e2; -fx-padding: 10px; -fx-border-radius: 10px;");
        label.setWrapText(true);
        label.setMinWidth(50);
        label.setMaxWidth(200);
        label.setAlignment(Pos.CENTER_LEFT);
        return label;
    }

    private void addMessage(String text, boolean alignRight) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(alignRight ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        messageBox.setSpacing(10);

        Label label = createMessageLabel(text);
        messageBox.getChildren().add(label);

        contentMessages.getChildren().add(messageBox);

        //scrollToBottom();
    }
}

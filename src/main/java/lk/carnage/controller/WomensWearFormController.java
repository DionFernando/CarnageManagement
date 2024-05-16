package lk.carnage.controller;

import animatefx.animation.JackInTheBox;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.carnage.model.Product;
import lk.carnage.model.Womens;
import lk.carnage.model.tm.WomensTm;
import lk.carnage.repository.ProductRepo;
import lk.carnage.repository.WomensRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class WomensWearFormController implements Initializable {
    public AnchorPane rootNode;
    public ImageView img;
    public JFXButton clearBtn;
    public JFXButton addBtn;
    public JFXButton updateBtn;
    public JFXButton deleteBtn;
    public TableColumn colSeason;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colProduct;
    public TableView tblWomen;
    public TextField txtSeason;
    public TextField txtQty;
    public TextField txtCategory;
    public TextField txtPrice;
    public TextField txtId;
    public Label lblSeason;
    public Label lblQty;
    public Label lblPrice;
    public Label lblID;
    public Label lblCategory;
    public Label lblWomen;
    public ImageView homeImg;
    public ImageView logoImg;
    public Label lblInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        // Add listeners to the text properties of the TextFields
        addTextChangeListener(txtId);
        addTextChangeListener(txtCategory);
        addTextChangeListener(txtPrice);
        addTextChangeListener(txtQty);
        addTextChangeListener(txtSeason);

        setCellValueFactory();
        loadAllWomens();

        Platform.runLater(() -> txtId.requestFocus());

        KeyEventHandlersToTextFields();

        Validations();

        generateID();
        txtId.setEditable(false);

        setMouseNavigation();
    }

    private void setMouseNavigation() {
        rootNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2) {
                try {
                    navigateToMensWearForm();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void navigateToMensWearForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/mens_wear_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Mens Wear Form");
    }

    private void generateID() {
        try {
            String currentID = WomensRepo.getCurrentID();

            String newID = generateNextID(currentID);
            txtId.setText(newID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] split = currentID.split("W");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return String.format("W%03d", idNum);
        }
        return "W001";
    }

    private void Validations() {
        txtId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtId.getText().isEmpty() && !event.getCharacter().equalsIgnoreCase("W")) {
                event.consume();
            }
        });

        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtPrice.setText(oldValue);
                lblInfo.setText("Enter Price only in numbers");
                lblInfo.setStyle("-fx-text-fill: red;");
            } else {
                lblInfo.setText("");
            }
        });

        txtQty.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQty.setText(oldValue);
                lblInfo.setText("Enter Qty only in numbers");
                lblInfo.setStyle("-fx-text-fill: red;");
            } else {
                lblInfo.setText("");
            }
        });
    }

    private void KeyEventHandlersToTextFields() {
        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCategory.requestFocus();
            }
        });

        txtCategory.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus();
            }
        });

        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQty.requestFocus();
            }
        });

        txtQty.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtSeason.requestFocus();
            }
        });

        txtSeason.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addItemOnAction(null);
            }
        });
    }
    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #005436;");

            if (textField == txtId && !newValue.matches("^W.*$")) {
                lblInfo.setText("ID should start with 'W'");
                lblInfo.setStyle("-fx-text-fill: red;");
            } else {
                lblInfo.setText("");
            }
        });

        // Change the border color back to black when the TextField loses focus
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                textField.setStyle("-fx-border-color: black;");
            }
        });
    }
    private void addAnimations() {
        Pulse pulse = new Pulse(img);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();

        new JackInTheBox(img).play();
        new JackInTheBox(tblWomen).play();
        new JackInTheBox(lblWomen).play();
        new JackInTheBox(homeImg).play();
        new JackInTheBox(logoImg).play();
        new JackInTheBox(lblID).play();
        new JackInTheBox(lblSeason).play();
        new JackInTheBox(lblQty).play();
        new JackInTheBox(lblCategory).play();
        new JackInTheBox(lblPrice).play();
        new JackInTheBox(txtId).play();
        new JackInTheBox(txtSeason).play();
        new JackInTheBox(txtQty).play();
        new JackInTheBox(txtCategory).play();
        new JackInTheBox(txtPrice).play();
        new JackInTheBox(addBtn).play();
        new JackInTheBox(clearBtn).play();
        new JackInTheBox(updateBtn).play();
        new JackInTheBox(deleteBtn).play();

    }
    private void addHoverEffects() {
        addHoverEffect(addBtn);
        clearHoverEffect(clearBtn);
        updateHoverEffect(updateBtn);
        deleteHoverEffect(deleteBtn);
    }
    private void deleteHoverEffect(JFXButton deleteBtn) {
        deleteBtn.setOnMouseEntered(event -> deleteBtn.setStyle("-fx-background-color: #ba4e4e; -fx-background-radius: 10;"));
        deleteBtn.setOnMouseExited(event -> deleteBtn.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }
    private void updateHoverEffect(JFXButton updateBtn) {
        updateBtn.setOnMouseEntered(event -> updateBtn.setStyle("-fx-background-color: #ba7d4e; -fx-background-radius: 10;"));
        updateBtn.setOnMouseExited(event -> updateBtn.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }
    private void clearHoverEffect(JFXButton clearBtn) {
        clearBtn.setOnMouseEntered(event -> clearBtn.setStyle("-fx-background-color: #bab14e; -fx-background-radius: 10;"));
        clearBtn.setOnMouseExited(event -> clearBtn.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }
    private void addHoverEffect(JFXButton addBtn) {
        addBtn.setOnMouseEntered(event -> addBtn.setStyle("-fx-background-color: #1d991f; -fx-background-radius: 10;"));
        addBtn.setOnMouseExited(event -> addBtn.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }
    public void clearOnAction(ActionEvent actionEvent) {
        clearText();
        txtId.setDisable(false);
        generateID();
    }
    public void homeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }
    public void addItemOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String category = txtCategory.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();
        String season = txtSeason.getText();

        // Check if all TextFields are filled
        if (id.isEmpty() || category.isEmpty() || price.isEmpty() || qty.isEmpty() || season.isEmpty()) {
            lblInfo.setText("Fill all the information");
            lblInfo.setStyle("-fx-text-fill: red;");

            // Request focus on the unfilled TextField
            if (id.isEmpty()) {
                txtId.requestFocus();
                txtId.setStyle("-fx-border-color: red;");
            } else if (category.isEmpty()) {
                txtCategory.requestFocus();
                txtCategory.setStyle("-fx-border-color: red;");
            } else if (price.isEmpty()) {
                txtPrice.requestFocus();
                txtPrice.setStyle("-fx-border-color: red;");
            } else if (qty.isEmpty()) {
                txtQty.requestFocus();
                txtQty.setStyle("-fx-border-color: red;");
            } else {
                txtSeason.requestFocus();
                txtSeason.setStyle("-fx-border-color: red;");
            }
            return; // Return from the method if any field is not filled
        }

        Womens womens = new Womens(id, category, Double.parseDouble(price), Integer.parseInt(qty), season);

        try {
            boolean isSaved = WomensRepo.save(womens);
            if(isSaved) {
                lblInfo.setText("Item Saved Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearText();
                loadAllWomens();
                txtId.requestFocus();
                generateID();
            }
        } catch (SQLException e) {
            lblInfo.setText("ID already taken");
            lblInfo.setStyle("-fx-text-fill: red;");
            txtId.requestFocus();
            txtId.setStyle("-fx-border-color: red;");
        }
    }
    private void clearText() {
        txtId.clear();
        txtCategory.clear();
        txtPrice.clear();
        txtQty.clear();
        txtSeason.clear();
    }
    private void setCellValueFactory(){
        colProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));
    }
    private void loadAllWomens(){
        ObservableList<WomensTm> obList = FXCollections.observableArrayList();

        try{
            List<Womens> womensList = WomensRepo.getAll();
            for(Womens womens : womensList){
                WomensTm tm = new WomensTm(
                        womens.getId(),
                        womens.getCategory(),
                        womens.getPrice(),
                        womens.getQty(),
                        womens.getSeason()
                );
                obList.add(tm);
            }
            tblWomen.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String category = txtCategory.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();
        String season = txtSeason.getText();

        Product product = new Product(id, category, Double.parseDouble(price), Integer.parseInt(qty), season);

        try {
            boolean isUpdated = ProductRepo.updateItem(product);
            if (isUpdated) {
                lblInfo.setText("Product Updated Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllWomens();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Update!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = ProductRepo.delete(id);
            if(isDeleted) {
                lblInfo.setText("Product Deleted Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllWomens();
                clearText();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Delete!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        WomensTm womensTm = (WomensTm) tblWomen.getSelectionModel().getSelectedItem();
        txtId.setText(womensTm.getId());
        txtCategory.setText(womensTm.getCategory());
        txtPrice.setText(String.valueOf(womensTm.getPrice()));
        txtQty.setText(String.valueOf(womensTm.getQty()));
        txtSeason.setText(womensTm.getSeason());

        txtId.setDisable(true);
    }

    public void MenuBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/menuForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(150);
        stage.setY(35);

        stage.setTitle("Menu Form");

        stage.show();
    }
}

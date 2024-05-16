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
import lk.carnage.model.Accessories;
import lk.carnage.model.Product;
import lk.carnage.model.tm.AccessoriesTm;
import lk.carnage.repository.AccessoriesRepo;
import lk.carnage.repository.ProductRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AccessoriesFormController implements Initializable {
    public AnchorPane rootNode;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colCat;
    public TableColumn colProd;
    public TableView tblAcces;
    public JFXButton btnDel;
    public JFXButton btnUpd;
    public JFXButton btnAdd;
    public JFXButton btnClear;
    public TextField txtSeason;
    public TextField txtQty;
    public TextField txtPrice;
    public TextField txtCat;
    public TextField txtID;
    public Label lblSeason;
    public Label lblQty;
    public Label lblPrice;
    public Label lblCat;
    public Label lblID;
    public ImageView imgBag;
    public Label lblAccess;
    public ImageView imgBot;
    public ImageView imgcarnage;
    public ImageView imgHome;
    public TableColumn colSeason;
    public Label lblInfo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        // Add listeners to the text properties of the TextFields
        addTextChangeListener(txtID);
        addTextChangeListener(txtCat);
        addTextChangeListener(txtPrice);
        addTextChangeListener(txtQty);
        addTextChangeListener(txtSeason);

        setCellValueFactory();
        loadAllAccessories();

        Platform.runLater(() -> txtID.requestFocus());

        KeyEventHandlersToTextFields();

        txtID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtID.getText().isEmpty() && !event.getCharacter().equalsIgnoreCase("A")) {
                event.consume();
            }
        });

        Validations();

        generateID();
        txtID.setEditable(false);

        setMouseNavigation();
    }

    private void setMouseNavigation() {
        rootNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                try {
                    navigateToMensWearForm();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2) {
                try {
                    navigateToGiftCardsForm();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void navigateToGiftCardsForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/gift_cards_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Gift Cards Form");
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
            String currentID = AccessoriesRepo.getCurrentID();

            String newID = generateNextID(currentID);
            txtID.setText(newID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] split = currentID.split("A");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return String.format("A%03d", idNum);
        }
        return "A001";
    }

    private void Validations() {
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
        txtID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCat.requestFocus();
            }
        });

        txtCat.setOnKeyPressed(event -> {
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
                // Here you can define what should happen when Enter is pressed in the last TextField
                // For example, you can simulate a button click:
                addBtnOnAction(null);
            }
        });
    }
    private void addAnimations() {
        Pulse pulse = new Pulse(imgBag);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();

        Pulse pulse2 = new Pulse(imgBot);
        pulse2.setCycleCount(Animation.INDEFINITE);
        pulse2.setSpeed(0.3);
        pulse2.play();

        new JackInTheBox(imgcarnage).play();
        new JackInTheBox(imgBot).play();
        new JackInTheBox(imgBag).play();
        new JackInTheBox(lblAccess).play();
        new JackInTheBox(lblCat).play();
        new JackInTheBox(lblID).play();
        new JackInTheBox(lblPrice).play();
        new JackInTheBox(lblQty).play();
        new JackInTheBox(lblSeason).play();
        new JackInTheBox(txtCat).play();
        new JackInTheBox(txtID).play();
        new JackInTheBox(txtPrice).play();
        new JackInTheBox(txtQty).play();
        new JackInTheBox(txtSeason).play();
        new JackInTheBox(tblAcces).play();
        new JackInTheBox(btnAdd).play();
        new JackInTheBox(btnClear).play();
        new JackInTheBox(btnDel).play();
        new JackInTheBox(btnUpd).play();
    }
    private void addTextChangeListener(TextField textField) {
        // Change the border color to green when the user types into the TextField
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #005436;");

            if (textField == txtID && !newValue.matches("^A.*$")) {
                lblInfo.setText("ID should start with 'A'");
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
    private void addHoverEffects() {
        addHoverEffect(btnAdd);
        clearHoverEffect(btnClear);
        updateHoverEffect(btnUpd);
        deleteHoverEffect(btnDel);
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
    private void setCellValueFactory(){
        colProd.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));
    }
    private void loadAllAccessories(){
        ObservableList<AccessoriesTm> obList = FXCollections.observableArrayList();

        try{
            List<Accessories> accessoriesList = AccessoriesRepo.getAll();
            for(Accessories accessories : accessoriesList){
                AccessoriesTm tm = new AccessoriesTm(
                        accessories.getId(),
                        accessories.getCategory(),
                        accessories.getPrice(),
                        accessories.getQty(),
                        accessories.getSeason()
                );
                obList.add(tm);
            }
            tblAcces.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void homeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }
    public void DelBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();

        try {
            boolean isDeleted = ProductRepo.delete(id);
            if(isDeleted) {
                lblInfo.setText("Product Deleted Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllAccessories();
                clearText();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Delete!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String category = txtCat.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();
        String season = txtSeason.getText();

        Product product = new Product(id, category, Double.parseDouble(price), Integer.parseInt(qty), season);

        try {
            boolean isUpdated = ProductRepo.updateItem(product);
            if (isUpdated) {
                lblInfo.setText("Product Updated Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllAccessories();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Update!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void addBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String category = txtCat.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();
        String season = txtSeason.getText();

        // Check if all TextFields are filled
        if (id.isEmpty() || category.isEmpty() || price.isEmpty() || qty.isEmpty() || season.isEmpty()) {
            lblInfo.setText("Fill all the information");
            lblInfo.setStyle("-fx-text-fill: red;");

            // Request focus on the unfilled TextField
            if (id.isEmpty()) {
                txtID.requestFocus();
                txtID.setStyle("-fx-border-color: red;");
            } else if (category.isEmpty()) {
                txtCat.requestFocus();
                txtCat.setStyle("-fx-border-color: red;");
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
            return;
        }

        Accessories accessories = new Accessories(id, category, Double.parseDouble(price), Integer.parseInt(qty), season);

        try {
            boolean isSaved = AccessoriesRepo.save(accessories);
            if(isSaved) {
                lblInfo.setText("Item Saved Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearText();
                loadAllAccessories();
                txtID.requestFocus();
                generateID();
            }
        } catch (SQLException e) {
            lblInfo.setText("ID already taken");
            lblInfo.setStyle("-fx-text-fill: red;");
            txtID.requestFocus();
            txtID.setStyle("-fx-border-color: red;");
        }
    }
    private void clearText() {

        txtID.clear();
        txtCat.clear();
        txtPrice.clear();
        txtQty.clear();
        txtSeason.clear();
    }
    public void clearBtnOnAction(ActionEvent actionEvent) {
        txtID.setDisable(false);
        clearText();
        generateID();
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        AccessoriesTm accessoriesTm = (AccessoriesTm) tblAcces.getSelectionModel().getSelectedItem();
        txtID.setText(accessoriesTm.getId());
        txtCat.setText(accessoriesTm.getCategory());
        txtPrice.setText(String.valueOf(accessoriesTm.getPrice()));
        txtQty.setText(String.valueOf(accessoriesTm.getQty()));
        txtSeason.setText(accessoriesTm.getSeason());

        txtID.setDisable(true);
    }
}

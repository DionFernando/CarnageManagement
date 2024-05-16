package lk.carnage.controller;

import animatefx.animation.JackInTheBox;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.carnage.model.GiftCard;
import lk.carnage.model.tm.GiftCardTm;
import lk.carnage.repository.GiftCardRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GiftCardsFormController implements Initializable {
    public AnchorPane rootNode;
    public CheckBox checkBoxECard;
    public CheckBox checkBoxPhysical;
    public TableColumn colType;
    public TableView tblGiftCard;
    public JFXButton btnDel;
    public JFXButton btnUpd;
    public JFXButton btnClr;
    public JFXButton btnAdd;
    public TextField txtPrice;
    public Label lbl4;
    public Label lbl3;
    public Label lbl2;
    public Label lbl1;
    public ImageView img;
    public ImageView imgHome;
    public ImageView imgCarnage;
    public TextField txtID;
    public Label lblInfo;
    public TableColumn colPrice;
    public TableColumn colID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        // Add listeners to the text properties of the TextFields
        addTextChangeListener(txtPrice);
        addTextChangeListener(txtID);

        checkBoxSelection();

        setCellValueFactory();
        loadAllGiftCards();

        addTextChangeListener(txtID);

        Validations();

        KeyEventHandlersToTextFields();

        generateID();
        txtID.setEditable(false);

        setMouseNavigation();
    }

    private void setMouseNavigation() {
        rootNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                try {
                    navigateToAccessorisForm();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void navigateToAccessorisForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/accessories_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Mens Wear Form");
    }

    private void generateID() {
        try {
            String currentID = GiftCardRepo.getCurrentID();

            String newID = generateNextID(currentID);
            txtID.setText(newID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] split = currentID.split("G");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return String.format("G%03d", idNum);
        }
        return "G001";
    }

    private void KeyEventHandlersToTextFields() {
        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addBtnOnAction(null);
            }
        });
    }

    private void Validations() {
        txtID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtID.getText().isEmpty() && !event.getCharacter().equalsIgnoreCase("G")) {
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
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void checkBoxSelection() {
        checkBoxECard.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkBoxPhysical.setSelected(false);
            }
        });

        checkBoxPhysical.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkBoxECard.setSelected(false);
            }
        });
    }

    private void addTextChangeListener(TextField textField) {
        // Change the border color to green when the user types into the TextField
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #005436;");

            if (textField == txtID && !newValue.matches("^G.*$")) {
                lblInfo.setText("ID should start with 'G'");
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
        new JackInTheBox(imgHome).play();
        new JackInTheBox(imgCarnage).play();

        new JackInTheBox(lbl1).play();
        new JackInTheBox(lbl2).play();
        new JackInTheBox(lbl3).play();
        new JackInTheBox(lbl4).play();

        new JackInTheBox(txtPrice).play();
        new JackInTheBox(txtID).play();

        new JackInTheBox(btnAdd).play();
        new JackInTheBox(btnClr).play();
        new JackInTheBox(btnDel).play();
        new JackInTheBox(btnUpd).play();

        new JackInTheBox(checkBoxECard).play();
        new JackInTheBox(checkBoxPhysical).play();

        new JackInTheBox(tblGiftCard).play();
    }
    private void addHoverEffects() {
        addHoverEffect(btnAdd);
        clearHoverEffect(btnClr);
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
    public void homeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();

        try {
            boolean isDeleted = GiftCardRepo.delete(id);
            if(isDeleted) {
                lblInfo.setText("Product Deleted Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllGiftCards();
                clearText();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Delete!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String PGC = checkBoxPhysical.isSelected() ? checkBoxPhysical.getText() : "";
        String EGC = checkBoxECard.isSelected() ? checkBoxECard.getText() : "";

        String type = PGC.isEmpty() ? EGC : PGC;
        String price = txtPrice.getText();

        GiftCard giftCard= new GiftCard(id, Double.parseDouble(price), type);

        try {
            boolean isUpdated = GiftCardRepo.updateItem(giftCard);
            if (isUpdated) {
                lblInfo.setText("Product Updated Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllGiftCards();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Update!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void addBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String price = txtPrice.getText();

        if (id.isEmpty() || price.isEmpty() || (!checkBoxPhysical.isSelected() && !checkBoxECard.isSelected())) {
            lblInfo.setText("Fill all the information");
            lblInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        String PGC = checkBoxPhysical.isSelected() ? checkBoxPhysical.getText() : "";
        String EGC = checkBoxECard.isSelected() ? checkBoxECard.getText() : "";

        String type = PGC.isEmpty() ? EGC : PGC;

        GiftCard giftCard = new GiftCard(id, Double.parseDouble(price), type);

        try {
            boolean isSaved = GiftCardRepo.save(giftCard);
            if(isSaved) {
                lblInfo.setText("Item Saved Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearText();
                loadAllGiftCards();
                generateID();
            }
        } catch (SQLException e) {
            lblInfo.setText("ID already taken");
            lblInfo.setStyle("-fx-text-fill: red;");
            txtID.requestFocus();
            txtID.setStyle("-fx-border-color: red;");
        }

    }

    private void loadAllGiftCards() {
        ObservableList<GiftCardTm> obList = FXCollections.observableArrayList();

        try{
            List<GiftCard> giftCardsList = GiftCardRepo.getAll();
            for(GiftCard giftCard : giftCardsList){
                GiftCardTm tm = new GiftCardTm(
                        giftCard.getId(),
                        giftCard.getType(),
                        giftCard.getPrice()

                );
                obList.add(tm);
            }
            tblGiftCard.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearText() {
        txtID.clear();
        txtPrice.clear();
        checkBoxPhysical.setSelected(false);
        checkBoxECard.setSelected(false);
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearText();
        txtID.setDisable(false);
        generateID();
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        GiftCardTm selectedItem = (GiftCardTm) tblGiftCard.getSelectionModel().getSelectedItem();
        txtID.setText(selectedItem.getId());
        txtPrice.setText(String.valueOf(selectedItem.getPrice()));
        txtID.setDisable(true);
    }
}

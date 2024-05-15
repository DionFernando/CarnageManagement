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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.carnage.model.Customer;
import lk.carnage.model.tm.CustomerTm;
import lk.carnage.repository.CustomerRepo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    public AnchorPane rootNode;
    public TableColumn colName;
    public TableColumn colTel;
    public TableView tblCustomer;
    public JFXButton btnDel;
    public JFXButton btnUpd;
    public JFXButton btnClear;
    public JFXButton btnAdd;
    public TextField txtAddress;
    public TextField txtname;
    public TextField txtTel;
    public TextField txtID;
    public Label lblAddress;
    public Label lblTel;
    public Label lblName;
    public Label lblID;
    public Label lblCustomer;
    public ImageView imgBig;
    public ImageView imgHome;
    public ImageView imgCarnage;
    public Label lblInfo;
    public TableColumn colAddress;
    public JFXButton sendEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        // Add listeners to the text properties of the TextFields
        addTextChangeListener(txtID);
        addTextChangeListener(txtAddress);
        addTextChangeListener(txtname);
        addTextChangeListener(txtTel);

        setCellValueFactory();
        loadAllCustomers();

        Platform.runLater(() -> txtID.requestFocus());

        KeyEventHandlersToTextFields();

        txtID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtID.getText().isEmpty() && !event.getCharacter().equalsIgnoreCase("C")) {
                event.consume();
            }
        });

        Validations();

        generateID();
        txtID.setEditable(false);
    }

    private void generateID() {
        try {
            String currentID = CustomerRepo.getCurrentID();

            String newID = generateNextID(currentID);
            txtID.setText(newID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] split = currentID.split("C");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return String.format("C%03d", idNum);
        }
        return "C001";
    }

    private void Validations() {
        txtTel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.startsWith("+")) {
                if (!newValue.matches("\\+\\d{0,13}")) { // regex for foreign numbers
                    txtTel.setText(oldValue); // revert back to the old value
                    lblInfo.setText("Only numeric limited to 13 digits");
                    lblInfo.setStyle("-fx-text-fill: red;");
                } else {
                    lblInfo.setText("");
                }
            } else {
                if (!newValue.matches("\\d{0,10}")) { // regex for local numbers
                    txtTel.setText(oldValue); // revert back to the old value
                    lblInfo.setText("Only numeric limited to 10 digits");
                    lblInfo.setStyle("-fx-text-fill: red;");
                } else {
                    lblInfo.setText("");
                }
            }
        });
    }

    private void KeyEventHandlersToTextFields() {
        txtID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtname.requestFocus();
            }
        });

        txtname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        txtTel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addBtnOnAction(null);
            }
        });
    }

    private void setCellValueFactory() {
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void addAnimations() {
        Pulse pulse = new Pulse(imgCarnage);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();

        new JackInTheBox(lblID).play();
        new JackInTheBox(lblName).play();
        new JackInTheBox(lblTel).play();
        new JackInTheBox(lblAddress).play();
        new JackInTheBox(lblCustomer).play();
        new JackInTheBox(imgBig).play();
        new JackInTheBox(imgCarnage).play();
        new JackInTheBox(imgHome).play();
        new JackInTheBox(txtAddress).play();
        new JackInTheBox(txtID).play();
        new JackInTheBox(txtname).play();
        new JackInTheBox(txtTel).play();
        new JackInTheBox(tblCustomer).play();
        new JackInTheBox(btnAdd).play();
        new JackInTheBox(btnClear).play();
        new JackInTheBox(btnDel).play();
        new JackInTheBox(btnUpd).play();
    }
    private void addTextChangeListener(TextField textField) {
        // Change the border color to green when the user types into the TextField
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #005436;");

            if (textField == txtID && !newValue.matches("^C.*$")) {
                lblInfo.setText("ID should start with 'C'");
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
    public void homeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }
    public void addBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtname.getText();
        String tel = txtTel.getText();
        String address = txtAddress.getText();

        if (id.isEmpty() || name.isEmpty() || tel.isEmpty() || address.isEmpty()) {
            lblInfo.setText("Fill all the information");
            lblInfo.setStyle("-fx-text-fill: red;");

            // Request focus on the unfilled TextField
            if (id.isEmpty()) {
                txtID.requestFocus();
                txtID.setStyle("-fx-border-color: red;");
            } else if (name.isEmpty()) {
                txtname.requestFocus();
                txtname.setStyle("-fx-border-color: red;");
            } else if (tel.isEmpty()) {
                txtTel.requestFocus();
                txtTel.setStyle("-fx-border-color: red;");
            } else {
                txtAddress.requestFocus();
                txtAddress.setStyle("-fx-border-color: red;");
            }
            return; // Return from the method if any field is not filled
        }

        CheckValidEmail();

        Customer customer = new Customer(id, name, Integer.parseInt(tel), address);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if(isSaved) {
                lblInfo.setText("Customer Saved Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearText();
                loadAllCustomers();
                generateID();
                //sendWhatsappMessage();
                sendEmail(name, address);

                Platform.runLater(() -> txtID.requestFocus());
            }
        } catch (SQLException e) {
            lblInfo.setText("ID already taken");
            lblInfo.setStyle("-fx-text-fill: red;");
            txtID.requestFocus();
            txtID.setStyle("-fx-border-color: red;");
            System.out.println(e.getMessage());
        }
    }

    private void CheckValidEmail() {
        String email = txtAddress.getText();
        if (!email.contains("@") || !email.contains(".")) {
            lblInfo.setText("Invalid Email Address");
            lblInfo.setStyle("-fx-text-fill: red;");
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color: red;");
        }
    }

    private void sendEmail(String name, String email) {
        new Thread(()->{
            try {
                String emailBody = "Dear " + name + ",\n\n" +
                        "We wanted to extend a warm welcome to you as a valued member of our CARNAGE family! \uD83C\uDF89\n\n" +
                        "Thank you for registering with us. We're thrilled to have you on board and can't wait to assist you in finding the perfect outfits that suit your style.\n\n" +
                        "As a member, you'll be the first to know about our latest collections, exclusive offers, and exciting promotions. Feel free to explore our online store at your convenience. If you have any questions or need assistance, don't hesitate to reach out to us. We're here to help!\n" +
                        "Once again, welcome to CARNAGE! We're delighted to have you with us.\n\n" +
                        "Visit our website: https://incarnage.com/\n\n\n\n" +
                        "Best regards,\n" +
                        "The CARNAGE Team\n";

                String subject = "WELCOME TO CARNAGE!";
                String encodedEmailBody = URLEncoder.encode(emailBody, "UTF-8");
                String encodedSubject = URLEncoder.encode(subject, "UTF-8");
                String url = "https://mail.google.com/mail/?view=cm&fs=1&to=" + email + "&body="+encodedEmailBody+"&su="+encodedSubject;
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                System.out.println("An error occurred : "+e.getLocalizedMessage());
            }
        }).start();
    }


    /*private void sendWhatsappMessage() throws AWTException {
        String tel = "+94767149543";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + tel),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Hello there!")
                .create();

        System.out.println("Whatsapp message was sent successfully!");

    }*/

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try{
            List<Customer> customerList = CustomerRepo.getAll();
            for(Customer customer : customerList){
                CustomerTm tm = new CustomerTm(
                        customer.getName(),
                        customer.getTel(),
                        customer.getAddress()
                );
                obList.add(tm);
            }
            tblCustomer.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearText() {
        txtID.clear();
        txtname.clear();
        txtTel.clear();
        txtAddress.clear();
    }

    public void delBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if(isDeleted) {
                lblInfo.setText("Product Deleted Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllCustomers();
                clearText();
            }
        } catch (SQLException e) {
            lblInfo.setText("Product Didn't Delete!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtname.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        Customer customer = new Customer(id, name, Integer.parseInt(tel), address);

        try {
            boolean isUpdated = CustomerRepo.updateCustomer(customer);
            if (isUpdated) {
                lblInfo.setText("Customer Updated Successfully!");
                lblInfo.setStyle("-fx-text-fill: green;");
                loadAllCustomers();
            }
        } catch (SQLException e) {
            lblInfo.setText("Customer Didn't Update!");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }
    public void clearBtnOnAction(ActionEvent actionEvent) {
        txtID.setDisable(false);
        clearText();
        generateID();
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        CustomerTm selectedItem = (CustomerTm) tblCustomer.getSelectionModel().getSelectedItem();

        try {
            Customer customer = CustomerRepo.searchByTel(String.valueOf(selectedItem.getTel()));
            if (customer != null) {
                txtID.setText(customer.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtname.setText(selectedItem.getName());
        txtTel.setText(String.valueOf(selectedItem.getTel()));
        txtAddress.setText(selectedItem.getAddress());

        txtID.setDisable(true);
    }

    public void sendEmailBtnOnAction(ActionEvent actionEvent) throws IOException {
        rootNode.setEffect(new GaussianBlur(10));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/send_email_customers_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.setOnHidden(e -> {
            this.rootNode.setEffect(null);
        });
        stage.show();
    }
}

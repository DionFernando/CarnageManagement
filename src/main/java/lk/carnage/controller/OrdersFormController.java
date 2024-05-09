package lk.carnage.controller;

import animatefx.animation.Pulse;
import animatefx.animation.Wobble;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.carnage.model.*;
import lk.carnage.model.tm.CartTm;
import lk.carnage.repository.CustomerRepo;
import lk.carnage.repository.OrderRepo;
import lk.carnage.repository.PlaceOrderRepo;
import lk.carnage.repository.ProductRepo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersFormController implements Initializable {
    public AnchorPane rootNode;
    public Label lblOrder;
    public Label lblDate;
    public JFXComboBox cmbCustomerId;
    public Label lblCusName;
    public JFXComboBox cmbItemCode;
    public Label lblDescription;
    public Label lblPrice;
    public Label lblQtyOnHand;
    public Label lblNetTotal;
    public AnchorPane leafNode;
    public JFXButton btnPlaceOrder;
    public TableColumn colAction;
    public TableColumn colTot;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colDesc;
    public static int rowCount;
    public TableColumn colCode;
    public TableView tblCart;
    public JFXButton btnAddToCart;
    public TextField txtQty;
    public Label lbl10;
    public Label lbl9;
    public Label lbl8;
    public Label lbl7;
    public Label lbl6;
    public Label lbl5;
    public Label lbl4;
    public Label lbl3;
    public Label lbl1;
    public Label lbl2;
    public ImageView imgCarnage;
    public ImageView imgHome;
    public Label lbl11;
    public JFXButton btnAddCus;
    public Label lblInfoQty;
    public Label lblInfoPlaceOrder;

    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        addAnimations();
        addHoverEffects();

        cmbCustomerId.setEditable(true);
        cmbItemCode.setEditable(true);

        //============================================================
        getCurrentOrderID();
        getCustomerIds();
        getItemCodes();
        setCellValueFactory();


        txtQty.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                btnAddToCart.fire();
            }
        });
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = ProductRepo.getCodes();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getTel();

            for (String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderID() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrder.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private void addHoverEffects() {
        cartHoverEffect(btnAddToCart);
        placeOrderHoverEffect(btnPlaceOrder);
        placeOrderHoverEffect(btnAddCus);
    }

    private void placeOrderHoverEffect(JFXButton btnPlaceOrder) {
        btnPlaceOrder.setOnMouseEntered(event -> btnPlaceOrder.setStyle("-fx-background-color: #1d991f; -fx-background-radius: 10;"));
        btnPlaceOrder.setOnMouseExited(event -> btnPlaceOrder.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }

    private void cartHoverEffect(JFXButton btnAddToCart) {
        btnAddToCart.setOnMouseEntered(event -> btnAddToCart.setStyle("-fx-background-color: #ba7d4e; -fx-background-radius: 10;"));
        btnAddToCart.setOnMouseExited(event -> btnAddToCart.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }

    private void addAnimations() {
        Pulse pulse = new Pulse(lbl1);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.6);
        pulse.play();

        new Wobble(lbl1).play();
        new Wobble(lbl2).play();
        new Wobble(lbl3).play();
        new Wobble(lbl4).play();
        new Wobble(lbl5).play();
        new Wobble(lbl6).play();
        new Wobble(lbl7).play();
        new Wobble(lbl8).play();
        new Wobble(lbl9).play();
        new Wobble(lbl10).play();
        new Wobble(lblOrder).play();
        new Wobble(lblDate).play();
        new Wobble(lblCusName).play();
        new Wobble(lblDescription).play();
        new Wobble(lblPrice).play();
        new Wobble(lblQtyOnHand).play();
        new Wobble(lblNetTotal).play();
        new Wobble(cmbCustomerId).play();
        new Wobble(cmbItemCode).play();
        new Wobble(txtQty).play();
        new Wobble(btnAddToCart).play();
        new Wobble(btnPlaceOrder).play();
        new Wobble(tblCart).play();
        new Wobble(imgCarnage).play();
        new Wobble(imgHome).play();
        new Wobble(lbl11).play();
        new Wobble(btnAddCus).play();

        btnPlaceOrder.setDisable(true);
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) throws SQLException {
        /*if (tblCart.getItems().isEmpty()) {
            lblInfoPlaceOrder.setText("Cart is empty. Cannot place order.");
            return;
        } else {
            lblInfoPlaceOrder.setText("");
        }*/

        String orderId = lblOrder.getText();
        String telePhone = (String) cmbCustomerId.getValue();
        String cusId = CustomerRepo.searchByTel(telePhone).getId();
        System.out.println(cusId);
        Date date = Date.valueOf(LocalDate.now());

        var order = new Order(orderId, date, cusId);

        List<OrderDetail> odList = new ArrayList<>();

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderDetail od = new OrderDetail(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice());

            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);

        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if (isPlaced) {
                AnchorPane employeeForm = FXMLLoader.load(this.getClass().getResource("/view/ReportForm.fxml"));
                leafNode.getChildren().setAll(employeeForm);

                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();


                checkQTy();
                // checkQtyEmail();

            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void checkQTy() throws SQLException {

       String category = ProductRepo.qtyCount();
       System.out.println(category);
       if (!category.equals("[]")) {
           sendReminderEmail(category);
       }
       return;
    }

    /*private void checkQtyEmail() {
        System.out.println("Email Message Sending...");

        String to = "s98fernando@gmail.com";
        String from = "dionfernando2003@gmail.com";
        final String username = "dionfernando2003@gmail.com";


        final String password = "";
        String host = "smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Stock Alert");

            message.setText("The stock is reducing. Supply new stock");

            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void sendReminderEmail(String category){
        new Thread(()->{
            try {
                String emailBody = "The stock " + category + " is reducing. Supply new stock!";
                String subject = "Stock Alert!";
                String encodedEmailBody = URLEncoder.encode(emailBody, "UTF-8");
                String encodedSubject = URLEncoder.encode(subject, "UTF-8");
                String url = "https://mail.google.com/mail/?view=cm&fs=1&to=dionfernando2003@gmail.com&body="+encodedEmailBody+"&su="+encodedSubject;
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                System.out.println("An error occurred : "+e.getLocalizedMessage());
            }
        }).start();
    }
    public void addToCartBtnOnAction(ActionEvent actionEvent) {
        rowCount++;

        String code = (String) cmbItemCode.getValue();
        String description = lblDescription.getText();
        String qtyText = txtQty.getText();
        String unitPriceText = lblPrice.getText();

        if (code == null || code.isEmpty() || description == null || description.isEmpty() ||
                qtyText == null || qtyText.isEmpty() || unitPriceText == null || unitPriceText.isEmpty()) {
            lblInfoQty.setText("Please fill all the fields first");
            return;
        }

        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblPrice.getText());
        double total = qty * unitPrice;

        int qtyOnHand = Integer.parseInt(lblQtyOnHand.getText());

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            if(code.equals(colCode.getCellData(i))) {
                CartTm tm = obList.get(i);
                int totalQty = tm.getQty() + qty;

                if(totalQty > qtyOnHand) {
                    lblInfoQty.setText("QTY exceeded");
                    return;
                } else {
                    lblInfoQty.setText("");
                }

                total = totalQty * unitPrice;

                tm.setQty(totalQty);
                tm.setTotal(total);

                tblCart.refresh();

                calculateNetTotal();
                return;
            }
        }

        if(qty > qtyOnHand) {
            lblInfoQty.setText("QTY exceeded");
            return;
        } else {
            lblInfoQty.setText("");
        }

        qtyOnHand -= qty;
        lblQtyOnHand.setText(String.valueOf(qtyOnHand));

        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblCart.refresh();
                calculateNetTotal();

                if (tblCart.getItems().isEmpty()) {
                    btnPlaceOrder.setDisable(true);
                }
            }
        });

        CartTm tm = new CartTm(code, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
        cmbItemCode.requestFocus();

        //============
        if (!tblCart.getItems().isEmpty()) {
            btnPlaceOrder.setDisable(false);
        }

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            netTotal += (double) colTot.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    public void homebtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }

    public void cmbItemOnAction(ActionEvent actionEvent) {
        String code = (String) cmbItemCode.getValue();

        try {
            Product product = ProductRepo.searchById(code);
            if (product != null) {
                lblDescription.setText(product.getCategory());
                lblPrice.setText(String.valueOf(product.getPrice()));
                lblQtyOnHand.setText(String.valueOf(product.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String tel = (String) cmbCustomerId.getValue();

        try {
            Customer customer = CustomerRepo.searchByTel(tel);

            lblCusName.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCusOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Customer Form");
        stage.show();

        stage.setOnCloseRequest(e -> {
            getCustomerIds();
        });
    }

    public void filterCustomerIds(KeyEvent keyEvent) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        String enteredText = cmbCustomerId.getEditor().getText();

        try {
            List<String> idList = CustomerRepo.getTel();

            for (String id : idList) {
                if (id.contains(enteredText)) {
                    filteredList.add(id);
                }
            }

            cmbCustomerId.setItems(filteredList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterItemCodes(KeyEvent keyEvent) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        String enteredText = cmbItemCode.getEditor().getText();

        try {
            List<String> idList = ProductRepo.getCodes();

            for (String id : idList) {
                if (id.contains(enteredText)) {
                    filteredList.add(id);
                }
            }

            cmbItemCode.setItems(filteredList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
       /* CartTm selectedItem = (CartTm) tblCart.getSelectionModel().getSelectedItem();
        txtQty.setText(String.valueOf(selectedItem.getQty()));
        cmbItemCode.setValue(selectedItem.getCode());*/
    }



}

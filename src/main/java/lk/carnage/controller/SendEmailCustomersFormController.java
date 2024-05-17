package lk.carnage.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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

public class SendEmailCustomersFormController implements Initializable {
    public TextArea txtSubject;
    public TextArea txtBody;
    public Label lblInfo;
    public JFXButton btnSend;
    public AnchorPane rootNode;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomers();
        txtSubject.requestFocus();
        addHoverEffect();

    }

    private void addHoverEffect() {
        btnSend.setOnMouseEntered(event -> btnSend.setStyle("-fx-background-color: #1d991f; -fx-background-radius: 10;"));
        btnSend.setOnMouseExited(event -> btnSend.setStyle("-fx-background-color:  #404040; -fx-background-radius: 10;"));

    }

    private List<String> getCustomers() {
       try {
            List<String> customers = CustomerRepo.getCustomerAddress();

            return customers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendEmailBtnOnAction(ActionEvent actionEvent) {
        if (txtSubject.getText().isEmpty()) {
            lblInfo.setText("Please fill in the subject field.");
            txtSubject.requestFocus();
            return;
        }

        if (txtBody.getText().isEmpty()) {
            lblInfo.setText("Please fill in the body field.");
            txtBody.requestFocus();
            return;
        }

        List<String> customers = getCustomers();
        System.out.println(customers);

        new Thread(()->{
            try {
                String emailBody = txtBody.getText();

                String subject = txtSubject.getText();

                String encodedEmailBody = URLEncoder.encode(emailBody, "UTF-8");
                String encodedSubject = URLEncoder.encode(subject, "UTF-8");

                String allCustomerEmails = String.join(",", customers);

                String url = "https://mail.google.com/mail/?view=cm&fs=1&to=" + allCustomerEmails + "&body=" + encodedEmailBody + "&su=" + encodedSubject;
                Desktop.getDesktop().browse(new URI(url));

            } catch (IOException | URISyntaxException e) {
                System.out.println("An error occurred : "+e.getLocalizedMessage());
            }
        }).start();
    }
}

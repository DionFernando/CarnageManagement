package lk.carnage.controller;

import animatefx.animation.Bounce;
import animatefx.animation.JackInTheBox;
import animatefx.animation.Pulse;
import animatefx.animation.Wobble;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.carnage.db.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public AnchorPane rootNode;
    public ImageView img2;
    public Label lblCarnage;
    public Label datelbl;
    public Label timelbl;
    public ImageView img;
    public Label lblLogin;
    public Label lblUsername;
    public Label lblPas;
    public TextField usernameTxt;

    public TextField passwordVisibleTxt;

    public JFXButton btnLogin;
    public Label lblDntHvAcc;
    public Hyperlink lblRegister;
    public Label lblErrorMsg;
    public ImageView plane;
    public Label welcomeLabel;
    public ImageView imgPassword;
    public PasswordField passwordTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setTime();

        lblRegister.setStyle("-fx-text-fill: black;");

        planeAnimation();
        animations();

        addHoverEffect(btnLogin);
        hyperLinkHoverEffect(lblRegister);

        textFieldEnter();

        focusColor();

        passwordHideShow();

    }

    private void passwordHideShow() {
        //imgPassword.setImage(javafx.scene.image.Image"src/main/resources/icon/Hide.png");

        imgPassword.setOnMouseClicked(event -> {
            if (passwordTxt.isVisible()) {
                passwordVisibleTxt.setText(passwordTxt.getText());
                passwordVisibleTxt.setVisible(true);
                passwordTxt.setVisible(false);
                imgPassword.setImage(new Image(getClass().getResource("/icon/Unhide.png").toExternalForm()));
            } else {
                passwordTxt.setText(passwordVisibleTxt.getText());
                passwordVisibleTxt.setVisible(false);
                passwordTxt.setVisible(true);
                imgPassword.setImage(new Image(getClass().getResource("/icon/Hide.png").toExternalForm()));
            }
        });
    }

    private void focusColor() {
        usernameTxt.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused
                usernameTxt.setStyle("-fx-focus-color: #005436;");
            } else {
                // TextField is not focused
                usernameTxt.setStyle("-fx-focus-color: black;");
            }
        });

        passwordTxt.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused
                passwordTxt.setStyle("-fx-focus-color: #005436;");
            } else {
                // TextField is not focused
                passwordTxt.setStyle("-fx-focus-color: black;");
            }
        });
    }
    private void textFieldEnter() {
        usernameTxt.focusedProperty();

        usernameTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordTxt.requestFocus();
            }
        });

        passwordTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
    }
    private void animations() {
        Pulse pulse = new Pulse(img);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();

        new JackInTheBox(img2).play();
        new JackInTheBox(lblCarnage).play();
        new JackInTheBox(lblLogin).play();
        new JackInTheBox(lblUsername).play();
        new JackInTheBox(lblPas).play();
        new JackInTheBox(usernameTxt).play();
        new JackInTheBox(passwordTxt).play();
        new JackInTheBox(btnLogin).play();
        new JackInTheBox(lblDntHvAcc).play();
        new JackInTheBox(lblRegister).play();
    }
    private void planeAnimation() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        plane.setTranslateX(-screenWidth / 5);
        welcomeLabel.setTranslateX(-screenWidth / 5);

        TranslateTransition translateTransitionPlane = new TranslateTransition();
        translateTransitionPlane.setDuration(Duration.seconds(10)); // Animation duration
        translateTransitionPlane.setNode(plane); // The node (image) to be moved
        translateTransitionPlane.setByX(2100); // The amount by which to move the node horizontally
        translateTransitionPlane.play(); // Start the animation

        TranslateTransition translateTransitionLabel = new TranslateTransition();
        translateTransitionLabel.setDuration(Duration.seconds(10)); // Animation duration
        translateTransitionLabel.setNode(welcomeLabel); // The node (label) to be moved
        translateTransitionLabel.setByX(2100); // The amount by which to move the node horizontally
        translateTransitionLabel.play(); // Start the animation
    }
    private void hyperLinkHoverEffect(Hyperlink hyperlink) {
        hyperlink.setOnMouseEntered(event -> hyperlink.setStyle("-fx-text-fill: #039965;"));
        hyperlink.setOnMouseExited(event -> hyperlink.setStyle("-fx-text-fill: black;"));
    }
    private void addHoverEffect(JFXButton button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #005436; -fx-background-radius: 10;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: black; -fx-background-radius: 10;"));
    }
    private void setTime() {
        new Thread(() -> {
            while (true) {
                try {
                    String formattedTime = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                    Platform.runLater(() -> timelbl.setText(formattedTime));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        String formattedDate = now.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        datelbl.setText(formattedDate);
    }
    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        String userId = usernameTxt.getText();
        String pw = passwordTxt.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT username, password FROM Credential WHERE username = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String dbPw = resultSet.getString("Password");

            if (pw.equals(dbPw)) {
                navigateToMainDashBoardForm();
            } else {
                new Wobble(lblErrorMsg).play();
                lblErrorMsg.setText("Sorry! Password in incorrect!");
                passwordTxt.setStyle("-fx-focus-color: red;");

                passwordTxt.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    passwordTxt.setStyle("-fx-focus-color: red;");
                });
                passwordTxt.requestFocus();
            }
        } else {
            new Wobble(lblErrorMsg).play();
            lblErrorMsg.setText("Sorry! Username in incorrect!");
            usernameTxt.focusedProperty().addListener((observable, oldValue, newValue) -> {
                usernameTxt.setStyle("-fx-focus-color: red;");
            });
            usernameTxt.requestFocus();
        }
    }
    private void navigateToMainDashBoardForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }
    public void registerOnAction(ActionEvent actionEvent) throws IOException {
        rootNode.setEffect(new GaussianBlur(10));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));

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

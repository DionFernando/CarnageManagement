package lk.carnage.controller;

import animatefx.animation.JackInTheBox;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDashboardFormController implements Initializable {
    public AnchorPane rootNode;
    public JFXButton lblManAtte;
    public JFXButton lblManEmp;
    public JFXButton lblSal;
    public AnchorPane leafNode;
    public ImageView imgCarnage;
    public ImageView imgHome;

    private JFXButton currentButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

    }
    private void addAnimations() {
        Pulse pulse = new Pulse(imgCarnage);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();


        new JackInTheBox(lblManAtte).play();
        new JackInTheBox(lblManEmp).play();
        new JackInTheBox(lblSal).play();
        new JackInTheBox(imgHome).play();
        new JackInTheBox(imgCarnage).play();

    }
    private void addHoverEffects() {
        addHoverEffect(lblManAtte);
        addHoverEffect(lblManEmp);
        addHoverEffect(lblSal);
    }
    public void homeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
    }
    public void manSalOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonColors();
        lblSal.setStyle("-fx-background-color: #013825; -fx-background-radius: 10;");
        currentButton = lblSal;
        AnchorPane employeeForm = FXMLLoader.load(this.getClass().getResource("/view/employee_salary_form.fxml"));
        leafNode.getChildren().setAll(employeeForm);
    }
    public void manAttendOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonColors();
        lblManAtte.setStyle("-fx-background-color: #013825; -fx-background-radius: 10;");
        currentButton = lblManAtte;
        AnchorPane employeeForm = FXMLLoader.load(this.getClass().getResource("/view/employee_attendance_form.fxml"));
        leafNode.getChildren().setAll(employeeForm);
    }
    public void manEmpOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonColors();
        lblManEmp.setStyle("-fx-background-color: #013825; -fx-background-radius: 10;");
        currentButton = lblManEmp;
        AnchorPane employeeForm = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        leafNode.getChildren().setAll(employeeForm);
    }
    private void resetButtonColors() {
        if (currentButton != null) {
            currentButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 10;");
        }
    }
    private void addHoverEffect(JFXButton button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #013825; -fx-background-radius: 10;"));
        button.setOnMouseExited(event -> {
            if (button != currentButton) {
                button.setStyle("-fx-background-color: transparent; -fx-background-radius: 10;");
            }
        });
    }
}

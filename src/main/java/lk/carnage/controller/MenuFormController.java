package lk.carnage.controller;

import animatefx.animation.JackInTheBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuFormController implements Initializable {
    public Line line2;
    public Line line1;
    public Button btnC;
    public Button btnP;
    public Button btnG;
    public Button btnA;
    public Button bntM;
    public Button bntw;
    public Button btnE;
    public AnchorPane rootNode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
    }

    private void addAnimations() {
        new JackInTheBox(line1).play();
        new JackInTheBox(line2).play();
        new JackInTheBox(btnC).play();
        new JackInTheBox(btnP).play();
        new JackInTheBox(btnG).play();
        new JackInTheBox(btnA).play();
        new JackInTheBox(bntM).play();
        new JackInTheBox(bntw).play();
        new JackInTheBox(btnE).play();
    }

    public void EmployeeFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) this.rootNode.getScene().getWindow();

        currentStage.close();
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);

        Stage employeeStage = new Stage();
        employeeStage.setScene(scene);
        employeeStage.centerOnScreen();
        employeeStage.setTitle("Employee Form");

        employeeStage.show();

    }

    public void CustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Customer Form");
    }

    public void PlaceOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/orders_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Orders Form");
    }

    public void GiftCardsFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/gift_cards_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Gift Cards Form");
    }

    public void AccessoriesFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/accessories_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Accessories Form");
    }

    public void MensWearFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/mens_wear_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Men's Wear Form");
    }

    public void womensWearFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/womens_wear_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Women's Wear Form");
    }
}

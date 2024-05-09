package lk.carnage.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Pulse;
import animatefx.animation.Wobble;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeSalaryFormController implements Initializable {
    public Label lblSalary;
    public Label lblS;
    public Line line;
    public TextField txtBonus;
    public Label lbl6;
    public Label lblName;
    public Label lbl2;
    public Label lbl4;
    public TableColumn colBonus;
    public TableColumn colSalary;
    public TableColumn colAttd;
    public TableColumn colName;
    public TableView tblSalary;
    public JFXButton btnUpd;
    public JFXButton btnAdd;
    public JFXButton btnClr;
    public ImageView img;
    public TextField txtSalary;
    public TextField txtAttend;
    public Label lbl1;
    public Label lblEmp;
    public Label lbl3;
    public Label lbl5;
    public TextField txtID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        addTextChangeListener(txtID);
        addTextChangeListener(txtAttend);
        addTextChangeListener(txtSalary);
        addTextChangeListener(txtBonus);
    }
    private void addAnimations() {
        Pulse pulse = new Pulse(img);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setSpeed(0.3);
        pulse.play();

        new Wobble(lbl1).play();
        new Wobble(lbl2).play();
        new Wobble(lbl3).play();
        new Wobble(lbl4).play();
        new Wobble(lbl5).play();
        new Wobble(lbl6).play();
        new Wobble(lblEmp).play();
        new Wobble(lblS).play();

        new Wobble(txtID).play();
        new Wobble(txtAttend).play();
        new Wobble(txtSalary).play();
        new Wobble(txtBonus).play();

        new FadeIn(img).play();

        new Wobble(btnAdd).play();
        new Wobble(btnUpd).play();
        new Wobble(btnClr).play();

        new Wobble(tblSalary).play();

    }
    private void addTextChangeListener(TextField textField) {
        // Change the border color to green when the user types into the TextField
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #005436;");
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
        clearHoverEffect(btnClr);
        updateHoverEffect(btnUpd);
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
    public void updateSalaryBtnOnAction(ActionEvent actionEvent) {
    }
    public void addSalaryBtnOnAction(ActionEvent actionEvent) {
    }
    public void clearBtnOnAction(ActionEvent actionEvent) {
    }
}

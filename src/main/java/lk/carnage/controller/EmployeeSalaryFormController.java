package lk.carnage.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Pulse;
import animatefx.animation.Wobble;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import lk.carnage.model.EmpSalary;
import lk.carnage.model.tm.EmpSalaryTm;
import lk.carnage.repository.EmpAttendRepo;
import lk.carnage.repository.EmployeeRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
    public TextField txtMonth;
    public Label lbl41;
    public Label lblInfo;
    public TableColumn colFSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAnimations();
        addHoverEffects();

        addTextChangeListener(txtID);
        addTextChangeListener(txtAttend);
        addTextChangeListener(txtSalary);
        addTextChangeListener(txtBonus);

        txtMonth.setText("24");
        txtSalary.setText("0");
        txtBonus.setText("0");
        lblSalary.setText("0.00");

        txtAttend.setEditable(false);

        loadAllSalaries();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colAttd.setCellValueFactory(new PropertyValueFactory<>("attend"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        colFSalary.setCellValueFactory(new PropertyValueFactory<>("finalSalary"));
    }

    private void loadAllSalaries() {
        ObservableList<EmpSalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<EmpSalary> allSalaries = EmployeeRepo.getAllSalaries();
            for (EmpSalary salary : allSalaries) {
                EmpSalaryTm tm = new EmpSalaryTm(
                        salary.getEmpID(),
                        salary.getAttend(),
                        salary.getSalary(),
                        salary.getBonus(),
                        salary.getFinalSalary()
                );
                obList.add(tm);
            }
            tblSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        String empID = txtID.getText();
        String attend = txtAttend.getText();
        String salary = txtSalary.getText();
        String bonus = txtBonus.getText();
        String finalSalary = lblSalary.getText();

        EmpSalary empSalary = new EmpSalary(empID, attend, salary, bonus, finalSalary);

        try {
            boolean isUpdated = EmployeeRepo.updateSalary(empSalary);
            if (isUpdated) {
                lblInfo.setText("Salary updated successfully");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearBtnOnAction(actionEvent);
                loadAllSalaries();
                txtID.requestFocus();
            }
        } catch (SQLException e) {
            lblInfo.setText("Failed to update salary");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }

    public void addSalaryBtnOnAction(ActionEvent actionEvent) {
        String empID = txtID.getText();
        String attend = txtAttend.getText();
        String salary = txtSalary.getText();
        String bonus = txtBonus.getText();
        String finalSalary = lblSalary.getText();

        if (empID.isEmpty() || attend.isEmpty() || salary.isEmpty() || bonus.isEmpty() || finalSalary.isEmpty()) {
            lblInfo.setText("Please fill all the fields");
            return;
        }

        try {
            // Check if the employee ID already exists in the database
            boolean isExists = EmployeeRepo.isEmployeeExists(empID);
            if (isExists) {
                lblInfo.setText("Employee ID already exists");
                lblInfo.setStyle("-fx-text-fill: red;");
                return;
            }
        } catch (SQLException e) {
            lblInfo.setText("Failed to check employee ID");
            lblInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        EmpSalary empSalary = new EmpSalary(empID, attend, salary, bonus, finalSalary);

        try {
            boolean isSaved = EmployeeRepo.saveSalary(empSalary);
            if (isSaved){
                lblInfo.setText("Salary added successfully");
                lblInfo.setStyle("-fx-text-fill: green;");
                clearBtnOnAction(actionEvent);
                loadAllSalaries();
                txtID.requestFocus();
            }
        }catch (SQLException e){
            lblInfo.setText("Failed to add salary");
            lblInfo.setStyle("-fx-text-fill: red;");
        }
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        txtID.clear();
        txtSalary.clear();
        txtAttend.clear();
        txtBonus.clear();
        lblName.setText("");
        lblInfo.setText("");
    }

    public void txtEmpIdOnAction(ActionEvent actionEvent) {
        txtID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String empID = txtID.getText();
                try {
                    String name = EmployeeRepo.getEmployeeName(empID);
                    List<String> count = EmpAttendRepo.getEmployeeAttend(empID);
                    System.out.println(count);

                    int noOfDays = count.size();
                    txtAttend.setText(String.valueOf(noOfDays));

                    txtSalary.setText("40000.00");

                    txtSalary.requestFocus();

                    if (name != null) {
                        lblName.setText(name);
                        lblInfo.setText("");
                    } else {
                        lblInfo.setText("Invalid Employee ID");
                    }
                } catch (SQLException e) {
                    lblInfo.setText("Error retrieving employee name");
                }
            } else {
                lblInfo.setText("Invalid Employee ID");
            }
        });
    }

    public void bonusTxtOnAction(ActionEvent actionEvent) {
        String salary = txtSalary.getText();
        String bonus = txtBonus.getText();

        int days = Integer.parseInt(txtAttend.getText());
        int monthly = Integer.parseInt(txtMonth.getText());

        double tot = 0.0;

        if (days < monthly){
            double income = ((double)days/monthly) * Double.parseDouble(salary);
            tot = income;
        }else {
            tot = Double.parseDouble(salary);
        }

        String total = String.valueOf(tot + Double.parseDouble(bonus));

        txtBonus.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                lblSalary.setText(total);
            }
        });
    }

    public void salaryTxtOnAction(ActionEvent actionEvent) {
        String salary = txtSalary.getText();
        String bonus = txtBonus.getText();

        int days = Integer.parseInt(txtAttend.getText());
        int monthly = Integer.parseInt(txtMonth.getText());

        double tot = 0.0;

        if (days < monthly){
            double income = ((double)days/monthly) * Double.parseDouble(salary);
            tot = income;
        }else {
            tot = Double.parseDouble(salary);
        }

        String total = String.valueOf(tot + Double.parseDouble(bonus));

        txtSalary.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                lblSalary.setText(total);
                txtBonus.requestFocus();
            }
        });
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        EmpSalaryTm empSalaryTm = (EmpSalaryTm) tblSalary.getSelectionModel().getSelectedItem();
        txtID.setText(empSalaryTm.getEmpID());
        txtAttend.setText(empSalaryTm.getAttend());
        txtSalary.setText(empSalaryTm.getSalary());
        txtBonus.setText(empSalaryTm.getBonus());
        lblSalary.setText(empSalaryTm.getFinalSalary());
    }
}





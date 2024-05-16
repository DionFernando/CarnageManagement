package lk.carnage.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.carnage.db.DbConnection;
import lk.carnage.repository.OrderRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.Sides;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {
    public Label llblItems;
    public JFXButton btnPrintBill;
    public Label llblItemCount;
    public Label llblCusName;
    public Label llblOrderID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
    }

    private void setValues() {
        setOrderID();
        setName();
        setItemCount();
    }

    private void setItemCount() {
        System.out.println(OrdersFormController.rowCount);
        llblItemCount.setText(String.valueOf(OrdersFormController.rowCount));
    }

    private void setName() {
        try {
            String name = OrderRepo.getCurrentName();
            llblCusName.setText(name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setOrderID() {
        try {
            String currentId = OrderRepo.getCurrentId();
            llblOrderID.setText(currentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void PrintBillBtnOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        System.out.println("Bill Printed");

        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/CarnageReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("NetTotal", "123"); //===============================

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());

        //===================

        PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService specificPrinter = null;
        for (PrintService service : services) {
            if (service.getName().equals("PRT80")) {
                specificPrinter = service;
                break;
            }
        }

        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(Sides.DUPLEX);
        attributes.add(PrintQuality.HIGH);

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(attributes);
        configuration.setPrintService(specificPrinter);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);

        exporter.setConfiguration(configuration);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}

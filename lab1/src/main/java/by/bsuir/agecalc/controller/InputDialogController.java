package by.bsuir.agecalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputDialogController {
    @FXML private TextField txtDay;
    @FXML private TextField txtMonth;
    @FXML private TextField txtYear;
    @FXML private Label lblDialogError;

    private MainController mainController;
    private Stage dialogStage;

    public void setMainController(MainController c) { this.mainController = c; }
    public void setDialogStage(Stage s) { this.dialogStage = s; }
    public Stage getDialogStage() { return dialogStage; }

    public void setValues(Integer day, Integer month, Integer year) {
        txtDay.setText(day != null ? String.valueOf(day) : "");
        txtMonth.setText(month != null ? String.valueOf(month) : "");
        txtYear.setText(year != null ? String.valueOf(year) : "");
        lblDialogError.setText("");
    }

    @FXML
    private void handleOk() {
        try {
            int day = Integer.parseInt(txtDay.getText().trim());
            int month = Integer.parseInt(txtMonth.getText().trim());
            int year = Integer.parseInt(txtYear.getText().trim());
            mainController.onDateEntered(day, month, year);
            dialogStage.close();
        } catch (NumberFormatException e) {
            lblDialogError.setText("Введите целые числа");
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
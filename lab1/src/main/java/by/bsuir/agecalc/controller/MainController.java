package by.bsuir.agecalc.controller;

import by.bsuir.agecalc.model.AgeModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainController implements AgeModel.ModelListener {
    @FXML private Label lblBirthDate;
    @FXML private Label lblResult;
    @FXML private Label lblError;

    private final AgeModel model = new AgeModel();
    private InputDialogController inputDialogController;

    @FXML
    public void initialize() {
        model.addListener(this);
    }

    @FXML
    private void handleOpenInput() {
        try {
            if (inputDialogController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/view/InputDialog.fxml"));
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Ввод даты рождения");
                dialog.setScene(new Scene(loader.load()));

                inputDialogController = loader.getController();
                inputDialogController.setMainController(this);
                inputDialogController.setDialogStage(dialog);
            }

            LocalDate last = model.getBirthDate();
            if (last != null) {
                inputDialogController.setValues(
                        last.getDayOfMonth(), last.getMonthValue(), last.getYear());
            } else {
                inputDialogController.setValues(null, null, null);
            }

            inputDialogController.getDialogStage().showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDateEntered(int day, int month, int year) {
        model.setData(day, month, year);
    }

    @Override
    public void onModelChanged() {
        String err = model.getErrorMessage();
        if (err != null) {
            lblError.setText(err);
            lblResult.setText("—");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(err);
            alert.showAndWait();
            return;
        }
        lblError.setText("");
        LocalDate bd = model.getBirthDate();
        if (bd != null) {
            lblBirthDate.setText("Дата рождения: " + bd);
        }
        AgeModel.AgeResult r = model.getResult();
        if (r != null) {
            lblResult.setText(String.format(
                    "Лет: %d%nМесяцев: %d%nДней: %d%n%nВсего дней: %d%nВсего минут: %,d",
                    r.years(), r.months(), r.days(), r.totalDays(), r.totalMinutes()));
        }
    }
}
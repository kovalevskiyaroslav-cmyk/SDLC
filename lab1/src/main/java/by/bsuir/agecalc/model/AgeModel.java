package by.bsuir.agecalc.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AgeModel {
    public interface ModelListener {
        void onModelChanged();
    }

    private LocalDate birthDate;
    private AgeResult result;
    private String errorMessage;
    private final List<ModelListener> listeners = new ArrayList<>();

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ModelListener l : listeners) l.onModelChanged();
    }

    public void setData(int day, int month, int year) {
        errorMessage = null;
        result = null;

        if (month < 1 || month > 12) {
            errorMessage = "Месяц должен быть в диапазоне 1–12";
            notifyListeners();
            return;
        }
        if (day < 1 || day > 31) {
            errorMessage = "День должен быть в диапазоне 1–31";
            notifyListeners();
            return;
        }
        if (year < 1900 || year > LocalDate.now().getYear()) {
            errorMessage = "Год должен быть в диапазоне 1900–" + LocalDate.now().getYear();
            notifyListeners();
            return;
        }

        try {
            LocalDate date = LocalDate.of(year, month, day);
            if (date.isAfter(LocalDate.now())) {
                errorMessage = "Дата рождения не может быть в будущем";
                notifyListeners();
                return;
            }
            this.birthDate = date;
            calculateAge();
        } catch (java.time.DateTimeException e) {
            errorMessage = "Указанной даты не существует в григорианском календаре!";
        }
        notifyListeners();
    }

    private void calculateAge() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthDate, now);
        long totalDays = ChronoUnit.DAYS.between(birthDate, now);
        long totalMinutes = ChronoUnit.MINUTES.between(
                birthDate.atStartOfDay(), now.atStartOfDay());

        result = new AgeResult(
                period.getYears(),
                period.getMonths(),
                period.getDays(),
                totalDays,
                totalMinutes
        );
    }

    public LocalDate getBirthDate() { return birthDate; }
    public AgeResult getResult() { return result; }
    public String getErrorMessage() { return errorMessage; }

    public record AgeResult(int years, int months, int days,
                            long totalDays, long totalMinutes) {}
}
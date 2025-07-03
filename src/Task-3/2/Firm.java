import java.util.ArrayList;
import java.util.List;

public class Firm {
    private String nameFirm;
    private String nameDirector = "Ivanov Ivan";
    private final String locateFirm = "Russia";
    private List<Worker> workers;

    public Firm(String nameFirm) {
        this.nameFirm = nameFirm;
        workers = new ArrayList<>();
    }
    public void addWorker(Worker worker) {
        if (worker != null && !workers.contains(worker)) {
            workers.add(worker);
        }
    }

    public double calculateTotalSalaryExpenses() {
        double total = 0.0;
        for (Worker worker : workers) {
            total += worker.totalSalary();
        }
        return total;
    }

    public void removeWorker (Worker worker){
        workers.remove(worker);
    }
    public void switchNameDirector(String name){
        nameDirector = name;
    }

    public void setNameFirm(String nameFirm1) {
        this.nameFirm = nameFirm1;
    }

    public void setNameDirector(String nameDirector) {
        this.nameDirector = nameDirector;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    @Override
    public String toString() {
        return "Firm{" +
                "locateFirm='" + locateFirm + '\'' +
                ", nameFirm='" + nameFirm + '\'' +
                ", nameDirector='" + nameDirector + '\'' +
                ", workers=" + workers +
                '}';
    }
}

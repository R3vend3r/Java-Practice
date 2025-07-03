public class Analyst extends Worker {
    private static final double BASE_BONUS = 600.0;
    private int completedReports;

    public Analyst(String name, String surname, RankType rank, Salary baseSalary) {
        super(name, surname, rank, baseSalary);
        addBonus(BASE_BONUS);
    }

    @Override
    public double calculateBonus() {
        return bonus + (completedReports * 100);
    }

    public void addCompletedReport() {
        completedReports++;
        addBonus(100);
    }
}
public class DataScientist extends Worker {
    private static final double BASE_BONUS = 2000.0;
    private int completedModels;

    public DataScientist(String name, String surname, RankType rank, Salary baseSalary) {
        super(name, surname, rank, baseSalary);
        addBonus(BASE_BONUS);
    }

    @Override
    public double calculateBonus() {
        return bonus + (completedModels * 3000);
    }

    public void addCompletedModel() {
        completedModels++;
        addBonus(3000);
    }
}
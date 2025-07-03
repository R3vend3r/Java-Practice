public class BackendDev extends Worker {
    private static final double BASE_BONUS = 1000.0;
    public BackendDev(String name, String surname, RankType rank, Salary baseSalary) {
        super(name, surname, rank, baseSalary);
    }
    @Override
    public double calculateBonus() {
        double totalBonus = this.bonus;

        totalBonus += calculateBaseSalary() * 0.05;

        return totalBonus;
    }

    public void addProjectBonus(int completedProjects) {
        if (completedProjects > 2) {
            addBonus(300 * (completedProjects - 2));
        }
    }
}

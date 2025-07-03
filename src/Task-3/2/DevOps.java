public class DevOps extends Worker {
    private static final double BASE_BONUS = 1500.0;
    private int deployedProjects;
    public DevOps(String name, String surname, RankType rank, Salary baseSalary) {
        super(name, surname, rank, baseSalary);
        addBonus(BASE_BONUS);
    }

    @Override
    public double calculateBonus() {
        return bonus + (deployedProjects * 200);
    }

    public void addDeployedProject() {
        deployedProjects++;
        addBonus(5000);
    }

    public int getDeployedProjects() {
        return deployedProjects;
    }
}
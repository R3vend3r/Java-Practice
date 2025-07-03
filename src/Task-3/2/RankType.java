public enum RankType {
    JUNIOR(1.0),
    MIDDLE(2.0),
    SENIOR(3.2);
    private final double salaryMultiplier;

    RankType(double salaryMultiplier) {
        this.salaryMultiplier = salaryMultiplier;
    }
    public double getBaseSalaryMultiplier(){
        return salaryMultiplier;
    }
}

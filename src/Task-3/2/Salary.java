public enum Salary {
    BACKEND_DEVELOPER(100_000),
    DEVOPS_ENGINEER(120_000),
    DATA_SCIENTIST(150_000),
    ANALYST(85_000);
    private final double salaryByPost;

    Salary(double salary) {
        this.salaryByPost = salary;
    }

    public double getSalaryByPost(){
        return salaryByPost;
    }
}

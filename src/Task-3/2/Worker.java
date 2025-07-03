public abstract class Worker {
    protected String name;
    protected String surname;
    protected RankType rank;
    protected Salary baseSalary;
    protected double bonus;

    public Worker(String name, String surname, RankType rank, Salary baseSalary) {
        this.name = name;
        this.surname = surname;
        this.rank = rank;
        this.baseSalary = baseSalary;
        bonus = 0;
    }

    public double totalSalary(){
        return calculateBaseSalary() + calculateBonus();
    }

    public double calculateBaseSalary(){
        return baseSalary.getSalaryByPost() * rank.getBaseSalaryMultiplier();
    }

    public double calculateBonus(){
        return bonus;
    }

    public void addBonus(double bonus){
        this.bonus += bonus;
    }
    public void resetBonus(){
        this.bonus = 0;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public RankType getRank() {
        return rank;
    }

    public void setRank(RankType rank) {
        this.rank = rank;
    }

    public Salary getbaseSalary() {
        return baseSalary;
    }


    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rank=" + rank +
                ", baseSalary=" + baseSalary +
                '}';
    }


}

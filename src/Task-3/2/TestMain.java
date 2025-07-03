public class TestMain {
    public static void main(String[] args) {
        Firm firm = new Firm("DobroDelo");

        BackendDev backendDev = new BackendDev("Nik", "Kovalev", RankType.MIDDLE, Salary.BACKEND_DEVELOPER);
        backendDev.addProjectBonus(4);

        DevOps devOps = new DevOps("Ivan", "Malyk", RankType.SENIOR, Salary.DEVOPS_ENGINEER);
        devOps.addDeployedProject();
        devOps.addDeployedProject();

        Analyst analyst = new Analyst("Andrey", "Aspirinov", RankType.JUNIOR, Salary.ANALYST);
        analyst.addCompletedReport();
        analyst.addCompletedReport();
        analyst.addCompletedReport();

        firm.addWorker(backendDev);
        firm.addWorker(devOps);
        firm.addWorker(analyst);

        System.out.println(firm.getWorkers());

        System.out.println("Сумма выплат за месяц всем сотрудникам: " + firm.calculateTotalSalaryExpenses() + " рублей");

    }
}

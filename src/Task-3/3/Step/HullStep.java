public class HullStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Началась работа с корпусом танка");
        return new Hull();
    }
}

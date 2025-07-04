public class TurrelStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Началась работа с башней танка");
        return new Turrel();
    }
}

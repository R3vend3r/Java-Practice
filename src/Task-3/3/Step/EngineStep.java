public class EngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Началась работа с двигателем танка");
        return new Engine();
    }
}

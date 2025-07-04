public class Product implements IProduct {
    @Override
    public void installFirstPart(IProductPart part1) {
        System.out.println("Установка корпуса танка завершена.");
    }

    @Override
    public void installSecondPart(IProductPart part2) {
        System.out.println("Установка двигателя танка завершена");
    }

    @Override
    public void installThirdPart(IProductPart part3) {
        System.out.println("Установка башни танка завершена.");
    }
}

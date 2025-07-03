public class Number {
    public static void main(String[] args) {
        int max = 899;
        int numb1 = 100 + (new java.util.Random()).nextInt(max);
        int numb2 = 100 + (new java.util.Random()).nextInt(max);
        int numb3 = 100 + (new java.util.Random()).nextInt(max);
        System.out.println(numb1 + " " + numb2 + " " + numb3);
        int firstNumb1 = numb1 / 100;
        int firstNumb2 = numb2 / 100;
        int firstNumb3 = numb3 / 100;
        System.out.println("Сумма первых цифр у выведенных трехзначных цифр равна " + (firstNumb1+ firstNumb2 +firstNumb3));
    }
}

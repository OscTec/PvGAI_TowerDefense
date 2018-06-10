public class Test {

    int money;

    Test(int money) {
        this.money = money;
    }

    void tick(Environment e) {
        money++;
        System.out.println(money);
    }


}

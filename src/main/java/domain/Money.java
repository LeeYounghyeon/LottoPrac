package domain;

public class Money {
    public static final int MONEY_PER_LOTTO = 1000;

    private final int money;

    Money(int money) {
        if (money < MONEY_PER_LOTTO || money % MONEY_PER_LOTTO != 0) {
            throw new IllegalArgumentException("로또 금액은 1000원 단위이어야 합니다.");
        }

        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public int getRound() {
        return money / MONEY_PER_LOTTO;
    }
}

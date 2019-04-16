package domain;

import java.util.List;

/**
 * 로또 한장을 의미하는 객체
 */
public class Lotto {
    private final List<LottoNumber> numbers;

    public Lotto(List<LottoNumber> numbers) {
        this.numbers = numbers;
    }

    public List<LottoNumber> getNumbers() {
        return numbers;
    }

    public void printNumbers() {
        System.out.print(String.join(",", numbers.toString()));
        System.out.println();
    }
}

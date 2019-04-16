package domain;

public class LottoNumber {
    private final int no;

    LottoNumber(int no) {
        if (no < 1 || no > 45) {
            throw new IllegalArgumentException("로또 번호는 1-45사이 입니다.");
        }

        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public String toString() {
        return String.valueOf(no);
    }

    public boolean equals(LottoNumber lottoNumber) {
        return lottoNumber.no == no;
    }
}

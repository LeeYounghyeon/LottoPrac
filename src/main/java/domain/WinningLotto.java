package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 당첨 번호를 담당하는 객체
 */
public class WinningLotto {
    private final Lotto lotto;
    private final int bonusNo;

    public WinningLotto(Lotto lotto, int bonusNo) {
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }

    public List<Integer> getIntegers(List<LottoNumber> lotto) {
        List<Integer> list = new ArrayList<>();

        for (LottoNumber lottoNumber : lotto) {
            list.add(lottoNumber.getNo());
        }

        return list;
    }

    public Rank match(Lotto userLotto) {
        int conut = 0;
        List<Integer> list = getIntegers(lotto.getNumbers());

        for (int i = 0; i < lotto.getNumbers().size(); i++) {
            if (lotto.getNumbers().contains(userLotto.getNumbers().get(i))) {
                conut++;
            }
        }

        boolean matchBouns = list.contains(bonusNo);

        return Rank.valueOf(conut, matchBouns);
    }
}

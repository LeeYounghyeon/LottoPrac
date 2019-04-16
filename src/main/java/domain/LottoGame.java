package domain;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Pattern;

public class LottoGame {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final String PATTERN = "^[0-9]*$";
    private static final int MAX_LOTTO_NUMBER = 45;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int LOTTO_NUMBER_SIZE = 6;

    public void run() {
        Money lottoMoney = getLottoMoney();
        List<Lotto> lottoList = getLottoList(lottoMoney);
        List<LottoNumber> winnerLotto;
        WinningLotto winningLotto;
        printLottoList(lottoList);
        winnerLotto = getLastNumberList();
        winningLotto = getWinningLotto(winnerLotto);
    }

    private WinningLotto getWinningLotto(List<LottoNumber> lottoNumberList) {
        Lotto lotto = new Lotto(lottoNumberList);
        int bonus = getBonusNumber().getNo();

        if (checkOverLastNumber(lottoNumberList, bonus)) {
            return getWinningLotto(lottoNumberList);
        }

        WinningLotto winningLotto = new WinningLotto(lotto, bonus);

        return winningLotto;
    }


    private boolean checkOverLastNumber(List<LottoNumber> lottoNumberList, int bonusBall) {
        List<Integer> lottoList = new ArrayList<>();

        for (LottoNumber lottoNumber : lottoNumberList) {
            lottoList.add(lottoNumber.getNo());
        }

        if (lottoList.contains(bonusBall)) {
            System.err.println("당첨 번호와 중복됩니다.");
            return true;
        }

        return false;
    }


    private boolean checkNotInteger(String number) {
        if (!Pattern.matches(PATTERN, number)) {
            System.err.println("문자가 있습니다.");
            return true;
        }

        return false;
    }

    private LottoNumber validBounsNumber(int bonusBall) {
        try {
            LottoNumber lottoNumber = new LottoNumber(bonusBall);
            return lottoNumber;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return getBonusNumber();
        }
    }

    private LottoNumber getBonusNumber() {
        System.out.println("보너스 볼을 입력해 주세요.");
        String bonusBall = SCAN.nextLine();

        if (checkNotInteger(bonusBall)) {
            return getBonusNumber();
        }

        LottoNumber lottoNumber = validBounsNumber(Integer.parseInt(bonusBall));

        return lottoNumber;
    }

    private List<Boolean> addResult(String[] array) {
        List<Boolean> checkResult = new ArrayList<>();

        for (String number : array) {
            checkResult.add(!Pattern.matches(PATTERN, number));
        }

        return checkResult;
    }

    private boolean checkInChar(String[] array) {
        List<Boolean> checkResult = addResult(array);

        if (checkResult.contains(true)) {
            System.err.println("문자가 입력되었습니다.");
            return true;
        }

        return false;
    }

    private boolean checkLength(String[] array) {
        if (array.length != 6) {
            System.err.println("6개의 숫자를 입력해주세요.");
            return true;
        }

        return false;
    }

    private boolean checkContainComma(String input) {
        if (!input.contains(",")) {
            System.err.println(" ,가 존재하지 않습니다.");
            return true;
        }

        return false;
    }

    private List<LottoNumber> tryLottoNumbers(String[] lastNumberArr) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();

        try {
            addLottoNumbers(lottoNumbers, lastNumberArr);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return getLastNumberList();
        }

        return lottoNumbers;
    }

    private List<LottoNumber> makeLottoNumberList(String[] lastNumberArr) {
        if (checkInChar(lastNumberArr) || checkLength(lastNumberArr)) {
            return getLastNumberList();
        }

        List<LottoNumber> lottoNumbers = tryLottoNumbers(lastNumberArr);

        return lottoNumbers;
    }

    private List<LottoNumber> getLastNumberList() {
        String[] lastNumberArr = inputLastNumbers();
        List<LottoNumber> lottoNumbers = makeLottoNumberList(lastNumberArr);

        if (isDuplicate(lottoNumbers)) {
            System.err.println("값이 중복되었습니다.");
            return getLastNumberList();
        }

        return lottoNumbers;
    }

    private String[] inputLastNumbers() {
        System.out.println("지난 주 당첨 번호를 입력해 주세요.");
        String lastNumber = SCAN.nextLine();
        String[] lastNumberArr = lastNumber.split(",");

        if (isEmpty(lastNumber) || checkContainComma(lastNumber)) {
            return inputLastNumbers();
        }

        return lastNumberArr;
    }

    private void printLottoList(List<Lotto> lottoList) {
        for (Lotto lotto : lottoList) {
            lotto.printNumbers();
        }

        System.out.println();
    }

    private Lotto getOneLotto() {
        Lotto lotto = new Lotto(getOneLottoNumber());
        return lotto;
    }

    private List<Lotto> getLottoList(Money money) {
        List<Lotto> lottoList = new ArrayList<>();
        int round = money.getRound();
        System.out.println(round + "개를 구매했습니다.");

        for (int i = 0; i < round; i++) {
            lottoList.add(getOneLotto());
        }

        return lottoList;
    }

    private boolean isDuplicate(List<LottoNumber> lottoNumberList) {
        Set<Integer> numberSet = new HashSet<>();

        for (LottoNumber lottoNumber : lottoNumberList) {
            numberSet.add(lottoNumber.getNo());
        }

        return numberSet.size() != lottoNumberList.size();
    }

    private void makeRandomNumberList(List<LottoNumber> lotto) {
        for (int i = 0; i < LOTTO_NUMBER_SIZE; i++) {
            LottoNumber lottoNumber = new LottoNumber(getRandomNumber());
            lotto.add(lottoNumber);
        }
    }

    private List<LottoNumber> getOneLottoNumber() {
        List<LottoNumber> lottoNumberList = new ArrayList<>();

        makeRandomNumberList(lottoNumberList);

        if (isDuplicate(lottoNumberList)) {
            return getOneLottoNumber();
        }

        return lottoNumberList;
    }

    private void addLottoNumbers(List<LottoNumber> lottoNumberList, String[] lastNumberArray) throws IllegalArgumentException {
        for (String s : lastNumberArray) {
            LottoNumber lottoNumber = new LottoNumber(Integer.parseInt(s));
            lottoNumberList.add(lottoNumber);
        }
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(MAX_LOTTO_NUMBER) + MIN_LOTTO_NUMBER;
    }

    private boolean checkValid(String money) {
        return isCharcter(money) || isEmpty(money);
    }

    private boolean isEmpty(String money) {
        String moneyStr = String.valueOf(money);

        if (moneyStr.isEmpty()) {
            System.err.println("아무것도 입력되지 않았습니다.");
            return true;
        }

        return false;
    }

    private boolean isCharcter(String money) {
        if (!Pattern.matches(PATTERN, money)) {
            System.err.println("로또 금액은 숫자만 입력되어야 합니다.");
            return true;
        }

        return false;
    }

    private Money makeMoney(int money) {
        Money lottoMoney;

        try {
            lottoMoney = new Money(money);
            return lottoMoney;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return getLottoMoney();
        }
    }

    private Money getLottoMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        String lottoMoney = SCAN.nextLine();
        System.out.println();

        if (checkValid(lottoMoney)) {
            return getLottoMoney();
        }

        return makeMoney(Integer.parseInt(lottoMoney));
    }
}

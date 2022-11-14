package lotto.controller;

import lotto.domain.*;
import lotto.domain.generator.LottoGameResultGenerator;
import lotto.domain.generator.LottoGenerator;
import lotto.domain.generator.WinningNumbersGenerator;
import lotto.domain.validator.BonusNumberValidator;
import lotto.domain.validator.MoneyValidator;
import lotto.io.Inputter;
import lotto.io.MessagePrinter;

import java.util.List;

public class LottoGameSimulator {
    private LottoGameResult lottoGameResult;
    private List<Lotto> Lottos;
    private WinningNumbers winningNumbers;
    private int paidMoney;

    public LottoGameSimulator() {
    }

    public void run() {
        try {
            buyLotto();
            printLottos();
            selectWinningNumbers();
            selectBonusNumber();
            generateResult();
            printResult();
        } catch (IllegalArgumentException exception) {
            MessagePrinter.printErrorMessage(exception);
        }
    }

    public void buyLotto() {
        String moneyInput = getInputOfMoney();
        validateMoney(moneyInput);
        paidMoney = Integer.parseInt(moneyInput);
        generateLottos();
    }

    public String getInputOfMoney() {
        MessagePrinter.printMoneyInputRequest();
        String moneyInput = Inputter.readLine();
        return moneyInput;
    }

    public void validateMoney(String moneyInput) {
        MoneyValidator.validate(moneyInput);
    }

    public void generateLottos() {
        Lottos = LottoGenerator.generateLottos(paidMoney);
    }

    public void printLottos() {
        MessagePrinter.printGeneratedLottoQuantity(paidMoney);
        MessagePrinter.printGeneratedLottoNumbers(Lottos);
    }

    public void selectWinningNumbers() {
        String winningNumbersInput = getInputOfWinningNumbers();
        generateWinningNumbers(winningNumbersInput);
    }

    public String getInputOfWinningNumbers() {
        MessagePrinter.printWinningNumbersInputRequest();
        String winningNumbersInput = Inputter.readLine();
        return winningNumbersInput;
    }

    public void generateWinningNumbers(String winningNumbersInput) {
        winningNumbers = WinningNumbersGenerator.generateWinningNumbers(winningNumbersInput);
    }

    public void selectBonusNumber() {
        String bonusNumberInput = getInputOfBonusNumber();
        validateBonusNumber(bonusNumberInput);
        registerBonusNumberToWinningNumbers(bonusNumberInput);
    }

    public String getInputOfBonusNumber() {
        MessagePrinter.printBonusNumberInputRequest();
        String bonusNumberInput = Inputter.readLine();
        return bonusNumberInput;
    }

    public void validateBonusNumber(String bonusNumberInput) {
        BonusNumberValidator.validate(bonusNumberInput, winningNumbers);
    }

    public void registerBonusNumberToWinningNumbers(String bonusNumberInput) {
        int bonusNumber = Integer.parseInt(bonusNumberInput);
        winningNumbers.registerBonusNumber(bonusNumber);
    }

    public void generateResult() {
        LottoGameResultGenerator lottoGameResultGenerator = new LottoGameResultGenerator();
        lottoGameResult = lottoGameResultGenerator
                .generateLottoGameResult(Lottos, winningNumbers, paidMoney);
    }

    public void printResult() {
        MessagePrinter.printWinningDetails(lottoGameResult.getWinningDetails());
        MessagePrinter.printEarningRate(lottoGameResult.getEarningRate());
    }
}

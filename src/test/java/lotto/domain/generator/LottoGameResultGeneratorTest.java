package lotto.domain.generator;

import lotto.domain.Lotto;
import lotto.domain.WinningNumbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static lotto.domain.Rank.FIRST;
import static lotto.domain.Rank.SECOND;
import static lotto.domain.Rank.THIRD;
import static lotto.domain.Rank.FOURTH;
import static lotto.domain.Rank.FIFTH;

public class LottoGameResultGeneratorTest {
    private LottoGameResultGenerator lottoGameResultGenerator = new LottoGameResultGenerator();

    @Nested
    class WinningCountCalculatingTest {
        private Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        @DisplayName("당첨번호가 하나도 안 겹치는 경우")
        @Test
        void case1() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(7, 8, 9, 10, 11, 12));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(0);
        }

        @DisplayName("당첨번호가 하나만 겹치는 경우")
        @Test
        void case2() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(6, 7, 8, 9, 10, 11));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(1);
        }
        @DisplayName("당첨번호가 두 개 겹치는 경우")
        @Test
        void case3() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(5, 6, 7, 8, 9, 10));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(2);
        }

        @DisplayName("당첨번호가 세 개 겹치는 경우")
        @Test
        void case4() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(4, 5, 6, 7, 8, 9));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(3);
        }

        @DisplayName("당첨번호가 네 개 겹치는 경우")
        @Test
        void case5() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(3, 4, 5, 6, 7, 8));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(4);
        }

        @DisplayName("당첨번호가 다섯 개 겹치는 경우")
        @Test
        void case6() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(2, 3, 4, 5, 6, 7));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(5);
        }

        @DisplayName("당첨번호가 여섯 개 겹치는 경우")
        @Test
        void case7() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6));

            int winningCount = lottoGameResultGenerator.calculateWinningCount(lotto, winningNumbers);

            assertThat(winningCount).isEqualTo(6);
        }
    }

    @Nested
    class PrizeMoneyGettingTest {
       private WinningNumbers winningNumbers;

        @BeforeEach
        void initWinningNumbers() {
            winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6));
            winningNumbers.registerBonusNumber(7);
        }

        @DisplayName("6개가 일치할 때 1등상금을 가져오는지 테스트")
        @Test
        void case1() {
             Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
             int winningCount = 6;

             int prizeMoney = lottoGameResultGenerator.
                     getPrizeMoneyByWinningCount(lotto, winningNumbers, winningCount);

             assertThat(prizeMoney).isEqualTo(FIRST.prizeMoney());
        }

        @DisplayName("5개가 일치하고 보너스번호가 일치할 때 2등상금을 가져오는지 테스트")
        @Test
        void case2() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
            int winningCount = 5;

            int prizeMoney = lottoGameResultGenerator.
                    getPrizeMoneyByWinningCount(lotto, winningNumbers, winningCount);

            assertThat(prizeMoney).isEqualTo(SECOND.prizeMoney());
        }

        @DisplayName("5개가 일치하지만 보너스번호가 불일치할 때 3등상금을 가져오는지 테스트")
        @Test
        void case3() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));
            int winningCount = 5;

            int prizeMoney = lottoGameResultGenerator.
                    getPrizeMoneyByWinningCount(lotto, winningNumbers, winningCount);

            assertThat(prizeMoney).isEqualTo(THIRD.prizeMoney());
        }

        @DisplayName("4개가 일치할 때 4등상금을 가져오는지 테스트")
        @Test
        void case4() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 7, 8));
            int winningCount = 4;

            int prizeMoney = lottoGameResultGenerator.
                    getPrizeMoneyByWinningCount(lotto, winningNumbers, winningCount);

            assertThat(prizeMoney).isEqualTo(FOURTH.prizeMoney());
        }

        @DisplayName("3개가 일치할 때 5등상금을 가져오는지 테스트")
        @Test
        void case5() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 7, 8, 9));
            int winningCount = 3;

            int prizeMoney = lottoGameResultGenerator.
                    getPrizeMoneyByWinningCount(lotto, winningNumbers, winningCount);

            assertThat(prizeMoney).isEqualTo(FIFTH.prizeMoney());
        }
    }

    @Nested
    class ProfitsCalculatingTest {
        private List<Lotto> lottos = new ArrayList<>();

        @BeforeEach
        void initialize() {
            lottoGameResultGenerator = new LottoGameResultGenerator();
            lottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
            lottos.add(new Lotto(List.of(5, 6, 7, 8, 9, 10)));
            lottos.add(new Lotto(List.of(7, 8, 9, 10, 11, 12)));
            lottos.add(new Lotto(List.of(20, 21, 22, 23, 24, 25)));
        }

        @DisplayName("5등 2번한 경우")
        @Test
        void case1() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 10, 11, 12));
            winningNumbers.registerBonusNumber(7);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(2 * FIFTH.prizeMoney());
        }

        @DisplayName("5등 1번, 4등 1번한 경우")
        @Test
        void case2() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(4, 5, 6, 7, 8, 29));
            winningNumbers.registerBonusNumber(1);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(FIFTH.prizeMoney() + FOURTH.prizeMoney());
        }

        @DisplayName("5등 2번, 2등 1번한 경우")
        @Test
        void case3() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(4, 5, 6, 7, 8, 9));
            winningNumbers.registerBonusNumber(10);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(2 * FIFTH.prizeMoney() + SECOND.prizeMoney());
        }

        @DisplayName("4등 1번, 3등 1번한 경우")
        @Test
        void case4() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(6, 7, 8, 9, 10, 15));
            winningNumbers.registerBonusNumber(1);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(FOURTH.prizeMoney() + THIRD.prizeMoney());
        }

        @DisplayName("3등 1번, 2등 1번한 경우")
        @Test
        void case5() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(6, 7, 8, 9, 10, 11));
            winningNumbers.registerBonusNumber(12);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(THIRD.prizeMoney() + SECOND.prizeMoney());
        }

        @DisplayName("4등 1번, 1등 1번한 경우")
        @Test
        void case6() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(7, 8, 9, 10, 11, 12));
            winningNumbers.registerBonusNumber(40);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(FOURTH.prizeMoney() + FIRST.prizeMoney());
        }

        @DisplayName("1등 1번한 경우")
        @Test
        void case7() {
            WinningNumbers winningNumbers = new WinningNumbers(List.of(20, 21, 22, 23, 24, 25));
            winningNumbers.registerBonusNumber(12);

            lottoGameResultGenerator.calculateWinningDetails(lottos, winningNumbers);
            int profits = lottoGameResultGenerator.calculateProfits();

            assertThat(profits).isEqualTo(FIRST.prizeMoney());
        }
    }
}

package eternal.blue.sams.expenditure;

import com.google.gson.Gson;
import eternal.blue.sams.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public class ExpenditureServiceTest {
    private final BigInteger testShowId = BigInteger.valueOf(404);
    private final BigInteger testAccountantId = BigInteger.valueOf(30014);
    private final Expenditure testExpenditure = new Expenditure(212.50,"Electricity Bill",testShowId);
    private final Expenditure testExpenditure2 = new Expenditure(1250,"Software Developer Payment",testShowId);

    ExpenditureService expenditureService;
    @Mock ExpenditureRepository expenditureRepository;
    @Mock TransactionService transactionService;

    @BeforeEach
    public void setup(){
        openMocks(this);
        expenditureService = new ExpenditureService(expenditureRepository,transactionService);
    }

    @Test
    public void createNewExpenditure(){
        when(expenditureService.getExpendituresByShow(testShowId)).thenReturn(List.of(testExpenditure));
        when(expenditureRepository.save(any())).thenReturn(testExpenditure);

        Expenditure createdExpenditure = expenditureService.createExpenditure(testExpenditure,testAccountantId);
        assertThat(createdExpenditure).isNotNull();
        assertThat(createdExpenditure).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(testExpenditure);
    }

    @Test
    public void getExpenditureByShow(){
        when(expenditureService.getExpendituresByShow(testShowId)).thenReturn(List.of(testExpenditure,testExpenditure2));
        when(expenditureRepository.save(any())).thenReturn(testExpenditure);
        when(expenditureRepository.save(any())).thenReturn(testExpenditure2);

        Expenditure createdExpenditure = expenditureService.createExpenditure(testExpenditure,testAccountantId);
        List<Expenditure> retrievedExpenditureList = expenditureService.getExpendituresByShow(testShowId);

        assertThat(retrievedExpenditureList.size()).isEqualTo(2);
//        assertThat(retrievedExpenditureList.get(0)).isNotEqualTo(createdExpenditure);
    }


}

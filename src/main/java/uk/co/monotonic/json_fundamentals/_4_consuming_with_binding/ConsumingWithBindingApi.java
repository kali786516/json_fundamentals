package uk.co.monotonic.json_fundamentals._4_consuming_with_binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.monotonic.json_fundamentals.common.LoanApplication;

import java.io.File;
import java.io.IOException;

public class ConsumingWithBindingApi
{
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    public static void main(String[] args) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();

        final BasicLoanApplication loanApplication =
            objectMapper.readValue(BANK_LOAN_FILE, BasicLoanApplication.class);

        System.out.println(loanApplication);
    }
}

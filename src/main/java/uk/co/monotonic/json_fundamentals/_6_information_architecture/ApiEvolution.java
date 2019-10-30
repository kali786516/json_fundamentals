package uk.co.monotonic.json_fundamentals._6_information_architecture;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.monotonic.json_fundamentals._4_consuming_with_binding.BasicLoanApplication;
import uk.co.monotonic.json_fundamentals._4_consuming_with_binding.BasicLoanDetails;
import uk.co.monotonic.json_fundamentals.common.Job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApiEvolution
{
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");
    private static final File BANK_LOAN_EVOLVED_FILE = new File("src/main/resources/bank_loan_evolved.json");

    public static void main(String[] args) throws Exception
    {
        try (FileInputStream input = new FileInputStream(BANK_LOAN_EVOLVED_FILE))
        {
            //extractUsingDomApi(input);
            //extractUsingBindingApi(input);
            extractUsingStreamingApi(input);
        }
    }

    private static void extractUsingStreamingApi(final InputStream input) throws IOException
    {
        final JsonFactory jsonFactory = new JsonFactory();
        final JsonParser parser = jsonFactory.createParser(input);

        double totalIncome = 0;
        double amount = 0;

        JsonToken token;
        while ((token = parser.nextToken()) != null)
        {
            final String currentName = parser.getCurrentName();
            if (currentName != null && token.isNumeric())
            {
                switch (currentName)
                {
                    case "annualIncome":
                        totalIncome += parser.getDoubleValue();
                        break;

                    case "amount":
                        amount = parser.getDoubleValue();
                        break;
                }
            }
        }

        System.out.println("totalIncome = " + totalIncome);
        System.out.println("amount = " + amount);
    }

    private static void extractUsingBindingApi(final FileInputStream input) throws Exception
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        final BasicLoanApplication loanApplication =
            objectMapper.readValue(input, BasicLoanApplication.class);

        final double totalIncome =
            loanApplication.getJobs().stream().mapToDouble(Job::getAnnualIncome).sum();
        BasicLoanDetails loanDetails = loanApplication.getLoanDetails();
        if (loanDetails == null) {
            loanDetails = loanApplication.getLoanInfo();
        }
        final double amount = loanDetails.getAmount();

        System.out.println("totalIncome = " + totalIncome);
        System.out.println("amount = " + amount);
    }

    private static void extractUsingDomApi(final InputStream input) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode loanApplication = objectMapper.readTree(input);

        final double totalIncome = getTotalIncomeDom(loanApplication);
        final double amount = getAmountDom(loanApplication);

        System.out.println("totalIncome = " + totalIncome);
        System.out.println("amount = " + amount);
    }

    private static double getAmountDom(final JsonNode loanApplication)
    {
        JsonNode loanDetails = loanApplication.get("loanDetails");
        if (loanDetails == null) {
            loanDetails = loanApplication.get("loanInfo");
        }
        if (loanDetails != null) {
            JsonNode amountNode = loanDetails.get("amount");
            return amountNode.asDouble();
        }
        return 0;
    }

    private static double getTotalIncomeDom(final JsonNode loanApplication)
    {
        double totalIncome = 0;
        final JsonNode jobs = loanApplication.get("jobs");
        if (jobs != null) {
            for (int i = 0; i < jobs.size(); i++)
            {
                final JsonNode job = jobs.get(i);
                totalIncome += job.get("annualIncome").asDouble();
            }
        }
        return totalIncome;
    }
}

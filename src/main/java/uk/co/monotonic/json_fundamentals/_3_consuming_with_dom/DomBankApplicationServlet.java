package uk.co.monotonic.json_fundamentals._3_consuming_with_dom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.monotonic.json_fundamentals.common.SimpleJettyService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DomBankApplicationServlet extends HttpServlet
{
    // Approve the bank loan application if the income if the amount <= 3 * totalIncome, deny otherwise
    // SC_OK to approve, SC_FORBIDDEN to deny.

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(
        final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        final JsonNode loanApplication = objectMapper.readTree(req.getInputStream());
        final double totalIncome = getTotalIncome(loanApplication);
        final double amount = getAmount(loanApplication);

        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.println("TotalIncome = " + totalIncome);
        outputStream.println("Amount = " + amount);

        if (amount <= 3 * totalIncome) {
            resp.setStatus(HttpServletResponse.SC_OK);
            outputStream.println("Approved!");
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            outputStream.println("Denied");
        }
    }

    private double getAmount(final JsonNode loanApplication)
    {
        JsonNode loanDetails = loanApplication.get("loanDetails");
        if (loanDetails != null) {
            JsonNode amountNode = loanDetails.get("amount");
            return amountNode.asDouble();
        }
        return 0;
    }

    private double getTotalIncome(final JsonNode loanApplication)
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

    public static void main(String[] args)
    {
        SimpleJettyService.run(DomBankApplicationServlet.class);
    }
}

package uk.co.monotonic.json_fundamentals._4_consuming_with_binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import uk.co.monotonic.json_fundamentals.common.Job;
import uk.co.monotonic.json_fundamentals.common.LoanApplication;
import uk.co.monotonic.json_fundamentals.common.SimpleJettyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BindingBankApplicationServlet extends HttpServlet
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(
        final HttpServletRequest req, final HttpServletResponse resp) throws IOException
    {
        final BasicLoanApplication loanApplication =
            objectMapper.readValue(req.getInputStream(), BasicLoanApplication.class);

        final double totalIncome =
            loanApplication.getJobs().stream().mapToDouble(Job::getAnnualIncome).sum();
        final double amount = loanApplication.getLoanDetails().getAmount();

        final int status;
        final String message;
        if (amount <= 3 * totalIncome) {
            status = HttpServletResponse.SC_OK;
            message = "approved";
        } else {
            status = HttpServletResponse.SC_FORBIDDEN;
            message = "denied";
        }
        resp.setStatus(status);
        resp.getWriter().println(message);
    }

    public static void main(String[] args)
    {
        SimpleJettyService.run(BindingBankApplicationServlet.class);
    }
}

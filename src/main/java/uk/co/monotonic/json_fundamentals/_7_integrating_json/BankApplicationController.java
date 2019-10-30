package uk.co.monotonic.json_fundamentals._7_integrating_json;

import uk.co.monotonic.json_fundamentals._4_consuming_with_binding.BasicLoanApplication;
import uk.co.monotonic.json_fundamentals.common.Job;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Path("/")
public class BankApplicationController
{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response applyForLoan(
        final BasicLoanApplication loanApplication)
    {
        double totalIncome = loanApplication
            .getJobs().stream().mapToDouble(Job::getAnnualIncome).sum();
        double amount = loanApplication.getLoanDetails().getAmount();

        if (amount <= 3 * totalIncome)
        {
            return Response.ok("approved").build();
        }
        else
        {
            return Response.status(SC_FORBIDDEN).entity("denied").build();
        }
    }
}

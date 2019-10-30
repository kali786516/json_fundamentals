package uk.co.monotonic.json_fundamentals._7_integrating_json;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
public class BankApplicationController
{
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> loanApplication(
        final @RequestBody BasicLoanApplication loanApplication)
    {
        double totalIncome = loanApplication
            .getJobs().stream().mapToDouble(Job::getAnnualIncome).sum();
        double amount = loanApplication.getLoanDetails().getAmount();

        if (amount <= 3 * totalIncome)
        {
            return ResponseEntity.ok("approved");
        }
        else
        {
            return ResponseEntity.status(FORBIDDEN).body("denied");
        }
    }
}

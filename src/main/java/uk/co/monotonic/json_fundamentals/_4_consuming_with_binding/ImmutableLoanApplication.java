package uk.co.monotonic.json_fundamentals._4_consuming_with_binding;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.monotonic.json_fundamentals.common.Job;
import uk.co.monotonic.json_fundamentals.common.LoanDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ImmutableLoanApplication
{
    private final String name;
    private final String purposeOfLoan;
    private final LoanDetails loanDetails;
    private final List<Job> jobs;

    @JsonCreator
    public ImmutableLoanApplication(
        @JsonProperty("name") final String name,
        @JsonProperty("purposeOfLoan") final String purposeOfLoan,
        @JsonProperty("loanDetails") final LoanDetails loanDetails,
        @JsonProperty("jobs") final List<Job> jobs)
    {
        this.name = name;
        this.purposeOfLoan = purposeOfLoan;
        this.loanDetails = loanDetails;
        this.jobs = Collections.unmodifiableList(new ArrayList<>(jobs));
    }

    public String getName()
    {
        return name;
    }

    public String getPurposeOfLoan()
    {
        return purposeOfLoan;
    }

    public LoanDetails getLoanDetails()
    {
        return loanDetails;
    }

    public List<Job> getJobs()
    {
        return jobs;
    }

    @Override
    public String toString()
    {
        return "ImmutableLoanApplication{" +
            "name='" + name + '\'' +
            ", purposeOfLoan='" + purposeOfLoan + '\'' +
            ", loanDetails=\n\t" + loanDetails +
            ", jobs=\n\t" + jobs.stream().map(Job::toString).collect(joining("\n\t\t","[\n\t\t","\n\t]")) +
            '}';
    }
}

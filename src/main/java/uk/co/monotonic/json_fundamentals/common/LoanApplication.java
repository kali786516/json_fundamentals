package uk.co.monotonic.json_fundamentals.common;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class LoanApplication
{
    private String name;
    private String purposeOfLoan;
    private LoanDetails loanDetails;
    private List<Job> jobs;

    public LoanApplication() {
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getPurposeOfLoan()
    {
        return purposeOfLoan;
    }

    public void setPurposeOfLoan(final String purposeOfLoan)
    {
        this.purposeOfLoan = purposeOfLoan;
    }

    public LoanDetails getLoanDetails()
    {
        return loanDetails;
    }

    public void setLoanDetails(final LoanDetails loanDetails)
    {
        this.loanDetails = loanDetails;
    }

    public List<Job> getJobs()
    {
        return jobs;
    }

    public void setJobs(final List<Job> jobs)
    {
        this.jobs = jobs;
    }

    @Override
    public String toString()
    {
        return "LoanApplication{" +
            "name='" + name + '\'' +
            ", purposeOfLoan='" + purposeOfLoan + '\'' +
            ", loanDetails=\n\t" + loanDetails +
            ", jobs=\n\t" + jobs.stream().map(Job::toString).collect(joining("\n\t\t","[\n\t\t","\n\t]")) +
            '}';
    }
}

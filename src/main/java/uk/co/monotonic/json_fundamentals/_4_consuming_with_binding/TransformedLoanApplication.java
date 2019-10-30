package uk.co.monotonic.json_fundamentals._4_consuming_with_binding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.monotonic.json_fundamentals.common.Job;
import uk.co.monotonic.json_fundamentals.common.LoanDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class TransformedLoanApplication
{
    private String applicantName;
    private String purposeOfLoan;
    private LoanDetails loanDetails;
    private Map<String, Job> jobs;

    @JsonProperty("name")
    public String getApplicantName()
    {
        return applicantName;
    }

    @JsonProperty("name")
    public void setApplicantName(final String applicantName)
    {
        this.applicantName = applicantName;
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

    @JsonIgnore
    public Map<String, Job> getJobs()
    {
        return jobs;
    }

    @JsonIgnore
    public void setJobs(final Map<String, Job> jobs)
    {
        this.jobs = jobs;
    }

    @JsonProperty("jobs")
    public List<Job> getJobsJson()
    {
        return new ArrayList<>(jobs.values());
    }

    @JsonProperty("jobs")
    public void setJobsJson(final List<Job> jobs)
    {
        this.jobs = jobs.stream().collect(toMap(Job::getTitle, job -> job));
    }

    @Override
    public String toString()
    {
        return "LoanApplication{" +
            "applicantName='" + applicantName + '\'' +
            ", purposeOfLoan='" + purposeOfLoan + '\'' +
            ", loanDetails=\n\t" + loanDetails +
            ", jobs=\n\t" + jobs +
            '}';
    }
}

package uk.co.monotonic.json_fundamentals.common;

import java.time.LocalDate;

public class LoanDetails
{
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(final double amount)
    {
        this.amount = amount;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public String toString()
    {
        return "LoanDetails{" +
            "amount=" + amount +
            ", startDate='" + startDate + '\'' +
            ", endDate='" + endDate + '\'' +
            '}';
    }
}

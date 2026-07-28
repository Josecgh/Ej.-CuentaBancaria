package dev.jose.accounts;

import java.util.Locale;

public class Account {
    // Protected fields to allow inheritance in subclasses
    protected float balance;
    protected int deposits = 0;
    protected int withdrawals = 0;
    protected float annualRate;
    protected float monthlyFee = 0;

    public Account(float balance, float annualRate) {
        this.balance = balance;
        this.annualRate = annualRate;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getAnnualRate() {
        return this.annualRate;
    }

    public void setAnnualRate(float annualRate) {
        this.annualRate = annualRate;
    }

    public int getDeposits() {
        return this.deposits;
    }

    public int getWithdrawals() {
        return this.withdrawals;
    }

    public float getMonthlyFee() {
        return this.monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void incrementDeposits() {
        this.deposits++;
    }

    public void incrementWithdrawals() {
        this.withdrawals++;
    }

    public void deposit(float amount) {
        if (amount > 0) {
            this.balance += amount;
            incrementDeposits();
        }
    }

    public void withdraw(float amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            incrementWithdrawals();
        }
    }

    public void calculateMonthlyInterest() {
        float monthlyRate = (this.annualRate / 12) / 100;
        float earnedInterest = this.balance * monthlyRate;
        this.balance += earnedInterest;
    }

    public void monthlyStatement() {
        this.balance -= this.monthlyFee;
        this.calculateMonthlyInterest();
    }

    public String printInfo() {
        return String.format(Locale.US,
            "Balance: $%.2f\nNumber of deposits: %d\nNumber of withdrawals: %d\nAnnual rate: %.1f%%\nMonthly fee: $%.2f",
            balance, deposits, withdrawals, annualRate, monthlyFee
        );
    }
}
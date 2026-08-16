package dev.jose.accounts;

public class Account {
    private float balance;
    private int nDeposits = 0;
    private int nWithdrawals = 0;
    private float annualRate;
    private float monthlyFee = 0;

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

    public int getNDeposits() {
        return this.nDeposits;
    }

    public int getNWithdrawals() {
        return this.nWithdrawals;
    }

    public float getMonthlyFee() {
        return this.monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void incrementDeposits() {
        this.nDeposits++;
    }

    public void incrementWithdrawals() {
        this.nWithdrawals++;
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
        return "Balance: $" + balance +
            "\nNumber of deposits: " + nDeposits +
            "\nNumber of withdrawals: " + nWithdrawals +
            "\nAnnual rate: " + annualRate + "%" +
            "\nMonthly fee: $" + monthlyFee;
    }
}
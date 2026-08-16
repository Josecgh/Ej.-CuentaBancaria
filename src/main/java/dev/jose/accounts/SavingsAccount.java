package dev.jose.accounts;

public class SavingsAccount extends Account {
  private boolean active;

  public SavingsAccount(float balance, float annualRate) {
    super(balance, annualRate);
    checkActiveStatus();
  }

  public boolean isActive() {
    return this.active;
  }

  private void checkActiveStatus() {
    this.active = (getBalance() >= 10000.0f);
  }

  @Override
  public void deposit(float amount) {
    if (this.active || (getBalance() + amount) >= 10000.0f) {
      super.deposit(amount);
      checkActiveStatus();
    }
  }

  @Override
  public void withdraw(float amount) {
    checkActiveStatus();
    if (this.active) {
      super.withdraw(amount);
      checkActiveStatus();
    }
  }

  @Override
  public void monthlyStatement() {
    if (getNWithdrawals() > 4) {
      setMonthlyFee(getMonthlyFee() + (getNWithdrawals() - 4) * 1000.0f);
    }
    super.monthlyStatement();
    checkActiveStatus();
  }

  @Override
  public String printInfo() {
      int totalTransactions = getNDeposits() + getNWithdrawals();
      return "Balance: " + getBalance() +
              ", Monthly Commission: " + getMonthlyFee() +
              ", Transactions: " + totalTransactions +
              ", Active: " + this.active;
  }
}
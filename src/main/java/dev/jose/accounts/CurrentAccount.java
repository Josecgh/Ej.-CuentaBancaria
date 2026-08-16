package dev.jose.accounts;

public class CurrentAccount extends Account {
  private float overdraft = 0.0f;

  public CurrentAccount(float balance, float annualRate) {
    super(balance, annualRate);
  }

  public float getOverdraft() {
    return this.overdraft;
  }

  @Override
  public void withdraw(float amount) {
    if (amount <= 0) {
      return;
    }

    if (amount <= getBalance()) {
      super.withdraw(amount);
    } else {
      float deficit = amount - getBalance();
      this.overdraft += deficit;
      setBalance(0.0f); // Fixed: replaced getBalance() = 0.0f
      incrementWithdrawals();
    }
  }

  @Override
  public void deposit(float amount) {
    if (amount <= 0) {
      return;
    }

    if (this.overdraft > 0) {
      if (amount >= this.overdraft) {
        float remainder = amount - this.overdraft;
        this.overdraft = 0.0f;
        super.deposit(remainder);
      } else {
        this.overdraft -= amount;
        incrementDeposits();
      }
    } else {
      super.deposit(amount);
    }
  }

  @Override
  public void monthlyStatement() {
    super.monthlyStatement();
  }

  @Override
  public String printInfo() {
    int totalTransactions = getNDeposits() + getNWithdrawals();
    return "Balance: $" + getBalance() +
            ", Monthly Fee: $" + getMonthlyFee() +
            ", Transactions: " + totalTransactions +
            ", Overdraft: $" + this.overdraft;
  }
}
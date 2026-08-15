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
    this.active = (this.balance >= 10000.0f);
  }

  @Override
  public void deposit(float amount) {
    if (this.active || (this.balance + amount) >= 10000.0f) {
      super.deposit(amount);
      checkActiveStatus();
    }
  }

  @Override
  public void withdraw(float amount) {
    checkActiveStatus();
    if(this.active){
      super.withdraw(amount);
      checkActiveStatus();
    }
  }

  @Override
  public void monthlyStatement() {
    if (this.nWithdrawals > 4) {
      this.monthlyFee += (this.nWithdrawals - 4) * 1000.0f;
    }
    super.monthlyStatement();
    checkActiveStatus();
  }

  @Override
  public String printInfo() {
    int totalTransactions = this.nDeposits + this.nWithdrawals;
    return "Balance: " + this.balance +
            ", Monthly Commission: " + this.monthlyFee +
            ", Transactions: " + totalTransactions +
            ", Active: " + this.active;
  }
}
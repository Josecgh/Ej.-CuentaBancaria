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
    if (this.active) {
      super.deposit(amount);
      checkActiveStatus();
    }
  }

  @Override
  public void withdraw(float amount) {
    if(this.active){
      super.withdraw(amount);
      checkActiveStatus();
    }
  }
}
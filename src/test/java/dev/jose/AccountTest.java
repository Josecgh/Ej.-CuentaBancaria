package dev.jose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jose.accounts.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

  private Account account;

  @BeforeEach
  void setUp() {
    // Initializing an account with $10,000 balance and 12% annual rate
    account = new Account(10000.0f, 12.0f);
  }

  @Test
  void testInitialState() {
    assertEquals(10000.0f, account.getBalance());
    assertEquals(12.0f, account.getAnnualRate());
  }

  @Test
  void testDeposit() {
    account.deposit(5000.0f);
    assertEquals(15000.0f, account.getBalance());
    assertTrue(account.printInfo().contains("Number of deposits: 1"));
  }

  @Test
  void testValidWithdrawal() {
    account.withdraw(3000.0f);
    assertEquals(7000.0f, account.getBalance());
    assertTrue(account.printInfo().contains("Number of withdrawals: 1"));
  }

  @Test
  void testWithdrawalExceedingBalance() {
    // Attempting to withdraw more than the available balance should not modify the
    // balance nor increase withdrawals
    account.withdraw(15000.0f);
    assertEquals(10000.0f, account.getBalance());
    assertTrue(account.printInfo().contains("Number of withdrawals: 0"));
  }

  @Test
  void testCalculateMonthlyInterest() {
    // With a 12% annual rate, the monthly rate is 1%
    // 1% of $10,000 = $100 -> New balance: $10,100
    account.calculateMonthlyInterest();
    assertEquals(10100.0f, account.getBalance(), 0.01f);
  }

  @Test
  void testMonthlyStatement() {
    account.monthlyStatement();
    assertEquals(10100.0f, account.getBalance(), 0.01f);
  }

  @Test
  void testPrintInfo() {
    String result = account.printInfo();
    assertNotNull(result);
    assertTrue(result.contains("Balance: $10000.0"));
    assertTrue(result.contains("Annual rate: 12.0%"));
  }

  @Test
  void testSettersAndGettersCoverage() {
    // Cubre las líneas 20-22 (setBalance)
    account.setBalance(5000.0f);
    assertEquals(5000.0f, account.getBalance());

    // Cubre las líneas 28-30 (setAnnualRate)
    account.setAnnualRate(15.0f);
    assertEquals(15.0f, account.getAnnualRate());

    // Cubre las líneas 32-34 (getNDeposits)
    assertEquals(0, account.getNDeposits());
    account.deposit(100.0f);
    assertEquals(1, account.getNDeposits());
  }
  @Test
  void testInvalidDepositAndWithdrawal() {
    // Probar montos <= 0 para cubrir las ramas faltantes en los if (líneas 57 y 64)
    account.deposit(-50.0f);
    assertEquals(10000.0f, account.getBalance());

    account.withdraw(-50.0f);
    assertEquals(10000.0f, account.getBalance());
  }
}
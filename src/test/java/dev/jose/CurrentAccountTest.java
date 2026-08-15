package dev.jose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jose.accounts.CurrentAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrentAccountTest {

  private CurrentAccount account;

  @BeforeEach
  void setUp() {
    // Cuenta con saldo inicial de $5,000 y tasa del 12%
    account = new CurrentAccount(5000.0f, 12.0f);
  }

  @Test
  void testInitialState() {
    assertEquals(5000.0f, account.getBalance());
    assertEquals(0.0f, account.getOverdraft());
  }

  @Test
  void testNormalWithdrawal() {
    account.withdraw(2000.0f);
    assertEquals(3000.0f, account.getBalance());
    assertEquals(0.0f, account.getOverdraft());
    assertEquals(1, account.getNWithdrawals());
  }

  @Test
  void testWithdrawalWithOverdraft() {
    // Retiro mayor al saldo disponible ($8,000 en una cuenta con $5,000)
    account.withdraw(8000.0f);
    assertEquals(0.0f, account.getBalance());
    assertEquals(3000.0f, account.getOverdraft());
    assertEquals(1, account.getNWithdrawals());
  }

  @Test
  void testDepositPartialOverdraft() {
    account.withdraw(8000.0f); // Genera $3,000 de sobregiro
    
    // Consignación menor al sobregiro ($1,000)
    account.deposit(1000.0f);
    assertEquals(0.0f, account.getBalance());
    assertEquals(2000.0f, account.getOverdraft());
    assertEquals(1, account.getNDeposits());
  }

  @Test
  void testDepositExceedingOverdraft() {
    account.withdraw(8000.0f); // Genera $3,000 de sobregiro
    
    // Consignación mayor al sobregiro ($5,000): paga los $3,000 y $2,000 van al saldo
    account.deposit(5000.0f);
    assertEquals(2000.0f, account.getBalance());
    assertEquals(0.0f, account.getOverdraft());
    assertEquals(1, account.getNDeposits());
  }

  @Test
  void testDepositWithoutOverdraft() {
    account.deposit(2000.0f);
    assertEquals(7000.0f, account.getBalance());
    assertEquals(0.0f, account.getOverdraft());
  }

  @Test
  void testInvalidAmountOperations() {
    // Intentar retirar o consignar montos <= 0 no debe modificar el estado
    account.withdraw(-100.0f);
    assertEquals(5000.0f, account.getBalance());

    account.deposit(-100.0f);
    assertEquals(5000.0f, account.getBalance());
  }

  @Test
  void testMonthlyStatement() {
    account.monthlyStatement();
    // Con 12% anual (1% mensual), $5,000 genera $50 de interés -> $5,050
    assertEquals(5050.0f, account.getBalance(), 0.01f);
  }

  @Test
  void testPrintInfo() {
    account.withdraw(7000.0f); // Saldo: 0, Sobregiro: 2000
    String info = account.printInfo();
    
    assertNotNull(info);
    assertTrue(info.contains("Balance: $0.0"));
    assertTrue(info.contains("Overdraft: $2000.0"));
    assertTrue(info.contains("Transactions: 1"));
  }
}
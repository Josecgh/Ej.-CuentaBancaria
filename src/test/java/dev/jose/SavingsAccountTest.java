package dev.jose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.jose.accounts.SavingsAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SavingsAccountTest {

  private SavingsAccount activeAccount;
  private SavingsAccount inactiveAccount;

  @BeforeEach
  void setUp() {
    // Cuenta activa (>= $10,000) con 12% de tasa anual
    activeAccount = new SavingsAccount(12000.0f, 12.0f);
    // Cuenta inactiva (< $10,000) con 12% de tasa anual
    inactiveAccount = new SavingsAccount(5000.0f, 12.0f);
  }

  @Test
  void testInitialActiveState() {
    assertTrue(activeAccount.isActive());
    assertFalse(inactiveAccount.isActive());
  }

  @Test
  void testDepositWhenActive() {
    activeAccount.deposit(2000.0f);
    assertEquals(14000.0f, activeAccount.getBalance());
    assertTrue(activeAccount.isActive());
  }

  @Test
  void testDepositReactivatingAccount() {
    // Según la lógica con OR (sumar saldo suficiente para llegar a 10000 la activa)
    inactiveAccount.deposit(6000.0f); 
    assertEquals(11000.0f, inactiveAccount.getBalance());
    assertTrue(inactiveAccount.isActive());
  }

  @Test
  void testDepositFailsWhenInactiveAndInsufficient() {
    // Un depósito que no alcanza los 10,000 se ignora
    inactiveAccount.deposit(1000.0f);
    assertEquals(5000.0f, inactiveAccount.getBalance());
    assertFalse(inactiveAccount.isActive());
  }

  @Test
  void testWithdrawWhenActive() {
    activeAccount.withdraw(1000.0f);
    assertEquals(11000.0f, activeAccount.getBalance());
    assertTrue(activeAccount.isActive());
  }

  @Test
  void testWithdrawDeactivatesAccount() {
    // Retiro que reduce el saldo por debajo de 10,000
    activeAccount.withdraw(3000.0f);
    assertEquals(9000.0f, activeAccount.getBalance());
    assertFalse(activeAccount.isActive());
  }

  @Test
  void testWithdrawFailsWhenInactive() {
    inactiveAccount.withdraw(1000.0f);
    assertEquals(5000.0f, inactiveAccount.getBalance());
  }

  @Test
  void testMonthlyStatementWithoutExtraFee() {
    // 4 retiros o menos no deben generar comisión adicional
    for (int i = 0; i < 4; i++) {
      activeAccount.withdraw(100.0f);
    }
    // Saldo previo = 11,600 | Interés del 1% sobre 11,600 = 116 | Saldo final = 11,716
    activeAccount.monthlyStatement();
    assertEquals(11716.0f, activeAccount.getBalance(), 0.01f);
  }

  @Test
  void testMonthlyStatementWithExtraFee() {
    // 5 retiros generarán $1,000 de comisión por el 5to retiro
    for (int i = 0; i < 5; i++) {
      activeAccount.withdraw(100.0f);
    }
    // Saldo previo = 11,500
    // Comisión extra = (5 - 4) * 1000 = $1,000
    // Saldo tras comisión = 10,500
    // Interés del 1% sobre 10,500 = 105 | Saldo final = 10,605
    activeAccount.monthlyStatement();
    assertEquals(10605.0f, activeAccount.getBalance(), 0.01f);
  }

  @Test
  void testPrintInfo() {
    String result = activeAccount.printInfo();
    assertNotNull(result);
    assertTrue(result.contains("Balance: 12000.0"));
    assertTrue(result.contains("Active: true"));
  }
}
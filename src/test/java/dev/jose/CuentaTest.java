package dev.jose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for simple App.
 */
class CuentaTest {
  /**
   * Rigorous Test.
   */
  private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        // Inicializamos una cuenta con saldo $10,000 y tasa anual de 12%
        cuenta = new Cuenta(10000, 12.0f);
    }

    @Test
    void testEstadoInicial() {
        assertEquals(10000.0f, cuenta.getSaldo());
        assertEquals(12.0f, cuenta.getTasaAnual());
    }

    @Test
    void testIngresarEfectivo() {
        cuenta.ingresarEfectivo(5000.0f);
        assertEquals(15000.0f, cuenta.getSaldo());
        assertTrue(cuenta.imprimir().contains("Número de consignaciones: 1"));
    }

    @Test
    void testSacarEfectivoValido() {
        cuenta.sacarEfectivo(3000.0f);
        assertEquals(7000.0f, cuenta.getSaldo());
        assertTrue(cuenta.imprimir().contains("Número de retiros: 1"));
    }

    @Test
    void testSacarEfectivoExcediendoSaldo() {
        // Intentar retirar más dinero del disponible no debe modificar el saldo ni incrementar los retiros
        cuenta.sacarEfectivo(15000.0f);
        assertEquals(10000.0f, cuenta.getSaldo());
        assertTrue(cuenta.imprimir().contains("Número de retiros: 0"));
    }

    @Test
    void testCalcularInteresMensual() {
        // Con tasa anual de 12%, el interés mensual es 1% (12 / 12)
        // 1% de $10,000 = $100 -> Nuevo saldo: $10,100
        cuenta.calcularInteresMensual();
        assertEquals(10100.0f, cuenta.getSaldo(), 0.01f);
    }

    @Test
    void testExtractoMensual() {
        // En tu clase actual comisionMensual es 0
        // Aplica el interés mensual sobre el saldo actual
        cuenta.extractoMensual();
        assertEquals(10100.0f, cuenta.getSaldo(), 0.01f);
    }

    @Test
    void testImprimir() {
      String resultado = cuenta.imprimir();
      assertNotNull(resultado);
      assertTrue(resultado.contains("Saldo: $10000"));
      assertTrue(resultado.contains("Tasa anual: 12.0%"));
    }
}

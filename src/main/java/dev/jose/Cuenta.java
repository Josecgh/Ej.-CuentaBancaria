package dev.jose;

public class Cuenta {
  private float saldo;
  private int consignaciones = 0;
  private int retiros = 0;
  private float tasaAnual;
  private float comisionMensual = 0;

  public Cuenta(int saldo, float tasaAnual) {
    this.saldo = saldo;
    this.tasaAnual = tasaAnual;
  }

  public float getSaldo() {
    return this.saldo;
  }

  public void setSaldo(float saldo) {
    this.saldo = saldo;
  }

  public float getTasaAnual() {
    return this.tasaAnual;
  }

  public void setTasaAnual(float tasaAnual) {
    this.tasaAnual = tasaAnual;
  }

  public String retirarEfectivo(float valor) {
    String mensage = "";
    if (valor > this.saldo) {
      mensage = "No puedes retirar tanto dinero";
    } else if (valor <= 0) {
      mensage = "No has retirado nada";
    } else {
      saldo -= valor;
      this.retiros = retiros + 1;
      mensage = "Retiro exitoso";
    }

    return mensage;
  }

  public String ingresarEfectivo(float valor) {
    String mensaje = "";
    if (valor <= 0) {
      mensaje = "No has ingresado nada";
    } else {
      saldo += valor;
      this.consignaciones = consignaciones + 1;
      mensaje = "Ingreso exitoso";
    }

    return mensaje;
  }
}

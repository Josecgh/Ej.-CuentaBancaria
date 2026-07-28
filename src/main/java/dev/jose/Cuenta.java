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

  public void incrementarConsignacion() {
    this.consignaciones = consignaciones + 1;
  }

  public void incrementarRetiros() {
    this.retiros = retiros + 1;
  }
  
  
}

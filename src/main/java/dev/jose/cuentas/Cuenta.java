package dev.jose.cuentas;

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
  
  public void ingresarEfectivo(float num) {
    this.saldo = saldo + num;
    incrementarConsignacion();
  }

  public void sacarEfectivo(float num) {
    if(0 <= num && num <= this.saldo) {
      this.saldo = saldo - num;
      incrementarRetiros();
    }
  }

  public void calcularInteresMensual() {
    float interesMensual = (this.tasaAnual / 12) / 100;
    float interesDevengado = this.saldo * interesMensual;
    this.saldo += interesDevengado;
  }

  public void extractoMensual() {
    this.saldo -= this.comisionMensual;
    this.calcularInteresMensual();
  }


  public String imprimir() {
    return "Saldo: $" + String.format("%.2f", saldo) +
            "\nNúmero de consignaciones: " + consignaciones +
            "\nNúmero de retiros: " + retiros +
            "\nTasa anual: " + tasaAnual + "%" +
            "\nComisión mensual: $" + String.format("%.2f", comisionMensual)
    ;
  }
}

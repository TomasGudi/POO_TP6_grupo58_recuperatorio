package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Credito {

	private TarjetaCredito tarjetaCredito;
	private Factura factura;
	private List<Cuota> cuotas = new ArrayList<Cuota>();
	private double creditoRestante;
	
	public Credito() {
	}

	public Credito(TarjetaCredito tarjetaCredito, Factura factura, List<Cuota> cuotas) {
		this.tarjetaCredito = tarjetaCredito;
		this.factura = factura;
	    this.cuotas = new ArrayList<>();
		generarCuotas();
	}

	public Credito(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	
	public double getCreditoRestante() {
		return creditoRestante;
	}

	public void setCreditoRestante(double creditoRestante) {
		this.creditoRestante = creditoRestante;
	}

	public void generarCuotas() {
		double montoCuota = this.factura.calcularTotal() / 30;
		creditoRestante-=montoCuota;
		int nroCuota = 0;
		LocalDate currentDate = LocalDate.now();
		LocalDate auxDate = LocalDate.now();

		for (int i = 0; i < 30; i++) {
			nroCuota++;
			Cuota cuota = new Cuota();
			cuota.setMonto(montoCuota);
			cuota.setNroCuota(nroCuota);
			cuota.setFechaGeneracion(currentDate); 
			auxDate = auxDate.plusMonths(1);
			cuota.setFechaVencimiento(auxDate);
			cuotas.add(cuota);
		}

	}

	public void mostarCredito() {
		System.out.println("Tarjeta De Credito: " + tarjetaCredito.getNumero() + "\n" + factura + "\nCant. Cuotas:\n" + "");
		System.out.println("Numero de factura" + factura.getNroFactura());
		System.out.println("Cuotas");
		for(Cuota cuota: cuotas) {
			System.out.println("\nNumero de cuota: "+cuota.getNroCuota());
			System.out.println("Monto: "+cuota.getMonto());
		}
	}
	
}

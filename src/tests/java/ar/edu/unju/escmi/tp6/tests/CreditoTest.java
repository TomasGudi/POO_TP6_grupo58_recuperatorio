package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.*;

class CreditoTest {

	private Credito credito;
    private Factura factura;
    private TarjetaCredito tarjeta;
    
    @BeforeEach
    void setUp() {
    	Factura factura = new Factura();
    	TarjetaCredito tarjeta = new TarjetaCredito();
    	credito = new Credito();
        credito.setFactura(factura);
        credito.setTarjetaCredito(tarjeta);
    }
    
    
	@Test
	void testMontoCreditoValido() {	
		double montoTotal = credito.getFactura().calcularTotal();
		double montoPermitido = 1500000;
		
		assertTrue(montoTotal <= montoPermitido, "El monto total no debería superar el monto de 1.500.000");
	}
	
	
	@Test
	void testSumaDetallesIgualTotalFactura() {
		double sumaDetalles = factura.getDetalles().stream().mapToDouble(Detalle::getImporte).sum();
		
		double totalFactura = factura.calcularTotal();
		
		assertEquals(sumaDetalles, totalFactura, "La suma de los detalles debe ser igual al total de la factura");
	}
	
	@Test
	void testMontoTotalNoSuperaMontoPermitidoYCreditoDisponible() {
		double montoTotal = credito.getFactura().calcularTotal();
		double montoPermitido = 1500000;
		double limiteTarjeta = tarjeta.getLimiteCompra();
		
		assertTrue(montoTotal <= montoPermitido, "El monto total no debería superar el monto de 1.500.000");
		assertTrue(montoTotal <= limiteTarjeta, "El monto total no debería superar el monto de la tarjeta");
	}
}

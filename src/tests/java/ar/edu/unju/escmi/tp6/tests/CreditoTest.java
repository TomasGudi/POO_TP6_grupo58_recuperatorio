package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.*;

class CreditoTest {

	private Credito credito;
    private Factura factura;
    private TarjetaCredito tarjeta;
    
    @BeforeEach
    void setUp() {
    	Cliente cliente = new Cliente(45111222, "Mario Barca", "Alvear 120", "65454686");
    	Producto producto = new Producto(1111, "Aire Acondicionado Split On/Off 2750W FC Hisense", 220000, "Argentina");
    	List<Detalle> detalles = new ArrayList<Detalle>();
 
    	Factura factura = new Factura(LocalDate.now(), 1234, cliente, detalles );
    	TarjetaCredito tarjeta = new TarjetaCredito(1234, LocalDate.now(), cliente, 123);
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

package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.*;

class CuotaTest {
	
	 	private Credito credito;
		private Factura factura;
		private TarjetaCredito tarjeta;
		
	 	@BeforeEach
	    public void setUp() {
	 		Cliente cliente = new Cliente(45111222, "Mario Barca", "Alvear 120", "65454686");
	        Producto producto = new Producto(1111, "Aire Acondicionado Split On/Off 2750W FC Hisense", 220000, "Argentina");
	        List<Detalle> detalles = new ArrayList<Detalle>();
	        detalles.add(new Detalle(12,10000,producto));

	        this.factura = new Factura(LocalDate.now(), 1234, cliente, detalles);
	        this.tarjeta = new TarjetaCredito(1234, LocalDate.now(), cliente, (long)1200);

	        List<Cuota> cuotas = new ArrayList<Cuota>();
	        cuotas.add(new Cuota(9000, 5678, LocalDate.now(), LocalDate.now().plusMonths(1)));

	        this.credito = new Credito(this.tarjeta, this.factura, cuotas);
	        this.credito.generarCuotas();
	    }

	    @Test
	    public void testListaCuotasNoNull() {
	        List<Cuota> cuotas = credito.getCuotas();
	        assertNotNull(cuotas, "La lista de cuotas no debería ser null");
	    }

	    @Test
	    public void testListaCuotasTiene30Cuotas() {
	        List<Cuota> cuotas = credito.getCuotas();
	        assertEquals(30, cuotas.size(), "La lista de cuotas debería tener 30 cuotas");
	    }

	    @Test
	    public void testCantidadCuotasNoSupereLimite() {
	        List<Cuota> cuotas = credito.getCuotas();
	        assertTrue(cuotas.size() <= 30, "La cantidad de cuotas no debe superar 30");
	    }
}

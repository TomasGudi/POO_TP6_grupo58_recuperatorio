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
		
	 	@BeforeEach
	    public void setUp() {
	 		List<Detalle> detalles = new ArrayList<>();
	        Detalle detalle = new Detalle();
	        detalle.setImporte(250000.00);  
	        detalles.add(detalle);
	        Factura factura = new Factura();
	        factura.setDetalles(detalles);
	        credito = new Credito();
	        credito.setFactura(factura);
	        credito.generarCuotas();
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

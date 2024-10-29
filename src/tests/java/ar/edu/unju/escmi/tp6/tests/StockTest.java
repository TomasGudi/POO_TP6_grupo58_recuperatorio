package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.*;

class StockTest {
    
	private Stock stock;
	
	@BeforeEach
    public void setUp() {
        Producto producto = new Producto(1, "Heladera", 50000, "Argentina");
        stock = new Stock(10, producto);
    }

    @Test
    public void testDecrementarStockProducto() {
        stock.setCantidad(stock.getCantidad() - 3);
        assertEquals(7, stock.getCantidad(), "El stock debe reducirse en la cantidad indicada");
    }

}

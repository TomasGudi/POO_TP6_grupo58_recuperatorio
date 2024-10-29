package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.*;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

			CollectionTarjetaCredito.precargarTarjetas();
			CollectionCliente.precargarClientes();
			CollectionProducto.precargarProductos();
			CollectionStock.precargarStocks();
			
			long dniCliente, codProducto, nroTC;
			int opcion = 0;
			
			do {
				try {
				System.out.println("\n===== Menu Principal =====");
				System.out.println("1- Realizar una venta");
				System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
				System.out.println("3- Mostrar lista de los electrodomésticos");
				System.out.println("4- Consultar stock");
				System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
				System.out.println("6- Salir");

				System.out.println("Ingrese su opcion: ");
				opcion = scanner.nextInt();

				switch (opcion) {
				case 1:
			 		System.out.print("Ingrese el nro de su tarjeta de credito: "); 
			 		nroTC = scanner.nextLong(); 
			 		TarjetaCredito aux = CollectionTarjetaCredito.buscarTarjetaCredito(nroTC);
			 		if (aux != null) {
			 			boolean flag = false;
			 			Random random = new Random();
			 			List<Detalle> detalles = new ArrayList<Detalle>();
			 			List<Cuota> cuotas = new ArrayList<Cuota>();
			 			do {
			 				List<Producto> productos = CollectionProducto.productos;
			 				for(Producto producto : productos) {
			 					producto.mostrarProducto();
			 				}
			 				System.out.println();
			 				System.out.print("Ingrese el codigo del producto que desea comprar: "); 
			 				codProducto = scanner.nextLong(); 
				 			Producto aux2 = CollectionProducto.buscarProducto(codProducto);
				 			if (aux2 != null) {
				 				System.out.println("Cuantos de este producto desea comprar?");
				 				int cantidad = scanner.nextInt(); 
				 				double importe = aux2.getPrecioUnitario(); 
				 				Detalle det = new Detalle(cantidad,importe,aux2);
				 				detalles.add(det);
				 				Stock aux3 = CollectionStock.buscarStock(aux2);
				 				CollectionStock.reducirStock(aux3, cantidad);
				 				
				 				System.out.println("Cuanto desea abonar?");
					 			double monto = scanner.nextDouble();
					 			
					 			int numc = 1000 + random.nextInt(9000);
					 			Cuota cuota = new Cuota(monto, numc, LocalDate.now(), LocalDate.now().plusMonths(1));
					 			cuotas.add(cuota);
				 			} else {
				 				System.out.println("Producto: " + codProducto + " no encontrado.");
				 				scanner.nextLine();
								scanner.nextLine();
				 			}
				 			
				 			System.out.println("Desea continuar comprando?");
				 			System.out.println("Ingrese 0 para terminar su compra");
			 				int fin = scanner.nextInt();
				 			if(fin == 0) {
				 				flag = true;
				 			}
			 			} while (!flag);
			 	        long numt = 10000 + random.nextLong(90000);
			 			Factura compra = new Factura(LocalDate.now(), numt, aux.getCliente(), detalles);
			 			CollectionFactura.agregarFactura(compra);
			 			Credito credito = new Credito(aux,compra,cuotas);
			 			CollectionCredito.agregarCredito(credito);
			 		} else {
						System.out.println("Cliente con la Tarjeta " + nroTC + " no encontrado.");
						scanner.nextLine();
						scanner.nextLine();
					}
					break;
				case 2:
					System.out.print("Ingresa DNI del cliente: ");
					dniCliente = scanner.nextLong();
					Cliente aux1 = CollectionCliente.buscarCliente(dniCliente);
					if (aux1 != null) {
						List<Factura> compras = aux1.consultarCompras();
						if (compras.isEmpty()) {
							System.out.println("El cliente no tiene compras registradas.");
							scanner.nextLine();
							scanner.nextLine();
						} else {
							System.out.println("Compras realizadas por " + aux1.getNombre() + ":");
							for (Factura factura : compras) {
								System.out.println(factura);	
							}
						}
					} else {
						System.out.println("Cliente con DNI " + dniCliente + " no encontrado.");
						scanner.nextLine();
						scanner.nextLine();
					}					
					break;
				case 3:
					System.out.println("\nElectrodomesticos: ");
					List<Producto> productos = CollectionProducto.productos;
					for(Producto producto : productos) {
						if(!producto.getDescripcion().contains("Celular")) { 
							producto.mostrarProducto();
						}
					}
					break;
				case 4:
					for (Stock st : CollectionStock.stocks) {
						System.out.println("Producto: " + st.getProducto().getDescripcion() + " - Stock: " + st.getCantidad());
					}
					break;
				case 5:
					System.out.print("Ingresa DNI del cliente: ");
					dniCliente = scanner.nextLong();
					Cliente aux4 = CollectionCliente.buscarCliente(dniCliente);
					if (aux4 != null) {
						for (Credito cre : CollectionCredito.creditos) {
							if(cre.getTarjetaCredito().getCliente().getDni() == dniCliente) {
								System.out.println(cre);
							}
						}
					} else {
						System.out.println("Cliente con DNI " + dniCliente + " no encontrado.");
						scanner.nextLine();
						scanner.nextLine();
					}
					break;
				case 6:
					 System.out.println("Saliendo del sistema..."); 
					 break; 
				default:
					 System.out.println("Opción no válida, por favor seleccione nuevamente.");
					 break;
				}
			} catch (InputMismatchException e) {
                System.out.println("Error: Se debe ingresar un numero valido.");
                scanner.next();
            }
			
			}while (opcion != 6);
			scanner.close();
		}
}

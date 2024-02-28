//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner

data class Producto(val nombre: String, val precio: Double, var cantidadDisponible: Int)

class CarritoDeCompras {
    private val productosEnCarrito = mutableMapOf<Producto, Int>()

    fun agregarProducto(producto: Producto, cantidad: Int) {
        productosEnCarrito[producto] = productosEnCarrito.getOrDefault(producto, 0) + cantidad
    }

    fun eliminarProducto(producto: Producto) {
        productosEnCarrito.remove(producto)
    }

    fun mostrarCarrito() {
        println("---- Carrito de Compras ----")
        productosEnCarrito.forEach { (producto, cantidad) ->
            println("${producto.nombre} - Cantidad: $cantidad - Precio Unitario: ${producto.precio} - Precio Total: ${producto.precio * cantidad}")
        }
        println("Total: ${calcularTotal()}")
    }

    private fun calcularTotal(): Double {
        var total = 0.0
        productosEnCarrito.forEach { (producto, cantidad) ->
            total += producto.precio * cantidad
        }
        return total
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val carrito = CarritoDeCompras()

    // Crear algunos productos de ejemplo
    val productosDisponibles = listOf(
        Producto("Libro de Programación", 29.99, 10),
        Producto("Mouse inalámbrico", 49.99, 8)
    )

    // Mostrar lista de productos disponibles
    println("---- Productos Disponibles ----")
    productosDisponibles.forEachIndexed { index, producto ->
        println("${index + 1}. ${producto.nombre} - Precio: ${producto.precio} - Cantidad Disponible: ${producto.cantidadDisponible}")
    }

    // Permitir al usuario seleccionar productos
    while (true) {
        println("Ingrese el número del producto que desea agregar al carrito (0 para salir):")
        val opcion = scanner.nextInt()
        if (opcion == 0) break
        if (opcion < 1 || opcion > productosDisponibles.size) {
            println("Opción inválida. Por favor, ingrese un número válido.")
            continue
        }
        val productoSeleccionado = productosDisponibles[opcion - 1]
        println("Ingrese la cantidad:")
        val cantidad = scanner.nextInt()
        if (cantidad > productoSeleccionado.cantidadDisponible) {
            println("No hay suficiente cantidad disponible.")
            continue
        }
        carrito.agregarProducto(productoSeleccionado, cantidad)
        println("Producto agregado al carrito.")
    }

    // Mostrar carrito de compras
    carrito.mostrarCarrito()

    // Confirmar la compra y generar factura
    println("¿Desea confirmar la compra? (Sí/No):")
    val confirmacion = scanner.next()
    if (confirmacion.equals("Sí", ignoreCase = true)) {
        println("---- Factura ----")
        carrito.mostrarCarrito()
        println("Gracias por su compra!")
    } else {
        println("Compra cancelada. Gracias por visitarnos!")
    }
}

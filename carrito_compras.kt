import java.util.Scanner

// Clase para representar un producto
data class Producto(val nombre: String, val precio: Double, var cantidadDisponible: Int)

// Clase para representar el carrito de compras
class CarritoDeCompras {
    private val items = mutableMapOf<Producto, Int>()

    // Método para agregar un producto al carrito
    fun agregarProducto(producto: Producto, cantidad: Int) {
        if (producto in items.keys) {
            items[producto] = items[producto]!! + cantidad
        } else {
            items[producto] = cantidad
        }
    }

    // Método para mostrar el contenido del carrito
    fun mostrarCarrito() {
        if (items.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Carrito de Compras:")
            items.forEach { (producto, cantidad) ->
                println("${producto.nombre} - Cantidad: $cantidad - Precio total: ${producto.precio * cantidad}")
            }
        }
    }

    // Método para calcular el total de la compra
    fun calcularTotal(): Double {
        var total = 0.0
        items.forEach { (producto, cantidad) ->
            total += producto.precio * cantidad
        }
        return total
    }
}

// Función para mostrar la lista de productos y permitir al usuario seleccionar productos
fun seleccionarProductos(productos: List<Producto>): CarritoDeCompras {
    val carrito = CarritoDeCompras()
    val scanner = Scanner(System.`in`)

    println("Lista de Productos:")
    productos.forEachIndexed { index, producto ->
        println("${index + 1}. ${producto.nombre} - Precio: ${producto.precio} - Cantidad disponible: ${producto.cantidadDisponible}")
    }

    while (true) {
        print("Seleccione un producto (0 para terminar): ")
        val opcion = scanner.nextInt()
        if (opcion == 0) {
            break
        } else if (opcion < 1 || opcion > productos.size) {
            println("Opción inválida. Por favor, seleccione un número válido.")
        } else {
            print("Ingrese la cantidad deseada: ")
            val cantidad = scanner.nextInt()
            val productoSeleccionado = productos[opcion - 1]
            if (cantidad > productoSeleccionado.cantidadDisponible) {
                println("No hay suficiente cantidad disponible.")
            } else {
                carrito.agregarProducto(productoSeleccionado, cantidad)
                println("Producto agregado al carrito.")
            }
        }
    }

    return carrito
}

fun main() {
    // Lista de productos de ejemplo
    val productos = listOf(
        Producto("Producto 1", 10.0, 5),
        Producto("Producto 2", 20.0, 10),
        Producto("Producto 3", 15.0, 8)
    )

    // Seleccionar productos
    val carrito = seleccionarProductos(productos)

    // Mostrar carrito y total de la compra
    carrito.mostrarCarrito()
    println("Total de la compra: ${carrito.calcularTotal()}")
}

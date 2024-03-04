import java.util.Scanner
import java.util.InputMismatchException

data class Producto(val nombre: String, val precio: Double, var cantidadDisponible: Int)

class CarritoDeCompras {
    private val productosEnCarrito = mutableMapOf<Int, Producto>()
    private val cantidades = mutableMapOf<Producto, Int>()

    fun agregarProducto(numeroProducto: Int, producto: Producto, cantidad: Int) {
        productosEnCarrito[numeroProducto] = producto
        cantidades[producto] = cantidad
    }

    fun eliminarProducto(numeroProducto: Int) {
        val productoAEliminar = productosEnCarrito.remove(numeroProducto)
        if (productoAEliminar != null) {
            cantidades.remove(productoAEliminar)
            println("${productoAEliminar.nombre} ha sido eliminado del carrito.")
        } else {
            println("El número de producto \"$numeroProducto\" no se encontró en el carrito.")
        }
    }

    fun mostrarCarrito(): String {
        val carritoString = StringBuilder()
        carritoString.append("---- Carrito de Compras ----\n")
        if (productosEnCarrito.isNotEmpty()) {
            productosEnCarrito.forEach { (numeroProducto, producto) ->
                val cantidad = cantidades[producto] ?: 0
                carritoString.append("$numeroProducto. ${producto.nombre} - Cantidad: $cantidad - Precio Unitario: ${producto.precio} - Precio Total: ${producto.precio * cantidad}\n")
            }
            carritoString.append("Total: ${calcularTotal()}")
        } else {
            carritoString.append("El carrito está vacío.")
        }
        return carritoString.toString()
    }

    private fun calcularTotal(): Double {
        var total = 0.0
        productosEnCarrito.forEach { (_, producto) ->
            val cantidad = cantidades[producto] ?: 0
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

    var salir = "si"
    // Permitir al usuario seguir comprando después de ver la factura.
    while (salir != "no") {

        // Mostrar lista de productos disponibles
        println("---- Productos Disponibles ----")
        productosDisponibles.forEachIndexed { index, producto ->
            println("${index + 1}. ${producto.nombre} - Precio: ${producto.precio} - Cantidad Disponible: ${producto.cantidadDisponible}")
        }

        // Permitir al usuario seleccionar productos
        while (true) {
            println("Ingrese el número del producto que desea agregar al carrito (0 para salir):")
            try {
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
                carrito.agregarProducto(opcion, productoSeleccionado, cantidad)
                println("Producto agregado al carrito.")
            } catch (e: InputMismatchException) {
                println("Opción inválida. Por favor, ingrese un número válido.")
                scanner.nextLine() 
            }
        }

        // Mostrar carrito de compras
        val carritoMostrado = carrito.mostrarCarrito()
        println(carritoMostrado)

        // Eliminar producto del carrito
        var eliminarProducto: String
        while(true) {
            println("¿Desea eliminar algún producto del carrito? (Si/No) (0 para salir):")
            eliminarProducto = scanner.next().lowercase()
            if (eliminarProducto == "0") break

            if (eliminarProducto == "si") {
                println("Ingrese el número del producto que desea eliminar:")
                try {
                    val numeroProductoEliminar = scanner.nextInt()
                    carrito.eliminarProducto(numeroProductoEliminar)
                } catch (e: InputMismatchException) {
                    println("Opción inválida. Por favor, ingrese un número válido.")
                    scanner.nextLine() 
                }            
                continue
            } else if (eliminarProducto == "no") {
                break
            } else {
                println("Opción inválida. Por favor, ingrese \"Si\" o \"No\".")
            }
        }


        // Confirmar la compra y generar factura si hay productos en el carrito
        if (carrito.mostrarCarrito() != "El carrito está vacío.") {
            println("¿Desea confirmar la compra? (Sí/No):")
            val confirmacion = scanner.next().lowercase()
            if (confirmacion == "si" || eliminarProducto == "sí") {
                println("---- Factura ----")
                println(carrito.mostrarCarrito())
                println("Gracias por su compra!")
                println("¿Desea seguir comprando? (Sí/No):")
                salir = scanner.next().lowercase()
                if (salir == "no") break
                if (salir != "si") {
                    println("Opción inválida. Por favor, ingrese \"Sí\" o \"No\".")
                }
            } else {
                println("Compra cancelada. Gracias por visitarnos!")
                break
            }
        } else {
            println("Gracias por visitarnos!")
            break
        }
    }
}
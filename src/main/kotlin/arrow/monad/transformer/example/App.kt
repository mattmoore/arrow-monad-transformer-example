package arrow.monad.transformer.example

import arrow.core.Either
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.mtl.EitherT

fun getAddressFromOrder(orderId: Int): IO<Address> = IO.fx {
    val order = !getOrder(orderId)
    val customer = !getCustomer(order.customerId)
    val address = !getAddress(customer.addressId)
    address
}

suspend fun main(args: Array<String>) {
    getAddressFromOrder(1).attempt().map {
        when (it) {
            is Either.Left  -> println(it.a.message)
            is Either.Right -> println(it.b)
        }
    }.suspended()
}

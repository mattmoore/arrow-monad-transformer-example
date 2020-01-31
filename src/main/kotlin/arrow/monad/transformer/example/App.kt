package arrow.monad.transformer.example

import arrow.core.*
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.monad.monad
import arrow.mtl.EitherT
import arrow.fx.fix
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.fix

fun getAddressFromOrder(orderId: Int): EitherT<ForIO, Exception, Address> =
        EitherT.monad<ForIO, Exception>(IO.monad()).fx.monad {
            val order = !getOrder(orderId)
            val customer = !getCustomer(order.customerId)
            val address = !getAddress(customer.addressId)
            address
        }.fix()

suspend fun main(args: Array<String>) {
    // With an invalid order number, we get the Left type containing the Exception and print the Exception message.
    getAddressFromOrder(-1).value().fix().map {
        when (it) {
            is Either.Left -> println(it.a.message)
            is Either.Right -> println(it.b)
        }
    }.suspended()

    // With an valid order number, we get the Right type containing the address that was found.
    getAddressFromOrder(1).value().fix().map {
        when (it) {
            is Either.Left -> println(it.a.message)
            is Either.Right -> println(it.b)
        }
    }.suspended()
}

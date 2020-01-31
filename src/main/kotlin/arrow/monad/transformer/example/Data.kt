package arrow.monad.transformer.example

import arrow.core.Left
import arrow.core.Right
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.handleError
import arrow.mtl.EitherT

val addresses = listOf(
        Address(1, "123 Anywhere Street", "Chicago", "IL")
)

val customers = listOf(
        Customer(1, "Matt", "Moore", 1)
)

val orders = listOf(
        Order(1, 1)
)

fun getOrder(id: Int): EitherT<ForIO, Exception, Order> = EitherT(
        IO {
            Right(orders.find { it.id == id }!!)
        }.handleError { Left(Exception("Could not find order with ID $id.")) }
)

fun getCustomer(id: Int): EitherT<ForIO, Exception, Customer> = EitherT(
        IO {
            Right(customers.find { it.id == id }!!)
        }.handleError { Left(Exception("Could not find customer with ID $id.")) }
)

fun getAddress(id: Int): EitherT<ForIO, Exception, Address> = EitherT(
        IO {
            Right(addresses.find { it.id == id }!!)
        }.handleError { Left(Exception("Could not find address with ID $id.")) }
)

package routes
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import models.Order
import models.orderStorage

// Post用routing
fun Route.orderRoute(){
    post("/order") {
        val order = call.receive<Order>()
        orderStorage.add(order)
        call.respondText("Order stored correctly", status = HttpStatusCode.Created)
    }
}

// getされた時に全てのorderStorageを返す。
fun Route.listOrderRoute(){
    get("/order") {
        if(orderStorage.isNotEmpty()){
            call.respond(orderStorage)
        }
    }
}

// idが一致したものがあれば返す
fun Route.getOrderRoute(){
    get("/order/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText("Bad request", status = HttpStatusCode.BadRequest)
        val order = orderStorage.find{ it.number == id } ?: return@get call.respondText("Not Found", status = HttpStatusCode.NotFound)
        call.respond(order)
    }
}

// idが一致したもののトータルの金額
fun Route.totalizeOrderRoute(){
    get("/order/{id}/total"){
        val id = call.parameters["id"] ?: return@get call.respondText("Bad request", status = HttpStatusCode.BadRequest)
        val order = orderStorage.find{ it.number == id } ?: return@get call.respondText("Not Found", status = HttpStatusCode.NotFound)
        val total = order.contents.map { it.price * it.amount }.sum()
        call.respond(total)
    }
}


fun Application.registerOrderRoute(){
    routing {
        orderRoute()
        listOrderRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
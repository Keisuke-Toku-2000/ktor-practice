package com.jetbrains.handson.httpapi

import io.ktor.application.Application
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.serialization.*
import routes.registerCustomerRoute
import routes.registerOrderRouting


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation){
        json()
    }
    registerCustomerRoute()
    registerOrderRouting()
}

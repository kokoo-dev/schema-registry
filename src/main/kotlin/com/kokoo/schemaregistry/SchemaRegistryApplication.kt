package com.kokoo.schemaregistry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchemaRegistryApplication

fun main(args: Array<String>) {
	runApplication<SchemaRegistryApplication>(*args)
}

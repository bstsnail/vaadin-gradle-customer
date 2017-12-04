package com.bstsnail.vaadin

import java.time.LocalDate

/**
 * rdeng
 * 03/10/2017
 */
data class Customer(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val birthDate: LocalDate,
        val status: CustomerStatus,
        val email: String
)
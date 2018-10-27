package com.trackfinances.backend.trackfinancesbackend.model

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Expenses(
        @Id @GeneratedValue val id: Long? = null,
        val userId: Long,
        val cost: BigDecimal,
        val title: String,
        val description: String
)
package com.trackfinances.backend.trackfinancesbackend.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "expenses")
data class Expenses(
        @Id @GeneratedValue( strategy = GenerationType.AUTO ) var id: Long? = null,
        var price: BigDecimal? = null,
        var title: String? = null,
        var description: String? = null,
		@ManyToOne
		@JoinColumn(name = "user_id")
		var users: Users? = null
)
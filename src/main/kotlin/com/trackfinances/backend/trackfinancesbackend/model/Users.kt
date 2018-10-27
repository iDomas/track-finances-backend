package com.trackfinances.backend.trackfinancesbackend.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Users (
        @Id @GeneratedValue val id: Long? = null,
        val username: String,
        var password: String
)
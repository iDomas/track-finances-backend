package com.trackfinances.backend.trackfinancesbackend.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class Users (
        @Id @GeneratedValue( strategy = GenerationType.AUTO ) var id: Long,
        @Column(unique = true, nullable = false) var username: String,
        @Column(nullable = false) var password: String
)
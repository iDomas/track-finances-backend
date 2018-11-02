package com.trackfinances.backend.trackfinancesbackend.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class Users (
        @Id @GeneratedValue( strategy = GenerationType.AUTO ) var id: Long,
        @Column(unique = true) var username: String,
        var password: String
)
//{
//    @OneToMany(mappedBy = "users")
//    val expenses: MutableSet<Expenses> = HashSet()
//}
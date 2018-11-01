package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, Long> {
}
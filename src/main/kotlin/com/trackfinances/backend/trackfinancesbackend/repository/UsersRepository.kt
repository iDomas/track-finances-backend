package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Users
import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<Users, Long> {
}
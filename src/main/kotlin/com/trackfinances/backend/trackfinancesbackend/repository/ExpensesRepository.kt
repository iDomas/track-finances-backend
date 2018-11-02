package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import org.springframework.data.jpa.repository.JpaRepository

interface ExpensesRepository : JpaRepository<Expenses, Long> {
    fun getAllByUserId(userId: Long): List<Expenses>
}
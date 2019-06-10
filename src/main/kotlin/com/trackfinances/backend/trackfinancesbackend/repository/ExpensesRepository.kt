package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface ExpensesRepository : JpaRepository<Expenses, Long> {
	fun findAllExpensesByUsers(users: Users): List<Expenses>
}
package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import org.springframework.data.repository.CrudRepository

interface ExpensesRepository : CrudRepository<Expenses, Long> {
}
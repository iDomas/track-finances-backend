package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.repository.ExpensesRepository
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.web.bind.annotation.*

@RestController
class ExpensesController(private val expensesRepository: ExpensesRepository, private val usersRepository: UsersRepository) {

    @GetMapping("/expenses")
    @ResponseBody
    fun allExpenses(): List<Expenses> {
        return expensesRepository.findAll().iterator().asSequence().toList();
    }

    @PostMapping("/expenses")
    @ResponseBody
    fun insertExpense(@RequestBody expenses: Expenses): Expenses {
        println(expenses)
        expensesRepository.save(expenses);
        return expenses;
    }

}
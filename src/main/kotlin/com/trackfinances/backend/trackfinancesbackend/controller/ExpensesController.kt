package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.repository.ExpensesRepository
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import javassist.NotFoundException
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpensesController(private val expensesRepository: ExpensesRepository, private val usersRepository: UsersRepository) {


    @GetMapping(value = ["","/"])
    @ResponseBody
    fun allExpenses(): List<Expenses> {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        val userId: Long = usersRepository.findByUsername(user.toString()).id
        return expensesRepository.getAllByUserId(userId);
    }

    @PostMapping(value = ["","/"])
    @ResponseBody
    fun insertExpense(@RequestBody expenses: Expenses): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        val userId: Long = usersRepository.findByUsername(user.toString()).id
        expenses.userId = userId
        expensesRepository.save(expenses);
        return expenses;
    }

    @GetMapping(value = ["/{expenseId}", "/{expenseId}/"])
    @ResponseBody
    fun getExpenseById(@PathVariable("expenseId") expenseId: Long): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        val userId: Long = usersRepository.findByUsername(user.toString()).id

        val expense: Expenses = expensesRepository.findById(expenseId).get()

        if (expense.userId != userId) {
            throw NotFoundException("expense.userId != userId")
        }

        return expense
    }

    @PutMapping(value = ["/{expenseId}", "/{expenseId}/"])
    @ResponseBody
    fun updateExpense(@RequestBody expenses: Expenses,
                      @PathVariable("expenseId") expenseId: Long): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        val userId: Long = usersRepository.findByUsername(user.toString()).id

        val expense: Expenses = expensesRepository.findById(expenseId).get()

        expense.price = expenses.price
        expense.title = expenses.title
        expense.description = expenses.description

        if (expense.userId != userId) {
            throw NotFoundException("expense.userId != userId")
        }

        return expensesRepository.save(expense)
    }

}
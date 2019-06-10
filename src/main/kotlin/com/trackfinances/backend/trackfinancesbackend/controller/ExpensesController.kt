package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.ExpensesRepository
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import javassist.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpensesController(private val expensesRepository: ExpensesRepository, private val usersRepository: UsersRepository) {

    @GetMapping(value = ["","/"])
    @ResponseBody
    fun allExpensesByUser(): List<Expenses> {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        val currentUser: Users = usersRepository.findByUsername(user.toString())

        val expenses = expensesRepository.findAllExpensesByUsers(currentUser)

        if (expenses.isEmpty())
            return emptyList()

        return expenses
    }

    @PostMapping(value = ["","/"])
    @ResponseBody
    fun insertExpense(@RequestBody expenses: Expenses): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
        expenses.users = usersRepository.findByUsername(user.toString())

        expensesRepository.save(expenses)

        return expenses
    }

    @GetMapping(value = ["/{expenseId}", "/{expenseId}/"])
    @ResponseBody
    fun getExpenseById(@PathVariable("expenseId") expenseId: Long): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
		val currentUser: Users = usersRepository.findByUsername(user.toString())

        val expense: Expenses = expensesRepository.findById(expenseId).get()
		val expensesUserId: Long? = expense.users?.id

		if (currentUser.id != expensesUserId)
			throw Exception("NO SUCH USER AND EXPENSE WITH USER_ID: $currentUser.id AND EXPENSE_ID: $expensesUserId")

        return expense
    }

    @PutMapping(value = ["/{expenseId}", "/{expenseId}/"])
    @ResponseBody
    fun updateExpense(@RequestBody expenses: Expenses,
                      @PathVariable("expenseId") expenseId: Long): Expenses {
        val user: Any = SecurityContextHolder.getContext().authentication.principal
		val currentUser: Users = usersRepository.findByUsername(user.toString())

        val expense: Expenses = expensesRepository.findById(expenseId).get()
		val expensesUserId: Long? = expense.users?.id

		if (currentUser.id != expensesUserId)
			throw Exception("NO SUCH USER AND EXPENSE WITH USER_ID: $currentUser.id AND EXPENSE_ID: $expensesUserId")

        expense.price = expenses.price
        expense.title = expenses.title
        expense.description = expenses.description

        return expensesRepository.save(expense)
    }

    @DeleteMapping(value = ["/{expenseId}", "/{expenseId}/"])
    @ResponseBody
    fun deleteExpense(@PathVariable("expenseId") expenseId: Long): String {
        expensesRepository.deleteById(expenseId)
        return "{\"status\":\"OK\"}"
    }

}
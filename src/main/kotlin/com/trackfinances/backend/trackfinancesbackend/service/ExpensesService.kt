package com.trackfinances.backend.trackfinancesbackend.service

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.ExpensesRepository
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ExpensesService(
		private val expensesRepository: ExpensesRepository,
		private val usersRepository: UsersRepository
) {

	fun allExpensesByUser(): List<Expenses> {
		val user: Any = SecurityContextHolder.getContext().authentication.principal
		val currentUser: Users = usersRepository.findByUsername(user.toString())

		val expenses = expensesRepository.findAllExpensesByUsers(currentUser)

		if (expenses.isEmpty())
			return emptyList()

		return expenses
	}

	fun insertExpense(expenses: Expenses): Expenses {
		val user: Any = SecurityContextHolder.getContext().authentication.principal
		expenses.users = usersRepository.findByUsername(user.toString())

		expensesRepository.save(expenses)

		return expenses
	}

	fun getExpenseById(expenseId: Long): Expenses {
		val user: Any = SecurityContextHolder.getContext().authentication.principal
		val currentUser: Users = usersRepository.findByUsername(user.toString())

		val expense: Expenses = expensesRepository.findById(expenseId).get()
		val expensesUserId: Long? = expense.users?.id

		if (currentUser.id != expensesUserId)
			throw Exception("NO SUCH USER AND EXPENSE WITH USER_ID: ${currentUser.id} AND EXPENSE_ID: $expensesUserId")

		return expense
	}

	fun updateExpense(expenses: Expenses, expenseId: Long): Expenses {
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

	fun deleteExpense(expenseId: Long): String {
		expensesRepository.deleteById(expenseId)
		return "{\"status\":\"OK\"}"
	}
}

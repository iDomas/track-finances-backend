package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.model.Users
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
class ExpensesRepositoryTest {

	@MockBean
	private lateinit var expensesRepository: ExpensesRepository

	@Test
	fun findAllExpensesByUsers_shouldReturnListOfExpenses() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)
		val expenseOne = Expenses(
				1,
				BigDecimal(0.99),
				"test title",
				"test description",
				user
		)
		val expenseTwo = Expenses(
				2,
				BigDecimal(0.99),
				"test title",
				"test description",
				user
		)

		// when
		val expenses: ArrayList<Expenses> = ArrayList()
		expenses.add(expenseOne)
		expenses.add(expenseTwo)

		Mockito.`when`(expensesRepository.findAllExpensesByUsers(user)).thenReturn(expenses)

		// then
		val actualExpenses = expensesRepository.findAllExpensesByUsers(user)

		assertThat(actualExpenses).isNotEmpty
		assertThat(actualExpenses.get(0)).isEqualTo(expenseOne)
		assertThat(actualExpenses.get(1)).isEqualTo(expenseTwo)
		verify(expensesRepository, times(1)).findAllExpensesByUsers(user)
	}

}

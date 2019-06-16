package com.trackfinances.backend.trackfinancesbackend.service

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.ExpensesRepository
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.util.*

@RunWith(SpringRunner::class)
class ExpensesServiceTest {

	@MockBean
	private lateinit var usersRepository: UsersRepository
	@MockBean
	private lateinit var expensesRepository: ExpensesRepository

	private lateinit var expensesService: ExpensesService

	@Before
	fun init() {
		expensesService = ExpensesService(expensesRepository, usersRepository)
	}

	@Test
	fun allExpensesByUser_ShouldReturn1ExpenseByUser() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)
		val expense = Expenses(
				1,
				BigDecimal(10.99),
				"Java tutorial",
				"Cheap java tutorial",
				user
		)

		val username = "test"
		Mockito.`when`(usersRepository.findByUsername(username)).thenReturn(user)

		val givenExpenses = Arrays.asList(expense)
		Mockito.`when`(expensesRepository.findAllExpensesByUsers(
				usersRepository.findByUsername(username))).thenReturn(givenExpenses)

		// when
		val actualExpenses: List<Expenses> = expensesRepository.findAllExpensesByUsers(user)

		// then
		assertThat(actualExpenses).isNotEmpty
		assertThat(actualExpenses).contains(expense)
		verify(expensesRepository, times(1)).findAllExpensesByUsers(user)
	}

	@Test
	fun getAllExpensesByUser_2UsersGetIndividualExpenses() {
		// given
		val user1 = Users(
				1,
				"test",
				"test"
		)
		val user2 = Users(
				2,
				"test2",
				"test2"
		)
		val user1Expense1 = Expenses(
				1,
				BigDecimal(10.99),
				"Java tutorial nr1",
				"Java tutorial number 1",
				user1
		)
		val user1Expense2 = Expenses(
				2,
				BigDecimal(9.99),
				"Kotlin tutorial nr1",
				"Kotlin tutorial number 1",
				user1
		)
		val user2Expense1 = Expenses(
				3,
				BigDecimal(9.99),
				"Java tutorial",
				"Some description",
				user2
		)
		val user2Expense2 = Expenses(
				4,
				BigDecimal(12.99),
				"Kotlin tutorial",
				"Some description about Kotlin",
				user2
		)

		Mockito.`when`(usersRepository.findByUsername(user1.username)).thenReturn(user1)
		Mockito.`when`(usersRepository.findByUsername(user2.username)).thenReturn(user2)

		val givenUser1Expenses: List<Expenses> = Arrays.asList(user1Expense1, user1Expense2)
		val givenUser2Expenses: List<Expenses> = Arrays.asList(user2Expense1, user2Expense2)
		Mockito.`when`(expensesRepository.findAllExpensesByUsers(user1)).thenReturn(givenUser1Expenses)
		Mockito.`when`(expensesRepository.findAllExpensesByUsers(user2)).thenReturn(givenUser2Expenses)

		// when
		val actualUser1Expenses: List<Expenses> = expensesRepository.findAllExpensesByUsers(user1)
		val actualUser2Expenses: List<Expenses> = expensesRepository.findAllExpensesByUsers(user2)

		// then
		assertThat(actualUser1Expenses).isNotEmpty
		assertThat(actualUser1Expenses).contains(user1Expense1, user1Expense2)
		verify(expensesRepository, times(1)).findAllExpensesByUsers(user1)

		assertThat(actualUser2Expenses).isNotEmpty
		assertThat(actualUser2Expenses).contains(user2Expense1, user2Expense2)
		verify(expensesRepository, times(1)).findAllExpensesByUsers(user2)
	}

	@Test
	@WithMockUser(username = "test", password = "test")
	fun insertExpense_ShouldInsert1Expense() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)
		val expense = Expenses(
				1,
				BigDecimal(10.99),
				"Java tut",
				"Some description",
				user
		)

		Mockito.`when`(usersRepository.findByUsername(user.username)).thenReturn(user)
		Mockito.`when`(expensesRepository.save(expense)).thenReturn(expense)

		// when
		val insertedExpense = expensesService.insertExpense(expense)

		// then
		assertThat(insertedExpense).isNotNull
		assertThat(insertedExpense).isEqualTo(expense)
		verify(expensesRepository, times(1)).save(expense)
	}
}
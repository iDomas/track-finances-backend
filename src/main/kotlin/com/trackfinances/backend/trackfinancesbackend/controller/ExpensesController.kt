package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Expenses
import com.trackfinances.backend.trackfinancesbackend.service.ExpensesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpensesController(@Autowired private val expensesService: ExpensesService) {

	@GetMapping(value = ["", "/"])
	@ResponseBody
	fun allExpensesByUser(): List<Expenses> {
		return expensesService.allExpensesByUser()
	}

	@PostMapping(value = ["", "/"])
	@ResponseBody
	fun insertExpense(@RequestBody expenses: Expenses): Expenses {
		return expensesService.insertExpense(expenses)
	}

	@GetMapping(value = ["/{expenseId}", "/{expenseId}/"])
	@ResponseBody
	fun getExpenseById(@PathVariable("expenseId") expenseId: Long): Expenses {
		return expensesService.getExpenseById(expenseId)
	}

	@PutMapping(value = ["/{expenseId}", "/{expenseId}/"])
	@ResponseBody
	fun updateExpense(@RequestBody expenses: Expenses,
					  @PathVariable("expenseId") expenseId: Long): Expenses {
		return expensesService.updateExpense(expenses, expenseId)
	}

	@DeleteMapping(value = ["/{expenseId}", "/{expenseId}/"])
	@ResponseBody
	fun deleteExpense(@PathVariable("expenseId") expenseId: Long): String {
		return expensesService.deleteExpense(expenseId)
	}

}
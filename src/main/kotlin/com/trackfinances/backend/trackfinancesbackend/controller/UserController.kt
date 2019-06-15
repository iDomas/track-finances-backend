package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController()
@RequestMapping("/users")
class UserController(
		@Autowired private val usersService: UsersService
) {

	@GetMapping(value = ["", "/"])
	@ResponseBody
	fun getAllUsers(): List<Users> {
		return usersService.getAllUsers()
	}

	@PostMapping(value = ["", "/"])
	@ResponseBody
	fun insertUser(@RequestBody users: Users): Users {
		return usersService.insertUser(users)
	}

	@GetMapping(value = ["/current", "current"])
	@ResponseBody
	fun getCurrentUser(principal: Principal): Users {
		return usersService.getUserByUsername(principal.name)
	}

	@Deprecated("This endpoint is useless or should be refactored to be used from userService")
	@PostMapping(value = ["/search-by-username", "search-by-username"])
	@ResponseBody
	fun searchByUsername(@RequestBody body: String): String {
		var returnMessage = "{ \"isUsernameTaken\": false }"

		var user: Users? = null
		try {
			user = usersService.getUserByUsername(body)
		} catch (e: EmptyResultDataAccessException) {
			println("Err $e")
		}


		if (user != null) {
			returnMessage = "{ \"isUsernameTaken\": true }"
		}

		return returnMessage
	}

}
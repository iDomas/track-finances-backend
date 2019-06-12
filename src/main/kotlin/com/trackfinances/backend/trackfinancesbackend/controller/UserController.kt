package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController()
@RequestMapping("/users")
class UserController(private val usersRepository: UsersRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

	@GetMapping(value = ["", "/"])
	@ResponseBody
	fun getAllUsers(): List<Users> {
		return usersRepository.findAll().iterator().asSequence().toList()
	}

	@PostMapping(value = ["", "/"])
	@ResponseBody
	fun insertUser(@RequestBody users: Users): Users {
		users.password = bCryptPasswordEncoder.encode(users.password)
		usersRepository.save(users)
		return users
	}

	@GetMapping(value = ["/current", "current"])
	@ResponseBody
	fun getCurrentUser(principal: Principal): Users {
		return usersRepository.findByUsername(principal.name)
	}

	@PostMapping(value = ["/search-by-username", "search-by-username"])
	@ResponseBody
	fun searchByUsername(@RequestBody body: String): String {
		var returnMessage = "{ \"isUsernameTaken\": false }"

		var user: Users? = null
		try {
			user = usersRepository.findByUsername(body)
		} catch (e: EmptyResultDataAccessException) {
			println("Err $e")
		}


		if (user != null) {
			returnMessage = "{ \"isUsernameTaken\": true }"
		}

		return returnMessage
	}

}
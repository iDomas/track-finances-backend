package com.trackfinances.backend.trackfinancesbackend.controller

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/users")
class UserController(private val usersRepository: UsersRepository) {

    @GetMapping(value = ["", "/"])
    @ResponseBody
    fun getAllUsers(): List<Users> {
        return usersRepository.findAll().iterator().asSequence().toList();
    }

    @PostMapping(value = ["", "/"])
    @ResponseBody
    fun insertUser(@RequestBody users: Users): Users {
        usersRepository.save(users);
        return users;
    }

}
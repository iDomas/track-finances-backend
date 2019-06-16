package com.trackfinances.backend.trackfinancesbackend.service

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
class UserServiceTest {

	@MockBean private lateinit var usersRepository: UsersRepository
	@MockBean private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
	private lateinit var usersService: UsersService

	@Before
	fun init() {
		 usersService = UsersService(usersRepository, bCryptPasswordEncoder)
	}

	@Test
	fun getAllUsers_ShouldReturn2Users() {
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

		val users: List<Users> = Arrays.asList(user1, user2)
		Mockito.`when`(usersRepository.findAll()).thenReturn(users)

		// when
		val actual: List<Users> = usersService.getAllUsers()

		// then
		assertThat(actual).isNotEmpty
		assertThat(actual).contains(user1)
		assertThat(actual).contains(user2)
		verify(usersRepository, times(1)).findAll()
	}

	@Test
	fun insertUser_ShouldInsert1User() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)
		Mockito.`when`(usersRepository.save(user)).thenReturn(user)
		val encodedPasswordReturn = "test"
		Mockito.`when`(bCryptPasswordEncoder.encode(user.password)).thenReturn(encodedPasswordReturn)

		// when
		val actualUser = usersService.insertUser(user)

		// then
		verify(usersRepository, times(1)).save(user)
		assertThat(user).isEqualTo(actualUser)
	}

	@Test
	fun getUserByUsername_ShouldGet1UserByUsername() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)
		val usernameToFind = "test"
		Mockito.`when`(usersRepository.findByUsername(usernameToFind)).thenReturn(user)

		// when
		val actualUser = usersService.getUserByUsername(usernameToFind);

		// then
		verify(usersRepository, times(1)).findByUsername(usernameToFind)
		assertThat(user).isEqualTo(actualUser)
	}
}
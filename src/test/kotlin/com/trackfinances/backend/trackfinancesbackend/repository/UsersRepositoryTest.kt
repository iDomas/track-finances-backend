package com.trackfinances.backend.trackfinancesbackend.repository

import com.trackfinances.backend.trackfinancesbackend.model.Users
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class UsersRepositoryTest {

	@MockBean
	private lateinit var usersRepository: UsersRepository

	@Test
	fun findByUsername_ShouldFindUsernameByGivenUserObject() {
		// given
		val user = Users(
				1,
				"test",
				"test"
		)

		Mockito.`when`(usersRepository.findByUsername(user.toString())).thenReturn(user)

		// when
		val givenUser = usersRepository.findByUsername(user.toString())

		// then
		assertThat(givenUser).isNotNull
		assertThat(givenUser.id).isEqualTo(1L)
		assertThat(givenUser.username).isEqualTo("test")
		assertThat(givenUser.password).isEqualTo("test")
	}
}

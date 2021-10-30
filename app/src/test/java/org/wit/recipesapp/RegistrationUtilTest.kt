package org.wit.recipesapp




import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.wit.recipesapp.activities.RegistrationUtil

class RegistrationUtilTest
{
    @Test
    fun `empty user returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
           username = "James",
            password = "123",
            confirmedPassword = "123"

        )

        assertThat(result).isFalse()
    }
    @Test
    fun `valid username and password are entered`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "James Geraghty",
            password = "123",
            confirmedPassword = "123"

        )

        assertThat(result).isTrue()
    }

    @Test
    fun `the password is empty`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "James Geraghty",
            password = "",
            confirmedPassword = "123"

        )

        assertThat(result).isFalse()
    }

    @Test
    fun `the password was repeated incorrectly`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "James Geraghty",
            password = "123",
            confirmedPassword = "1234"

        )

        assertThat(result).isFalse()
    }

    @Test
    fun `the password contains less than 2 digit`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "James Geraghty",
            password = "1",
            confirmedPassword = "1"

        )

        assertThat(result).isFalse()
    }

}
package org.wit.recipesapp.activities

object RegistrationUtil {


    private val existingusers  = listOf("James Geraghty", "james")

    fun validateRegistrationInput(

        username:String,
        password:String,
        confirmedPassword: String

    ):Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false
        }
        if (username in existingusers) {
            return false
        }
        if (password != confirmedPassword) {
            return false
        }

        if (password.count { it.isDigit() } < 2){
            return false
    }
    return true
    }
}
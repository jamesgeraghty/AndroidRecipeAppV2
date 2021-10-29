package org.wit.recipesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

import org.wit.recipesapp.R

import org.wit.recipesapp.databinding.ActivityLoginBinding
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.UserModel
import timber.log.Timber


class LoginActivity : AppCompatActivity() {
    lateinit var app: MainApp

    private lateinit var binding: ActivityLoginBinding

    private lateinit var recipeIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var signUpIntentLauncher: ActivityResultLauncher<Intent>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp


        //create a temporary user
       if (app.users.findAll().firstOrNull { it.email == "james@geraghty.ie" } == null)
            app.users.create(UserModel("james@geraghty.ie", "James", "Geraghty", "123"))

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.title = title
        setSupportActionBar(binding.signIn)

        binding.btnLogin.setOnClickListener() {
            var email = binding.email.text.toString()
            Timber.i("Email: $email")

            var user: UserModel? = app.users.findAll().firstOrNull { it.email == email }
            if (user != null) {
                if (binding.password.text.toString() == user.password) {
                    val launcherIntent = Intent(this, RecipeListActivity::class.java)
                    launcherIntent.putExtra("user", user)
                    recipeIntentLauncher.launch(launcherIntent)
                } else {
                    Snackbar.make(it, R.string.invalid_password, Snackbar.LENGTH_LONG)
                        .show()
                }
            } else {
                Snackbar.make(it, R.string.invalid_username, Snackbar.LENGTH_LONG)
                    .show()
            }

        }
        registerRecipeCallback()
        registerSignUpCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_login, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_signUp -> {
                val launcherIntent = Intent(this, RegisterActivity::class.java)
                signUpIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRecipeCallback() {
        recipeIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }

    private fun registerSignUpCallback() {
        signUpIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }


}
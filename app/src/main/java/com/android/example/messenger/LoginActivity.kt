package com.android.example.messenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.example.messenger.data.local.AppPreferences
import com.android.example.messenger.ui.login.LoginPresenter
import com.android.example.messenger.ui.login.LoginPresenterImpl
import com.android.example.messenger.ui.login.LoginView
import com.android.example.messenger.ui.main.MainActivity
import com.android.example.messenger.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: LoginPresenter // LoginPresenter property declaration
    private lateinit var preferences: AppPreferences // AppPreferences property declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenterImpl(this)
        preferences = AppPreferences.create(this)

        if (preferences.accessToken != null) {
            navigateToHome()
            finish()
        }
        supportActionBar?.hide()
        bindViews()
    }

    override fun bindViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_sign_up)
        progressBar = findViewById(R.id.progress_bar)
        btnLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun setUsernameError() {
        etUsername.error = "Username field cannot be empty"
    }

    override fun setPasswordError() {
        etPassword.error = "Password field cannot be empty"
    }

    override fun showAuthError() {
        Toast.makeText(this, "Invalid username and password combination.",
            Toast.LENGTH_LONG).show()
    }

    override fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigateToHome() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_login) {
            presenter.executeLogin(etUsername.text.toString(), etPassword.text.toString())
        } else if (view.id == R.id.btn_sign_up) {
            navigateToSignUp()
        }
    }

    override fun getContext(): Context {
        return this
    }
}
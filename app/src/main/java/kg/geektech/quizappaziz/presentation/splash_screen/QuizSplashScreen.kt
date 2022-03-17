package kg.geektech.quizappaziz.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kg.geektech.quizappaziz.presentation.activity.MainActivity

@SuppressLint("CustomSplashScreen")
class QuizSplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
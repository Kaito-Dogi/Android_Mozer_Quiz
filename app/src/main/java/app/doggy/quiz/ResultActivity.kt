package app.doggy.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //クイズ数を受け取る。
        val quizCount = intent.getIntExtra("quizCount", 0)

        //クイズ数を表示。
        questionCountText.text = "${quizCount}問中…"

        //正解数を受け取る。
        val correctCount = intent.getIntExtra("correctCount", 0)

        //正解数を受け取る。
        correctCountText.text = "${correctCount}問正解"

        //クリックリスナを設定。
        retryButton.setOnClickListener {

            //Intentをインスタンス化。
            val quizIntent = Intent(applicationContext, QuizActivity::class.java)

            //画面遷移。
            startActivity(quizIntent)

        }

    }
}
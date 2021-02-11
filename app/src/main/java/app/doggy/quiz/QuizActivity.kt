package app.doggy.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    //クイズを作成。
    val quizLists: List<List<String>> = listOf(
        listOf("値を変えられる変数を宣言するときに使う命令は何でしょう？", "var", "val", "Var", "var"),
        listOf("文字列を扱うクラスは何でしょう？", "String", "string", "Strings", "String"),
        listOf("LinearLayoutで縦方向にViewを並べるとき、orientationアトリビュートの値は何でしょう？", "vertical", "horizontal", "height", "vertical")
    )

    //クイズをシャッフルする。
    val shuffledQuizLists: List<List<String>> = quizLists.shuffled()

    //クイズ数をカウントする変数。
    var quizCount = 0

    //正解を格納する変数。
    var correctAnswer = ""

    //正解数をカウントする変数。
    var correctCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //選択肢のボタンの配列。
        val answerButtons: Array<Button> = arrayOf(answerButton1, answerButton2, answerButton3)

        //クリックリスナの設定。
        for (i in answerButtons.indices) {
            answerButtons[i].setOnClickListener(JudgeListener(answerButtons))
        }

        nextButton.setOnClickListener {

            if (quizCount == quizLists.size) {

                //Intentをインスタンス化。
                val resultIntent = Intent(applicationContext, ResultActivity::class.java)

                //クイズ数をセット。
                resultIntent.putExtra("quizCount", quizCount)

                //正解数をセット。
                resultIntent.putExtra("correctCount", correctCount)

                //画面遷移。
                startActivity(resultIntent)

                //クイズ画面を終了。
                finish()

            } else {

                //判定画像を非表示にする。
                judgeImage.isInvisible = true

                //次へボタンを非表示にする。
                nextButton.isInvisible = true

                //回答ボタンを押せるようにする。
                for (i in answerButtons.indices) {
                    answerButtons[i].isEnabled = true
                }

                //正解の表示をリセットする。
                correctAnswerText.text = ""

                //次の問題を表示する。
                showQuestion(answerButtons)
            }

        }

        //最初の問題を表示。
        showQuestion(answerButtons)

    }

    //画面に表示されるクイズを作る。
    fun showQuestion(answerButtons: Array<Button>) {

        //クイズの中身を確認する。
        Log.d("shuffledQuizLists", shuffledQuizLists[quizCount][0].toString())

        //問題文を表示。
        questionText.text = shuffledQuizLists[quizCount][0]

        //選択肢を表示。
        for (i in answerButtons.indices) {
            answerButtons[i].text = shuffledQuizLists[quizCount][i+1]
        }

        //正解を変数に格納。
        correctAnswer = shuffledQuizLists[quizCount][4]
    }

    //回答の正誤判断。
    fun judgeAnswer(answerText: String, answerButtons: Array<Button>) {

        if (answerText == correctAnswer) {

            //画像を設定。
            judgeImage.setImageResource(R.drawable.maru)

            //正解数を1増やす。
            correctCount += 1

        } else {

            //画像を設定。
            judgeImage.setImageResource(R.drawable.batsu)

        }

        //正解を表示。
        showAnswer(answerButtons)

        //quizCountを1増やす。
        quizCount += 1
    }

    //正解を表示する時の処理。
    fun showAnswer(answerButtons: Array<Button>) {

        //正解を表示する。
        correctAnswerText.text = "正解：$correctAnswer"

        //判定画像を表示する。
        judgeImage.isVisible = true

        //次へボタンを表示する。
        nextButton.isVisible = true

        //回答ボタンを押せなくする。
        for (i in answerButtons.indices) {
            answerButtons[i].isEnabled = false
        }
    }

    private inner class JudgeListener(val answerButtons: Array<Button>): View.OnClickListener {
        override fun onClick(view: View) {

            when(view.id) {
                R.id.answerButton1 -> judgeAnswer(answerButton1.text.toString(), answerButtons)
                R.id.answerButton2 -> judgeAnswer(answerButton2.text.toString(), answerButtons)
                R.id.answerButton3 -> judgeAnswer(answerButton3.text.toString(), answerButtons)
            }

        }
    }
}
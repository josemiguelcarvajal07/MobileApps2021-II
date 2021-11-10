package co.edu.unal.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView




class MainActivity : AppCompatActivity() {

    private val boardCells = Array(3){ arrayOfNulls<ImageView>(3)}

    var board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBoard()

        button_restart.setOnClickListener {
            board = Board()
            text_view_result.text = ""
            mapBoardToUi()
        }

        button_quit.setOnClickListener {

        val dialog = AlertDialog.Builder(this).setTitle("Quit Game")
            .setMessage("Do you really want to quit?")
            .setIcon(R.mipmap.tictactoeicon)
            .setPositiveButton("Yes") { _, _ ->
                finishAndRemoveTask()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()

        val message = dialog.findViewById<View>(android.R.id.message) as TextView?
        message?.textSize = 26f
        //TODO - Create custom alert dialog

        }
    }

    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.o)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.x)
                        boardCells[i][j]?.isEnabled = false
                    }
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    private fun loadBoard(){
        for(i in boardCells.indices){
            for(j in boardCells.indices){
                boardCells[i][j] = ImageView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {

        override fun onClick(p0: View?) {

            if (!board.isGameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, Board.PLAYER)
                board.minimax(0, Board.COMPUTER)
                board.computersMove?.let {
                    board.placeMove(it, Board.COMPUTER)
                }
                mapBoardToUi()
            }

            when {
                board.hasComputerWon() -> text_view_result.text = "Android Won!"
                board.hasPlayerWon() -> text_view_result.text = "You Won!"
                board.isGameOver -> text_view_result.text = "It's a tie!"
            }
        }
    }
}
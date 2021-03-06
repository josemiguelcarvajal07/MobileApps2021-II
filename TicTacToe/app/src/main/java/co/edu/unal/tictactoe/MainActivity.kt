package co.edu.unal.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView

var singleUser = false
class MainActivity : AppCompatActivity() {

    lateinit var singlePlayerBtn: Button
    lateinit var multiPlayerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlePlayerBtn = findViewById(R.id.single_player)
        multiPlayerBtn = findViewById(R.id.multi_player)

        singlePlayerBtn.setOnClickListener {
            singleUser = true
            startActivity(Intent(this, GameplayActivity::class.java))
        }

        multiPlayerBtn.setOnClickListener {
            singleUser = false
            startActivity(Intent(this, MultiPlayerGameSelectionActivity::class.java))
        }



        button_quit.setOnClickListener {
        val dialog = AlertDialog.Builder(this).setTitle("Quit Game")
            .setMessage("Do you really want to quit?")
            .setIcon(R.mipmap.tictactoeicon)
            .setPositiveButton("Yes") { _, _ ->
                finishAndRemoveTask()
            }
            .setNeutralButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        val message = dialog.findViewById<View>(android.R.id.message) as TextView?
        message?.textSize = 26f
        }

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val aboutDialog = AlertDialog.Builder(this)

        return when (item.itemId) {

            R.id.about_item -> {
                aboutDialog.setTitle("About this game")
                    .setMessage("Tic Tac Toe by:\n\nJose Miguel Carvajal Jimenez\n\nDon't let android beat you!")
                    .setIcon(R.mipmap.tictactoeicon)
                    .setPositiveButton("Close"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
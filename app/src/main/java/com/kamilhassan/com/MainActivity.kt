package com.kamilhassan.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kamilhassan.com.models.BoardSize
import com.kamilhassan.com.models.MemoryCard
import com.kamilhassan.com.models.MemoryGame
import com.kamilhassan.com.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN ACTIVITY"
    }

    private lateinit var rvBoard: RecyclerView
    private lateinit var clRoot: ConstraintLayout
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter
    private val boardSize: BoardSize = BoardSize.EASY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        clRoot = findViewById(R.id.clRoot)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        // creating game
        memoryGame = MemoryGame(boardSize)

        // creating adapter and layout (Recycler view logic)
        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object:
            MemoryBoardAdapter.CardClickListener {
            override fun onCardClick(position: Int) {
                updateGameWithFlip(position)
            }
        })
        rvBoard.adapter = adapter
        rvBoard.hasFixedSize()
        rvBoard.layoutManager= GridLayoutManager(this, boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        // error handling
        if(memoryGame.hasWonGame()){
            Snackbar.make(clRoot, "You have already won!", Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.isCardFacedUp(position)){
            Snackbar.make(clRoot, "Invalid move!", Snackbar.LENGTH_LONG).show()
            return
        }
        memoryGame.flipCard(position)
        adapter.notifyDataSetChanged()
    }
}
package com.example.blackjack.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack.R


class PlayingRoom : AppCompatActivity() {

    public var room_id = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playroom)
        //setSupportActionBar(findViewById(R.id.toolbar))

    }



}
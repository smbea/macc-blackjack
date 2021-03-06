package com.example.blackjack

import android.util.Log
import com.example.blackjack.models.Game
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocketFrame
import org.json.JSONObject

class CommunicationManager {

    private val ws = WebSocketFactory().createSocket("ws://10.0.2.2:8080", 5000)

    fun connect() {
        // Create a WebSocket with a socket connection timeout value.
        WebSocketFactory().verifyHostname = false


        // Register a listener to receive WebSocket events.
        ws.addListener(object : WebSocketAdapter() {
            override fun onTextMessage(websocket: WebSocket?, text: String?) {
                super.onTextMessage(websocket, text)

                if (text != null) {
                    Log.v("WSS", text)
                    parseMessage(text)
                }
            }

            override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                super.onCloseFrame(websocket, frame)
                Log.v("WSS", "closing socket")
            }
        })

        ws.connect()
    }

    fun sendMessage(msg: JSONObject) {
        ws.sendText(msg.toString())
    }

    fun parseMessage(message: String) {

        val msg = JSONObject(message)

        when (msg.opt("type")) {
            "res_hit" -> {
                val status = msg.opt("status") as String
                val newCard = JSONObject(msg.opt("new_card") as String)
                val handValue = msg.opt("hand_value") as String
                Game.currentGameController.updateHit(status, newCard, handValue)
            }
            "res_fold" -> {
                val status = msg.opt("status") as String
            }
            "res_bet" -> {
                val status = msg.opt("status") as String
                Game.response = msg
                Game.responseStatus = true
                Game.amountAvailable = (msg.opt("balance") as String).toInt()
            }
            "res_stand" -> {
                val status = msg.opt("status") as String
                Game.currentGameController.updateStand(status)
            }
            "res_join_room" -> {
                val status = msg.opt("status") as String
                Game.response = msg
                Game.responseStatus = true
            }
            "res_create_room" -> {
                val status = msg.opt("status") as String
                Game.response = msg
                Game.responseStatus = true
            }
            "your_turn" -> {
                Log.i("your_turn", "updateTurn")

                Game.currentGameController.updateTurn(true)


            }
            "update_op" -> {
                val handValue = msg.opt("hand_value") as String
                val newCard = JSONObject(msg.opt("new_card") as String)
                Game.currentGameController.updateOpponent(newCard, handValue)
            }
            "game_start" -> {

                //how it is in code
                val player = msg.opt("players") as JSONObject
                val balance = player.opt("balance") as Int
                val opponentUsername = "username123"

                Game.startGame(opponentUsername, balance)
            }
            "deal_card" -> {
                 Game.currentGameController.dealCard(msg)
            }

        }

    }
}
package com.example.blackjack.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.blackjack.R
import com.example.blackjack.models.Game
import com.example.blackjack.views.activities.PlayingRoom
import kotlinx.android.synthetic.main.frag_join_room_menu.*
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class JoinRoom : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_join_room_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btn_join_room.setOnClickListener {
            try {
                val toInt = playroom_id_field.text.toString().toString().toInt()
                val response = Game.joinRoom(toInt)
                Game.readyUp()
                if (response=="ok") {
                    val intent = Intent(activity, PlayingRoom::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                }

            }catch (e:NumberFormatException) { }
        }

    }


}
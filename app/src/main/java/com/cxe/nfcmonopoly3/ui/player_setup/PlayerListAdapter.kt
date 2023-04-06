package com.cxe.nfcmonopoly3.ui.player_setup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cxe.nfcmonopoly3.data.entities.Player
import com.example.nfcmonopoly3.databinding.FragmentHomePlayerListItemBinding

class PlayerListAdapter(
    private val onEdit: (Player) -> Unit,
    private val onDelete: (Player) -> Unit
) : ListAdapter<Player, PlayerListAdapter.PlayerListViewHolder>(DiffCallback) {

    inner class PlayerListViewHolder(private val binding: FragmentHomePlayerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player, onDelete: (View) -> Unit, onEdit: (View) -> Unit) {
            binding.player = player
            binding.playerDeleteBtn.setOnClickListener(onDelete)
            binding.playerEditBtn.setOnClickListener(onEdit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        return PlayerListViewHolder(
            FragmentHomePlayerListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        val currentPlayer = getItem(position)

        val onEditBtn: (View) -> Unit = { onEdit(currentPlayer) }
        val onDeleteBtn: (View) -> Unit = { onDelete(currentPlayer) }

        holder.bind(currentPlayer, onEditBtn, onDeleteBtn)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldPlayer: Player, newPlayer: Player): Boolean {
                return oldPlayer === newPlayer
            }

            override fun areContentsTheSame(
                oldPlayer: Player,
                newPlayer: Player
            ): Boolean {
                return oldPlayer.playerId == newPlayer.playerId
            }
        }
    }
}
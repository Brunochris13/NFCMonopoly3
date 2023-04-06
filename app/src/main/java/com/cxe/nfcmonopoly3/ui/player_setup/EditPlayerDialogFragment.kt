package com.cxe.nfcmonopoly3.ui.player_setup

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cxe.nfcmonopoly3.logic.enums.CardColor
import com.cxe.nfcmonopoly3.logic.viewmodel.AppViewModel
import com.cxe.nfcmonopoly3.util.CARD_ID_TAG
import com.example.nfcmonopoly3.databinding.PlayerEditDialogBinding

private const val LOG_TAG = "EditPlayerDialogFragment"

class EditPlayerDialogFragment : DialogFragment() {

    // Binding
    private var _binding: PlayerEditDialogBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: AppViewModel by activityViewModels()

    // On Dismiss
    lateinit var onDismissListener: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Fix corners
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        _binding = PlayerEditDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Select editText
        binding.editTextPlayerName.requestFocus()

        var isNewPlayer = true

        // Get args from Bundle
        @Suppress("DEPRECATION") val cardColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(CARD_ID_TAG, CardColor::class.java)
        } else {
            arguments?.getSerializable(CARD_ID_TAG)
        } as CardColor?

        // Check if cardColor is null
        if (cardColor == null) {
            Log.e(LOG_TAG, "cardColor = null")
            return
        }

        // Set binding's cardId
        binding.cardColor = cardColor

        if (viewModel.playerExists(cardColor)) {
            val player = viewModel.getPlayer(cardColor)

            // Show Previous Player Name
            binding.editTextPlayerName.editText?.setText(player.name)

            isNewPlayer = false
        }

        // Save button
        binding.saveNameButton.setOnClickListener {
            val playerName = binding.editTextPlayerName.editText?.text.toString()
            if (playerName == "") {
                Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
            } else {
                // New Player
                if (isNewPlayer) {
                    viewModel.addPlayer(cardColor, playerName)
                } else { // Existing Player
                    viewModel.changePlayerName(cardColor, playerName)
                }
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (this::onDismissListener.isInitialized)
            onDismissListener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // Show keyboard when the dialog is created
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
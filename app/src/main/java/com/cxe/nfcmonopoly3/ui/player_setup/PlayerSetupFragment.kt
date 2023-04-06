package com.cxe.nfcmonopoly3.ui.player_setup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxe.nfcmonopoly3.logic.enums.CardColor
import com.cxe.nfcmonopoly3.logic.viewmodel.AppViewModel
import com.cxe.nfcmonopoly3.util.CARD_ID_TAG
import com.cxe.nfcmonopoly3.util.isDebitCard
import com.example.nfcmonopoly3.R
import com.example.nfcmonopoly3.databinding.FragmentPlayerSetupBinding

class PlayerSetupFragment : Fragment() {

    // Constants
    private val LOG_TAG = "PlayerSetupFragment"
    val EDIT_PLAYER_DIALOG_TAG = "edit_player_dialog_tag"

    // Default Version
    val DEFAULT_VERSION = "English"

    // Version Name -> json file name, mega
    val VERSIONS = mapOf(
        DEFAULT_VERSION to Pair("properties_en.json", false),
        "Greek" to Pair("properties_gr.json", false),
        "English Mega" to Pair("mega_properties_en.json", true)
    )

    // Binding
    private var _binding: FragmentPlayerSetupBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: AppViewModel by activityViewModels()

    // RecyclerViewAdapter
    private lateinit var recyclerViewAdapter: PlayerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the hasGameStarted variable in the viewModel
        viewModel.hasGameStarted.observe(viewLifecycleOwner) { gameStarted ->
            if (gameStarted)
            // Navigate to GameFragment
                findNavController().navigate(R.id.action_PlayerSetupFragment_to_GameFragment)
        }

        // RecyclerView
        val recyclerView = binding.recyclerviewHome
        recyclerViewAdapter = PlayerListAdapter(
            onEdit = {
                // Open the EditPlayer dialog
                editPlayerNameDialog(it.cardColor)
            },
            onDelete = { viewModel.deletePlayer(it) }
        )
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // RecyclerView Observer
        viewModel.players?.observe(viewLifecycleOwner) { players ->
            recyclerViewAdapter.submitList(players)
        }

        // Version Dropdown
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.version_list_item,
            VERSIONS.keys.toList()
        )
        (binding.versionMenu.editText as? AutoCompleteTextView?)?.setAdapter(adapter)
        // Default value
        (binding.versionMenu.editText as? AutoCompleteTextView?)?.setText(DEFAULT_VERSION, false)

        // Start Button
        binding.startBtn.setOnClickListener {

            // Check if we have 2 or more players
            if (viewModel.players?.value!!.size < 2) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.not_enough_players),
                    Toast.LENGTH_SHORT
                ).show()
                Log.i(LOG_TAG, resources.getString(R.string.not_enough_players))

                return@setOnClickListener
            }

            // Get selectedVersion from the Versions Menu
            val selectedVersion = binding.versionMenu.editText?.text.toString()

            // Check if the value is not null
            if (selectedVersion == "") {
                Toast.makeText(
                    context,
                    resources.getString(R.string.version_menu_nothing_selected),
                    Toast.LENGTH_SHORT
                ).show()
                Log.i(LOG_TAG, resources.getString(R.string.version_menu_nothing_selected))

                return@setOnClickListener
            }

            // Get Mega Status
            val mega = VERSIONS[selectedVersion]!!.second

            // Set the starting money for all Players
            viewModel.setPlayersStartingMoney(mega)

            // Read Properties from JSON
            val jsonFileName = VERSIONS[selectedVersion]!!.first
            viewModel.createPropertiesFromJson(jsonFileName, mega)

            // Navigate to GameFragment
            findNavController().navigate(R.id.action_PlayerSetupFragment_to_GameFragment)
        }
    }

    fun onNewIntent(msg: String) {
        // Check if it is a Debit Card
        if (isDebitCard(msg)) {
            val cardColor = CardColor.valueOf(msg)

            // Open the EditPlayer dialog
            editPlayerNameDialog(cardColor)
        } else {
            Toast.makeText(context, "Wrong Card", Toast.LENGTH_SHORT).show()
            Log.i(LOG_TAG, "Wrong Card")
        }
    }

    private fun editPlayerNameDialog(cardColor: CardColor) {
        // Arguments
        val bundle = Bundle()
        bundle.putSerializable(CARD_ID_TAG, cardColor)

        // Dialog
        val dialog = EditPlayerDialogFragment()
        dialog.arguments = bundle

        dialog.show(parentFragmentManager, EDIT_PLAYER_DIALOG_TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
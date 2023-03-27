package com.example.nfcmonopoly3

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cxe.nfcmonopoly20.logic.nfcparser.NdefMessageParser
import com.example.nfcmonopoly3.databinding.ActivityMainBinding
import com.example.nfcmonopoly3.logic.viewmodel.AppViewModel

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // ViewModel
    private lateinit var viewModel: AppViewModel

    private var navHostFragment: Fragment? = null

    // NFC
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var nfcParser: NdefMessageParser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Disable Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // ViewModel
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        // NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcParser = NdefMessageParser()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // NFC
        val msg = getMsgFromTag(intent!!)
        if (msg == null) {
            Toast.makeText(this, "Empty Tag", Toast.LENGTH_SHORT).show()
            Log.i(LOG_TAG, "Empty Tag")
            return
        } else {
            // Get current Fragment
            when (val currentFragment =
                navHostFragment?.childFragmentManager?.primaryNavigationFragment) {
//                is HomeFragment -> currentFragment.onNewIntent(msg)
//                is GameFragment -> currentFragment.onNewIntent(msg)
//                is TradeFragment -> currentFragment.onNewIntent(msg)
            }
        }
    }

    private fun getMsgFromTag(intent: Intent): String? {
        val action = intent.action
        val msgs = mutableListOf<NdefMessage>()

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            val rawMsgs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES,
                    NdefMessage::class.java
                )
            } else {
                intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES
                )
            }

            if (rawMsgs != null) {
                for (rawMsg in rawMsgs) {
                    msgs.add(rawMsg as NdefMessage)
                }
            }
        }

        if (msgs.isEmpty())
            return null

        val records = nfcParser.parse(msgs[0])

        if (records.size > 1) {
            Toast.makeText(this, "More than 1 Record", Toast.LENGTH_SHORT).show()
            Log.e(LOG_TAG, "More than 1 Record")
        }

        return records[0]
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun enableForegroundDispatch(activity: AppCompatActivity, adapter: NfcAdapter?) {
        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent =
            PendingIntent.getActivity(
                activity.applicationContext,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()
        filters[0] = IntentFilter()
        with(filters[0]) {
            this?.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            this?.addCategory(Intent.CATEGORY_DEFAULT)
            try {
                this?.addDataType("text/plain")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException(e)
            }
        }
        adapter?.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        if (nfcAdapter != null) {
            if (!nfcAdapter!!.isEnabled)
                showWirelessSettings()

            enableForegroundDispatch(this, this.nfcAdapter)
        }
    }

    override fun onPause() {
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }
    }

    private fun showWirelessSettings() {
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_NFC_SETTINGS)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
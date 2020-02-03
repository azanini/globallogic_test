package com.azanini.thenotelist.frontend

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.azanini.thenotelist.R
import com.azanini.thenotelist.extensions.conectedToInternet
import com.azanini.thenotelist.interfaces.DataInterface
import com.azanini.thenotelist.interfaces.LayoutInterface
import com.azanini.thenotelist.managers.TypeFaceManager


abstract class CustomActivity : AppCompatActivity(), LayoutInterface, DataInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
        setTypefaces()
        findViewById<Button>(R.id.retryButton).setOnClickListener {
            getAllData()
        }
    }

    override fun setMainContentView(mainContentView: View) {
        findViewById<LinearLayout>(R.id.contant_view).addView(mainContentView)
    }

    override fun setTypefaces() {
        findViewById<TextView>(R.id.errorText).typeface = TypeFaceManager.getTypeFaceByName(this, TypeFaceManager.TRASANDINA_W03_LIGHT)
        findViewById<Button>(R.id.retryButton).typeface = TypeFaceManager.getTypeFaceByName(this, TypeFaceManager.TRASANDINA_W03_MEDIUM)
    }

    private fun isConnected(): Boolean {
        return conectedToInternet()
    }

    override fun onResume() {
        super.onResume()
        setToolbar()
        if (isDataNull) {
            if (isConnected()) {
                getAllData()
            } else {
                toggleNoConnectionScreen(java.lang.Boolean.TRUE, java.lang.Boolean.FALSE, "")
            }
        }
    }

    open fun toggleNoConnectionScreen(show: Boolean, isError: Boolean, message: String?) {
        if (show) {
            findViewById<View>(R.id.contant_view).visibility = View.GONE
            findViewById<View>(R.id.error_screen).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.contant_view).visibility = View.VISIBLE
            findViewById<View>(R.id.error_screen).visibility = View.GONE
        }
        val errorTextView = findViewById<TextView>(R.id.errorText)
        if (isError) {
            errorTextView.text = message ?: ""
        } else {
            errorTextView.text = getString(R.string.no_internet_explanation)
        }
    }
}
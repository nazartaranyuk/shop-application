package com.nazartaraniuk.shopapplication.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nazartaraniuk.shopapplication.R

@Deprecated("Dialog is too archaic") // TODO remove this
class DialogError(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(getString(R.string.error_text))
                .setMessage(message)
                .setNegativeButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.cancel()
                }
            return builder.create()

        } ?: throw IllegalArgumentException("Activity cannot be null")
    }
}

package com.bed.seller.presentation.extensions

import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.getTextChanged(value: (String) -> Unit) {
    doOnTextChanged { text, _, _, _ -> value(text.toString())  }
}

fun TextInputEditText.actionKeyboard(
    action: Int = EditorInfo.IME_ACTION_DONE,
    stateAction: () -> Unit
) {
    setOnEditorActionListener { _, keyCode, _ ->
        if (keyCode == action) {
            stateAction()

            return@setOnEditorActionListener true
        }

        return@setOnEditorActionListener false
    }
}

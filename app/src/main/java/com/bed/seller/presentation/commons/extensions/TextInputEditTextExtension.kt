package com.bed.seller.presentation.commons.extensions

import android.view.inputmethod.EditorInfo
import androidx.core.os.postDelayed
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.getTextChanged(value: (String) -> Unit) {
    doOnTextChanged { text, _, _, _ -> value(text.toString()) }
}

fun TextInputEditText.debounce(delay: Long = 400L, action: (String) -> Unit) {
    doAfterTextChanged { text ->
        var counter = getTag(id) as? Int ?: 0
        handler?.removeCallbacksAndMessages(counter)
        handler?.postDelayed(delay, ++counter) { action(text.toString()) }

        setTag(id, counter)
    }
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

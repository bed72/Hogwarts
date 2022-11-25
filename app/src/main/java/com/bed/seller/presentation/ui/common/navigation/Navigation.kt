package com.bed.seller.presentation.ui.common.navigation

import android.os.Bundle
import androidx.core.os.bundleOf

enum class Navigation(val value: String) {
    TO_HOME("userName") {
        override fun buildParams(param: String): Bundle = bundleOf(value to param)
    };

    abstract fun buildParams(param: String): Bundle
}

package com.bed.seller.presentation.ui.auth.signin

import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    sealed class Actions {
        object SignIn : Actions()
    }

    sealed class States
}

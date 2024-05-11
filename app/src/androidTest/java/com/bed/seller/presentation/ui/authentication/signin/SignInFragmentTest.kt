package com.bed.seller.presentation.ui.authentication.signin

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf

import kotlinx.coroutines.runBlocking

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

import com.google.android.material.snackbar.Snackbar

import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

import  com.bed.seller.R

import com.bed.seller.presentation.extension.launchFragmentInHiltContainer

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(CoroutinesUseCaseModule::class)
internal class SignInFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        launchFragmentInHiltContainer<SignInFragment>()
    }

    @Test
    fun shouldRenderTheSignInFragmentWithSuccess(): Unit = runBlocking {
        onView(withId(R.id.title_text)).check(matches(withText("Bem vindo!")))
        onView(withId(R.id.subtitle_text)).check(matches(withText("Olá.\nAcesse sua conta e divulge seus produtos")))

        onView(withId(R.id.sign_in_button)).perform(click())
        onView(
            allOf(
                isAssignableFrom(Snackbar.SnackbarLayout::class.java),
                hasDescendant(withText("Ops! Por favor, verifique os campos e tente novamente."))
            )
        ).check(matches(isDisplayed()))
    }
}

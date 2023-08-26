package com.bed.seller.presentation.ui.authentication.signin

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.runner.RunWith

import kotlinx.coroutines.runBlocking

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import  com.bed.seller.R

import com.bed.seller.framework.modules.usecases.CoroutinesUseCaseModule

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
    fun shouldRenderTheSignInFragment(): Unit = runBlocking {
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))
    }
}

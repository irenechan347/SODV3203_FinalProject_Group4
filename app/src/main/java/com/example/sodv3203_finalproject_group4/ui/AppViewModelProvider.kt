package com.example.sodv3203_finalproject_group4.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sodv3203_finalproject_group4.ShoppingBuddyApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for EventViewModel
        initializer {
            EventViewModel(shoppingBuddyApplication().container.shoppingBuddyRepository)
        }
    }
}

fun CreationExtras.shoppingBuddyApplication(): ShoppingBuddyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ShoppingBuddyApplication)

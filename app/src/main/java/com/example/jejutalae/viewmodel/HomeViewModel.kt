package com.example.jejutalae.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

class HomeViewModel : ViewModel() {
    var text1 by mutableStateOf("")
    var text2 by mutableStateOf("")
    var isSearching by mutableStateOf(false)

    private val _selectedTime = MutableStateFlow<LocalTime?>(null)
    val selectedTime: StateFlow<LocalTime?> = _selectedTime

    fun updateSelectedTime(time: LocalTime) {
        _selectedTime.value = time
    }

    fun toggleSearch(searching: Boolean) {
        isSearching = searching
    }
}

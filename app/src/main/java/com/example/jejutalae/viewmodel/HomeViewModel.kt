package com.example.jejutalae.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

class HomeViewModel : ViewModel() {
    var text1 by mutableStateOf("제주국제공항")
    var text2 by mutableStateOf("오설록 티 뮤지엄")

    // 선택된 시간을 저장할 상태 변수
    private val _selectedTime = MutableStateFlow<LocalTime?>(null)
    val selectedTime: StateFlow<LocalTime?> = _selectedTime

    // 시간 업데이트 함수
    fun updateSelectedTime(time: LocalTime) {
        _selectedTime.value = time
    }
}
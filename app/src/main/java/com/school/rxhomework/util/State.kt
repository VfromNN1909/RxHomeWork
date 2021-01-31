package com.school.rxhomework.util

import com.school.rxhomework.model.Item

sealed class State {
    object Loading : State()
    data class Loaded(val content: List<Item>) : State()
}

/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import kotlinx.coroutines.flow.StateFlow
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * 仅当界面可见时，流水线才应有效。为此，请使用 SharingStarted.WhileSubscribed()。
 * 如需配置从最后一个订阅者消失到停止共享协程之间的延迟时间（以毫秒为单位），
 * 请将 TIMEOUT_MILLIS 传递给 SharingStarted.WhileSubscribed() 方法。
 */
class HomeViewModel(itemsRepository: ItemsRepository): ViewModel(){
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val homeUiState: StateFlow<HomeUiState> = itemsRepository.getAllItemsStream().map { HomeUiState(it) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Item> = listOf())

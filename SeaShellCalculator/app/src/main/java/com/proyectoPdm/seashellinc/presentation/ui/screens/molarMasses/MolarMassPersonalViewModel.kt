package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.model.Compound
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.repository.CompoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MolarMassPersonalViewModel @Inject constructor(
    private val compoundRepository: CompoundRepository
) : ViewModel() {

    private val _compoundList = MutableStateFlow<List<Compound>>(emptyList())

    private val _query = MutableStateFlow<String>("")
    val query: StateFlow<String> = _query

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String?> = _errorMessage

    val filteredList : StateFlow<List<Compound>> = combine(query, _compoundList) { text, list ->
        if (text.isBlank()) list
        else list.filter { item ->
            item.compoundName.contains(text, ignoreCase = true) || item.chemicalFormula.contains(
                text,
                ignoreCase = true
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    fun getCompoundList() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                when(val result = compoundRepository.getCompoundList()){
                    is Result.Success -> {
                        _compoundList.value = result.data
                    }
                    is Result.Failure -> {
                        _errorMessage.value = result.exception.message.toString()
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onValueChange(newQuery : String) {
        _query.value = newQuery
    }
}
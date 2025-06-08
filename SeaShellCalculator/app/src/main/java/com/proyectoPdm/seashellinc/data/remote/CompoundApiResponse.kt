package com.proyectoPdm.seashellinc.data.remote

import com.proyectoPdm.seashellinc.data.model.CompoundResponse

data class CompoundApiResponse(
    val message : List<CompoundResponse>,
    val status : String
)
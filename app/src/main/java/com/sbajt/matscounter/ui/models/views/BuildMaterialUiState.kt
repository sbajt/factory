package com.sbajt.matscounter.ui.models.views

data class BuildMaterialUiState(
    val name: String,
    val amount: Int,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BuildMaterialUiState

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

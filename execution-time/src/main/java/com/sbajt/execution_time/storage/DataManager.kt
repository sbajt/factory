package com.sbajt.execution_time.storage

interface DataManager {

    fun getStoredValues(): List<RowWrapper>

    fun addRow(row: RowWrapper)

    fun clearData()
}

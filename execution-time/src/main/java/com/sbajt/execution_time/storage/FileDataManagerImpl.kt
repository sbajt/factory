package com.sbajt.execution_time.storage

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class FileDataManagerImpl(context: Context) : CoroutineScope, DataManager {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
    private val file: File = File(context.filesDir, FILE_NAME)

    private val TAG = this::class.java.simpleName

    init {
        if (file.exists()) {
            Log.d(TAG, "Opened file ${context.filesDir}/$FILE_NAME")
        } else {
            file.createNewFile()
            Log.d(TAG, "Created file ${context.filesDir}/$FILE_NAME")
            writeHeaders()
        }
    }

    private fun writeHeaders() {
        CoroutineScope(context = coroutineContext).launch {
            FileHeader.entries.forEachIndexed { index, fileHeader ->
                if (index == FileHeader.entries.lastIndex) {
                    file.appendText(fileHeader.text)
                } else {
                    file.appendText("${fileHeader.text},")
                }
            }
        }
    }

    override fun getStoredValues(): List<RowWrapper> {
        return file.readLines()
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(",").map { it.trim() }
                val key = parts.firstOrNull() ?: ""
                val values = parts.drop(1).mapNotNull { it.toDoubleOrNull() }
                RowWrapper(key = key, valueList = values)
            }
    }

    override fun clearData() {
        CoroutineScope(context = coroutineContext).launch {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            writeHeaders()
        }
    }

    override fun addRow(row: RowWrapper) {
        CoroutineScope(context = coroutineContext).launch {
            file.appendText(row.valueList.joinToString(separator = ",", prefix = "\n${row.key},"))
        }
    }

    internal enum class FileHeader(val text: String) {
        KEY("Key"),
        INITIAL_TIME("Initial time (ms)"),
        AVERAGE_TIME("Average time (ms)"),
        MIN_TIME("Min time (ms)"),
        MAX_TIME("Max time (ms)");
    }

    companion object {

        private const val FILE_NAME = "time_measurements.txt"
    }
}

package com.sbajt.execution_time.time

import android.util.Log
import com.sbajt.execution_time.storage.DataManager
import com.sbajt.execution_time.storage.RowWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

class TimeMeasureManagerImpl(
    private val dataManager: DataManager,
) : TimeMeasureManager, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    private val TAG = this::class.java.simpleName

    override fun measureExecutionTime(
        repeat: Int,
        parentClassName: String?,
        methodName: String?,
        parameterName: String?,
        keySuffix: String?,
        measurementBlock: () -> Unit,
    ) {
        val key = generateKey(
            parentClassName = parentClassName,
            methodName = methodName,
            parameterName = parameterName,
            keySuffix = keySuffix,
        )
        performMeasurement(repeatCount = repeat, key = key) {
            measurementBlock()
        }
    }

    override fun <T> measureExecutionTime(
        repeat: Int,
        parentClassName: String?,
        methodName: String?,
        parameterName: String?,
        keySuffix: String?,
        measurementBlock: () -> T,
    ): T {
        val key = generateKey(
            parentClassName = parentClassName,
            methodName = methodName,
            parameterName = parameterName,
            keySuffix = keySuffix,
        )
        val returnValue = performMeasurement(repeatCount = repeat, key = key) {
            measurementBlock()
        }
        return returnValue
    }

    override fun clearData() {
        dataManager.clearData()
    }

    private fun generateKey(
        parentClassName: String?,
        methodName: String?,
        parameterName: String?,
        keySuffix: String?,
    ): String {
        val parts = mutableListOf<String>()
        parentClassName?.let { parts.add(it) }
        methodName?.let { parts.add(it) }
        parameterName?.let { parts.add(it) }
        keySuffix?.let { parts.add(it) }
        return parts.joinToString(separator = "_")
    }

    private fun generateKey(
        parentClassName: String?,
        composableName: String?,
        keySuffix: String?,
    ): String {
        val parts = mutableListOf<String>()
        parentClassName?.let { parts.add(it) }
        composableName?.let { parts.add(it) }
        keySuffix?.let { parts.add(it) }
        return parts.joinToString(separator = "_")
    }

    private fun <T> performMeasurement(
        repeatCount: Int,
        key: String,
        measurementBlock: () -> T,
    ): T {
        val (value, time) = measureTimedValue {
            measurementBlock()
        }
        val initialMeasurementTime = time.inWholeMilliseconds.toDouble()
        measureTimeInBackground(repeatCount, key, measurementBlock, initialMeasurementTime)
        return value
    }

    private fun <T> measureTimeInBackground(
        repeatCount: Int,
        key: String,
        measurementBlock: () -> T,
        initialMeasurementTime: Double,
    ) = CoroutineScope(context = coroutineContext).launch {
        val tempList = mutableListOf<Double>()
        val finalList = mutableListOf<Double>()
        repeat(repeatCount) {
            tempList.add(measureTime { measurementBlock() }.inWholeMilliseconds.toDouble())
        }
        finalList.add(initialMeasurementTime)
        if (tempList.isNotEmpty()) {
            finalList.add(tempList.average())
            finalList.add(tempList.minOrNull() ?: 0.0)
            finalList.add(tempList.maxOrNull() ?: 0.0)
        } else {
            finalList.add(0.0)
            finalList.add(0.0)
            finalList.add(0.0)
        }
        persistRow(key = key, values = finalList)
        logMeasurements(
            key = key,
            initialMeasurementTime = initialMeasurementTime,
            measurementList = tempList,
        )
    }

    private fun persistRow(key: String, values: List<Double>) {
        dataManager.addRow(
            row = createRow(
                key = key,
                values = values,
            )
        )
    }

    private fun createRow(key: String, values: List<Double>) = RowWrapper(
        key = key,
        valueList = values,
    )

    private fun logMeasurements(
        key: String,
        initialMeasurementTime: Double,
        measurementList: List<Double>,
    ) {
        Log.d(TAG, "Initial execution time for $key: $initialMeasurementTime ms")
        if (measurementList.isNotEmpty()) {
            Log.d(TAG, "Average execution time for $key: ${measurementList.average()} ms")
            Log.d(TAG, "Min execution time for $key: ${measurementList.minOrNull()} ms")
            Log.d(TAG, "Max execution time for $key: ${measurementList.maxOrNull()} ms")
        }
    }
}

package com.sbajt.execution_time.time

interface TimeMeasureManager {

    fun measureExecutionTime(
        repeat: Int = DEFAULT_MEASURE_REPEAT,
        parentClassName: String? = null,
        methodName: String? = null,
        parameterName: String? = null,
        keySuffix: String? = null,
        measurementBlock: () -> Unit,
    )

    fun <T> measureExecutionTime(
        repeat: Int = DEFAULT_MEASURE_REPEAT,
        parentClassName: String? = null,
        methodName: String? = null,
        parameterName: String? = null,
        keySuffix: String? = null,
        measurementBlock: () -> T,
    ): T

    fun clearData()

    companion object {
        const val DEFAULT_MEASURE_REPEAT = 10
    }
}

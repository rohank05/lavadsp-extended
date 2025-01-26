package com.therohankumar.echo

import com.github.natanbc.lavadsp.Converter
import kotlin.math.min

class EchoConverter(private val sampleRate: Int) : Converter {
    private var buffer: FloatArray = FloatArray(0)
    private var position = 0
    private var decay = 0.5f
    private var pendingDelay = -1.0

    fun setDelay(seconds: Double) {
        require(seconds > 0) { "Delay must be positive" }
        pendingDelay = seconds
    }

    fun setDecay(value: Float) {
        require(value in 0f..1f) { "Decay must be between 0 and 1" }
        decay = value
    }

    private fun resizeBuffer() {
        if (pendingDelay == -1.0) return

        val newBuffer = FloatArray((sampleRate * pendingDelay).toInt())
        if (buffer.isNotEmpty()) {
            val copyLength = min(buffer.size, newBuffer.size)
            System.arraycopy(buffer, position, newBuffer, 0, copyLength - position)
            System.arraycopy(buffer, 0, newBuffer, copyLength - position, position)
        }

        buffer = newBuffer
        position = 0
        pendingDelay = -1.0
    }

    override fun process(input: FloatArray, inputOffset: Int, output: FloatArray, outputOffset: Int, samples: Int) {
        if (buffer.isEmpty()) {
            input.copyInto(output, outputOffset, inputOffset, inputOffset + samples)
            resizeBuffer()
            return
        }

        for (i in 0 until samples) {
            val current = input[i + inputOffset] + buffer[position] * decay
            buffer[position] = current
            output[i + outputOffset] = current

            position = (position + 1) % buffer.size
        }

        resizeBuffer()
    }
}
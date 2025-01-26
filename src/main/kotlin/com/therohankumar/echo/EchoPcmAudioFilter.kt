package com.therohankumar.echo

import com.github.natanbc.lavadsp.ConverterPcmAudioFilter
import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter

class EchoPcmAudioFilter(
    downstream: FloatPcmAudioFilter?,
    channelCount: Int,
    sampleRate: Int
) : ConverterPcmAudioFilter<EchoConverter>(
    { EchoConverter(sampleRate) },
    downstream,
    channelCount
) {
    fun setDelay(delay: Double) = apply {
        converters().forEach { it.setDelay(delay) }
    }

    fun setDecay(decay: Float) = apply {
        require(decay in 0f..1f) { "Decay must be between 0 and 1" }
        converters().forEach { it.setDecay(decay) }
    }
}
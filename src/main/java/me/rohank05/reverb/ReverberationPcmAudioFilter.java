/**
 * Copyright 2022 Rohan Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.rohank05.reverb;

import com.github.natanbc.lavadsp.ConverterPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;


public class ReverberationPcmAudioFilter extends ConverterPcmAudioFilter<ReverberationConverter> {
    public ReverberationPcmAudioFilter(FloatPcmAudioFilter downstream, int channelCount, int sampleRate){
        super(()-> new ReverberationConverter(sampleRate), downstream, channelCount);
    }

    /**
     * An array of delay times in seconds.
     * @param delays Delays
     */
    public ReverberationPcmAudioFilter setDelays(float[] delays){
        for(ReverberationConverter converter: converters()){
            converter.setDelays(delays);
        }
        return this;
    }

    /**
     * An array of gain values between 0 and 1 for each delay line.
     * @param gains Gains (preferably between zero and one).
     */
    public ReverberationPcmAudioFilter setGains(float[] gains){
        for(ReverberationConverter converter: converters()){
            converter.setGains(gains);
        }
        return this;
    }
}

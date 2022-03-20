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

package me.rohank05.echo;

import com.github.natanbc.lavadsp.ConverterPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;

public class EchoPcmAudioFilter extends ConverterPcmAudioFilter<EchoConverter> {
    public EchoPcmAudioFilter(FloatPcmAudioFilter downstream, int channelCount, int sampleRate){
        super(()-> new EchoConverter(sampleRate), downstream, channelCount);
    }

    /**
     * @param delay A new echo buffer length in seconds.
     */
    public EchoPcmAudioFilter setDelay(double delay){
        for(EchoConverter converter: converters()){
            converter.setDelay(delay);
        }
        return this;
    }

    /**
     * A decay, should be a value between zero and one.
     * @param decay the new decay (preferably between zero and one).
     */
    public EchoPcmAudioFilter setDecay(float decay){
        for(EchoConverter converter: converters()){
            converter.setDecay(decay);
        }
        return this;
    }
}

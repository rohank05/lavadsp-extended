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

import com.github.natanbc.lavadsp.Converter;

public class ReverberationConverter implements Converter {
    private final int sampleRate;
    private double delay;
    private float decay;

    public ReverberationConverter(int sampleRate){
        this.sampleRate = sampleRate;
    }

    /**
     * @param delay A delay in seconds.
     */
    public void setDelay(double delay) {
        this.delay = delay;
    }

    /**
     * A decay, should be a value between zero and one.
     * @param decay the new decay (preferably between zero and one).
     */
    public void setDecay(float decay) {
        this.decay = decay;
    }


    @Override
    public void process(float[] input, int inputOffset, float[] output, int outputOffset, int samples) {
        int delaySamples = (int) ((float) delay * sampleRate);
        for(int i = 0; i < input.length-delaySamples; i++){
            output[i+delaySamples] += (short)((float) input[i]*decay);
        }
    }
}

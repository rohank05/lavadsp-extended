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
    private float[] delays = {0.037f, 0.042f, 0.048f, 0.053f};
    private float[] gains = {0.84f, 0.83f, 0.82f, 0.81f};
    private final float[] buffers;

    private int position;

    public ReverberationConverter(int sampleRate){
        this.sampleRate = sampleRate;
        this.buffers = new float[calculateBufferSize()];
    }

    public void setDelays(float[] delays){
        this.delays = delays;
    }

    public void setGains(float[] gains){
        this.gains = gains;
    }

    private int calculateBufferSize(){
        int maxDelay = (int) (sampleRate * delays[0]);
        for (int i = 1; i < delays.length; i++) {
            maxDelay = Math.max(maxDelay, (int) (sampleRate * delays[i]));
        }
        return maxDelay;
    }

    @Override
    public void process(float[] input, int inputOffset, float[] output, int outputOffset, int samples) {
        for (int i = 0; i < samples; i++) {
            float current = input[i + inputOffset];
            float outputSample = 0;

            // Process each delay line
            for (int j = 0; j < delays.length; j++) {
                int delaySamples = (int) (sampleRate * delays[j]);
                float gain = gains[j];

                // Read from delay buffer
                int readPosition = position - delaySamples;
                if (readPosition < 0) {
                    readPosition += buffers.length;
                }
                float delayedSample = buffers[readPosition];

                // Update delay buffer
                buffers[position] = current + (delayedSample * gain);

                // Update output sample
                outputSample += delayedSample;

                // Update position
                position++;
                if (position >= buffers.length) {
                    position = 0;
                }
            }

            // Add the output sample to the current sample
            output[i + outputOffset] = current + (outputSample / delays.length);
        }
    }
}

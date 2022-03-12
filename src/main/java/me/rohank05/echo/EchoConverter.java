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

import com.github.natanbc.lavadsp.Converter;

public class EchoConverter implements Converter {
    private final int sampleRate;
    private float[] echoBuffer;
    private double delay;
    private int position;
    private float decay;
    public EchoConverter(int sampleRate){
        this.sampleRate = sampleRate;
    }

    /**
     * @param delay A new echo buffer delay in seconds.
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

    //Apply Echo length to the buffer
    private void applyNewEchoLength(){
        if(this.delay == -1) return;
        float[] newEchoBuffer = new float[(int) (sampleRate * delay)];
        if(echoBuffer != null){
            for(int i = 0; i< newEchoBuffer.length; i++){
                if(position >= echoBuffer.length){
                    position = 0;
                }
                newEchoBuffer[i] = echoBuffer[position];
                position++;
            }
        }
        this.echoBuffer = newEchoBuffer;
        delay = -1;

    }

    @Override
    public void process(float[] input, int inputOffset, float[] output, int outputOffset, int samples) {
        if(echoBuffer != null){
            for(int i = 0; i < samples; i++){
                if(position >= echoBuffer.length){
                    position = 0;
                }
                float current = input[i+inputOffset];
                //output is the input added with the decayed echo
                current = current + echoBuffer[position] * decay;
                //store the sample in the buffer
                echoBuffer[position] = current;
                position++;
                output[i+outputOffset] = current;
            }
        }
        applyNewEchoLength();
    }
}

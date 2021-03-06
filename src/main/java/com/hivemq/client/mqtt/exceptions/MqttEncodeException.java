/*
 * Copyright 2018 dc-square and the HiveMQ MQTT Client Project
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
 *
 */

package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;
import org.jetbrains.annotations.NotNull;

/**
 * Exception that is used if an encoding error occurred.
 *
 * @author Silvio Giebl
 * @since 1.0
 */
public class MqttEncodeException extends AsyncRuntimeException {

    public MqttEncodeException(final @NotNull String message) {
        super(message);
    }

    private MqttEncodeException(final @NotNull MqttEncodeException e) {
        super(e);
    }

    @Override
    protected @NotNull MqttEncodeException copy() {
        return new MqttEncodeException(this);
    }
}

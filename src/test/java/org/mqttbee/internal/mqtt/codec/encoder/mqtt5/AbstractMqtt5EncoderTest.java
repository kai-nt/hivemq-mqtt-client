/*
 * Copyright 2018 The MQTT Bee project
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

package org.mqttbee.internal.mqtt.codec.encoder.mqtt5;

import org.jetbrains.annotations.NotNull;
import org.mqttbee.internal.mqtt.MqttClientConfig;
import org.mqttbee.internal.mqtt.MqttClientExecutorConfigImpl;
import org.mqttbee.internal.mqtt.codec.encoder.AbstractMqttEncoderTest;
import org.mqttbee.internal.mqtt.codec.encoder.MqttMessageEncoders;
import org.mqttbee.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import org.mqttbee.mqtt.MqttVersion;

class AbstractMqtt5EncoderTest extends AbstractMqttEncoderTest {

    AbstractMqtt5EncoderTest(final @NotNull MqttMessageEncoders messageEncoders, final boolean connected) {
        super(messageEncoders, connected, createClientData());
    }

    private static MqttClientConfig createClientData() {
        return new MqttClientConfig(MqttVersion.MQTT_5_0, MqttClientIdentifierImpl.of("test"), "localhost", 1883,
                MqttClientExecutorConfigImpl.DEFAULT, null, null, false, null);
    }
}
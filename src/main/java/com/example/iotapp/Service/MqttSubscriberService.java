package com.example.iotapp.Service;

import com.example.iotapp.Model.DeviceData;
import com.example.iotapp.repository.DeviceDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class MqttSubscriberService {

    private final MqttClient mqttClient;
    private final DeviceDataRepository deviceDataRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MqttSubscriberService(MqttClient mqttClient, DeviceDataRepository deviceDataRepository) {
        this.mqttClient = mqttClient;
        this.deviceDataRepository = deviceDataRepository;
        this.objectMapper = new ObjectMapper(); // Initialize ObjectMapper for JSON parsing
    }

    @PostConstruct
    public void subscribe() throws MqttException {
        mqttClient.subscribe("iot/device/data", (topic, message) -> {
            String payload = new String(message.getPayload());
            System.out.println("Received data: " + payload);

            try {
                JsonNode jsonNode = objectMapper.readTree(payload);
                DeviceData deviceData = new DeviceData();
                deviceData.setTemperature(jsonNode.get("temperature").asDouble());
                deviceData.setHumidity(jsonNode.get("humidity").asDouble());
                deviceData.setTimestamp(LocalDateTime.now());

                deviceDataRepository.save(deviceData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

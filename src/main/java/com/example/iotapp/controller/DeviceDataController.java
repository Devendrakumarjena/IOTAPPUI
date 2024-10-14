package com.example.iotapp.controller;

import com.example.iotapp.Model.DeviceData;
import com.example.iotapp.repository.DeviceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/device-data")
//Testing
public class DeviceDataController {

    private final DeviceDataRepository deviceDataRepository;

    @Autowired
    public DeviceDataController(DeviceDataRepository deviceDataRepository) {
        this.deviceDataRepository = deviceDataRepository;
    }

    @GetMapping
    public List<DeviceData> getAllDeviceData() {
        return deviceDataRepository.findAll();
    }
}

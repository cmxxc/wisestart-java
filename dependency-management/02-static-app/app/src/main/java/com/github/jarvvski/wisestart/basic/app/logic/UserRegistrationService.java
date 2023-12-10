package com.github.jarvvski.wisestart.basic.app.logic;

import com.github.jarvvski.wisestart.basic.app.metrics.MetricsRecorder;

public class UserRegistrationService {
    private final UserRepository userRepository;
    private final MetricsRecorder metricsRecorder;

    public UserRegistrationService(UserRepository userRepository, MetricsRecorder metricsRecorder) {
        this.userRepository = userRepository;
        this.metricsRecorder = metricsRecorder;
    }

    public void registerNewUser(String username) {
        this.metricsRecorder.count("new-user-registrations", 1);
    }
}

package com.github.jarvvski.wisestart.demo.domain;

import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import com.github.jarvvski.wisestart.demo.application.TransferCreatedResponse;

public interface TransferMessaging {
    void acceptNew(CreateTransferCommand createTransferCommand);
    void publishNew(TransferCreatedResponse transferCreatedResponse);
}

package com.github.jarvvski.wisestart.demo.domain;

import java.util.Optional;
import java.util.UUID;

public interface TransferRepository {

    void save(Transfer transfer);

    Optional<Transfer> fetchById(String id);

    default Transfer getById(String id) {
        return this.fetchById(id)
            .orElseThrow(() -> new TransferNotFound(id));
    }

}

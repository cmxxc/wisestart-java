package com.github.jarvvski.wisestart.demo.domain;

import java.util.Optional;
import java.util.UUID;

public interface TransferRepository {

    void save(Transfer transfer);

    Optional<Transfer> fetchById(TransferId transferId);

    default Transfer getById(TransferId transferId) {
        return this.fetchById(transferId)
            .orElseThrow(() -> new TransferNotFound(transferId));
    }

}

package com.github.jarvvski.wisestart.demo.domain;

import com.fasterxml.uuid.Generators;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class TransferId extends EntityId<UUID> {

    private TransferId(UUID uuid) {
        super(uuid);
    }

    private TransferId(String uuidString) {
        super(UUID.fromString(uuidString));
    }

    public static TransferId of(String uuidString) {
        return new TransferId(UUID.fromString(uuidString));
    }

    public static TransferId of(UUID uuidRaw) {
        return new TransferId(uuidRaw);
    }

    public static TransferId next() {
        return new TransferId(Generators.timeBasedEpochGenerator().generate());
    }
}

package com.github.jarvvski.wisestart.demo.domain;

public class TransferNotFound extends RuntimeException {

    public TransferNotFound(TransferId transferId) {
        super("Transfer not found for ID[" + transferId.getValue() + "]");
    }
}

package com.github.jarvvski.wisestart.demo.domain;

public class TransferNotFound extends RuntimeException {

    public TransferNotFound(String id) {
        super("Transfer not found for ID[" + id + "]");
    }
}

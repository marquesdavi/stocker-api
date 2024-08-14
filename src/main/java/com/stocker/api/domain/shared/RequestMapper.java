package com.stocker.api.domain.shared;

import java.io.Serializable;

public interface RequestMapper<D extends Serializable, R extends Serializable>{
    default D toDomain(final R request) {
        throw new UnsupportedOperationException("Operation refused.");
    }

    default R toRequest(final D domain) {
        throw new UnsupportedOperationException("Operation refused.");
    }
}

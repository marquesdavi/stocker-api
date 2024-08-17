package com.stocker.api.domain.shared;

import java.io.Serializable;

public interface DefaultMapper<D extends Serializable, R extends Serializable, S extends Serializable>{
    default D toDomain(final R request) {
        throw new UnsupportedOperationException("Operation refused.");
    }

    default R toRequest(final D domain) {
        throw new UnsupportedOperationException("Operation refused.");
    }

    default S toResponse(final D domain) {
        throw new UnsupportedOperationException("Operation refused.");
    }
}

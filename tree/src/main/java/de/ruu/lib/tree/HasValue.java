package de.ruu.lib.tree;

import lombok.NonNull;

/** non-nullable value */
public interface HasValue<V> { @NonNull V value(); }
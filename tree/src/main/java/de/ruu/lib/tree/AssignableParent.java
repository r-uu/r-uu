package de.ruu.lib.tree;

import lombok.NonNull;

public interface AssignableParent<V, P extends Node<V>>
{
	/** make sure {@code parent} is not {@code this} */
	@NonNull P parent(@NonNull HasChildren<V, P> parent);
}
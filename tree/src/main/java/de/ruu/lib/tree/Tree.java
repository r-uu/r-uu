package de.ruu.lib.tree;

import lombok.NonNull;

public interface Tree<V, P extends Node<V>>
{
	@NonNull Root<V, P> root();

	abstract class TreeAbstract<V, P extends Node<V>> implements Tree<V, P>
	{
		protected @NonNull Root<V, P> root;

		public TreeAbstract(@NonNull Root<V, P> root) { this.root = root; }
	}

	class TreeSimple<V, P extends Node<V>> extends TreeAbstract<V, P>
	{
		TreeSimple(@NonNull Root<V, P> root) { super(root); }

		@Override
		public @NonNull Root<V, P> root() { return super.root; }
	}

	static <V, P extends Node<V>> @NonNull Tree<V, P> create() { return new TreeSimple<>(Root.create()); }
	static <V, P extends Node<V>> @NonNull Tree<V, P> create(@NonNull Root<V, P> root) { return new TreeSimple<>(root); }
}
package de.ruu.lib.tree;

import de.ruu.lib.tree.Node.NodeAbstract;
import lombok.NonNull;

/** children always have a parent */
public interface HasParent<V, P extends Node<V>>
{
	@NonNull P parent();

	class NodeWithParent<V, P extends Node<V>> extends NodeAbstract<V> implements HasParent<V, P>
	{
		private @NonNull P parent;

		public NodeWithParent(@NonNull P parent) { this.parent = parent; }

		@Override public @NonNull P parent() { return parent; }
	}
}
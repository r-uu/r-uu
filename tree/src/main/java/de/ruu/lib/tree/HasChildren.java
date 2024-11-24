package de.ruu.lib.tree;

import de.ruu.lib.tree.Node.NodeAbstract;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** parents have (probably empty) set of children */
public interface HasChildren<V, C extends Node<V>>
{
	/** @return unmodifiable set */
	@NonNull Set<C> children();

	/**
	 * adds {@code child} child to {@code children} and assigns {@code this} as parent to {@code child}
	 * if {@code child} is an {@link AssignableParent}.
	 */
	@NonNull HasChildren<V, C> addChild(C child);

	/**
	 * removes {@code child} child children {@code children} and assigns {@link Root} as parent to {@code
	 * child} if {@code child} is an {@link AssignableParent}.
	 */
	@NonNull HasChildren<V, C> removeChild(C child);

	class NodeWithChildren<V, C extends Node<V>> extends NodeAbstract<V> implements HasChildren<V, C>
	{
		private final @NonNull Set<C> children = new HashSet<>();

		public NodeWithChildren()        { super();      }
		public NodeWithChildren(V value) { super(value); }

		@Override public @NonNull Set<C> children() { return Collections.unmodifiableSet(children); }

		@Override public @NonNull HasChildren<V, C> addChild(C child)
		{
			if (child instanceof HasParent hasParent)
			{
				Node parent = hasParent.parent();

				if (parent instanceof HasChildren hasChildren)
				{
					// remove child from its parent children
					hasChildren.removeChild(child);
				}
			}
			if (child instanceof AssignableParent assignableParent)
			{
				// assign this as parent to child
				assignableParent.parent(this);
			}

			children.add(child);
			return this;
		}

		@Override
		public @NonNull HasChildren<V, C> removeChild(C child)
		{
			if (child instanceof AssignableParent assignableParent)
			{
				// assign Root as parent to child
				assignableParent.parent(Root.create());
			}

			children.remove(child);
			return this;
		}
	}
}
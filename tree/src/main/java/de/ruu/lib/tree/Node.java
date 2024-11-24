package de.ruu.lib.tree;

import de.ruu.lib.tree.HasChildren.NodeWithChildren;
import jakarta.annotation.Nullable;
import lombok.NonNull;

/** makes no assumption about actual value {@code V} ({@code null} or not-{@code null}) and how to set it */
public interface Node<V>
{
	@Nullable V value();

	abstract class NodeAbstract<V> implements Node<V>
	{
		protected @Nullable V value;

		protected NodeAbstract() { }

		protected NodeAbstract(@Nullable V value) { this.value = value; }

		@Override public @Nullable V value() { return value; }

		public @NonNull Node<V> value(@Nullable V value)
		{
			value = value;
			return this;
		}
	}

	class NodeSimple<V> extends NodeAbstract<V>
	{
		private NodeSimple()                  { super();      }
		private NodeSimple(@Nullable V value) { super(value); }
	}

	class TreeNode<V>
			extends NodeWithChildren<V, Node<V>>
			implements HasParent<V, Node<V>>, HasChildren<V, Node<V>>
	{
		private @NonNull NodeWithChildren<V, Node<V>> parent;

		public TreeNode()                  { super();            }
		public TreeNode(@Nullable V value) { super.value(value); }

		@Override public @NonNull Node<V> parent() { return parent; }
	}

	static <V> @NonNull Node<V> create()                  { return new NodeSimple<>();      }
	static <V> @NonNull Node<V> create(@Nullable V value) { return new NodeSimple<>(value); }

	static <V> @NonNull TreeNode<V> createTreeNode()                  { return new TreeNode<>(); }
	static <V> @NonNull TreeNode<V> createTreeNode(@Nullable V value) { return new TreeNode<>(value); }
}
package de.ruu.lib.tree;

import jakarta.annotation.Nullable;

/** roots never have a parent */
public interface Root<V, P extends Node<V>> extends Node<V>, HasChildren<V, P>
{
	default @Nullable P parent() { return null; }

	abstract class RootAbstract<V, P>
			extends NodeWithChildren<V, Node<V>>
			implements Root<V, Node<V>>
	{
		public RootAbstract()                  { super();      }
		public RootAbstract(@Nullable V value) { super(value); }

		/** roots never have a parent */
		@Override public final @Nullable Node<V> parent() { return null; }
	}

	class RootSimple<V, P extends Node<V>> extends RootAbstract<V, P>
	{
		RootSimple()                  { super();      }
		RootSimple(@Nullable V value) { super(value); }

		@Override public @Nullable V value() { return super.value; }
	}

	public static <V, P extends Node<V>> Root<V, P> create()
	{
		return (Root<V, P>) new RootSimple<>();
	}

	public static <V, P extends Node<V>> Root<V, P> create(@Nullable V value)
	{
		return (Root<V, P>) new RootSimple<>(value);
	}
}
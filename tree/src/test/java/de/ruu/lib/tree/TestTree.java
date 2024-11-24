package de.ruu.lib.tree;

import de.ruu.lib.tree.HasParent.NodeWithParent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

class TestTree
{
	@Test void testRoot()
	{
		String name = "root";
		Root<String, ?> root = Root.create(name);
		assertThat(root, is(notNullValue()));
		assertThat(root.value(), is(name));
	}

	@Test void testTreeWithRoot()
	{
		Root<?, ?> root = Root.create();
		Tree<?, ?> tree = Tree.create(root);
		assertThat(tree, is(notNullValue()));
		assertThat(tree.root(), is(notNullValue()));
		assertThat(tree.root(), is(root));
		assertThat(tree.root().parent(), is(nullValue()));
	}

	@Test void testTreeWithRootAndNode()
	{
		Root          <String, Node<String>> root = Root.create();
		NodeWithParent<String, Node<String>> node = new NodeWithParent<>(root);
		Tree<?, ?> tree = Tree.create(root);

		root.addChild(node);

		assertThat(tree.root().children().contains(node), is(true));
		assertThat(tree.root().children().size(), is(1));
		assertThat(node.parent(), is(root));
	}
}
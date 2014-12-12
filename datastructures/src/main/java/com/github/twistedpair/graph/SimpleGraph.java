package com.github.twistedpair.graph;

import java.util.LinkedList;
import java.util.List;



final class SimpleGraph implements Graph {

	private final List<Integer>[] adj; // TODO sets t
	int E;
	int V;

	public SimpleGraph(final int vertexes) {
		adj = new LinkedList[vertexes];
		for (int v = 0; v < adj.length; v++) {
			adj[v] = new LinkedList<>();
		}
	}

	@Override
	public int vCount() {
		return V;
	}

	@Override
	public int eCount() {
		return E;
	}

	@Override
	public void addEdge(final int v, final int w) {
		adj[v].add(w);
		adj[w].add(v);
		V++;
	}

	@Override
	public Iterable<Integer> adj(final int vIdx) {
		if (vIdx < V) { return adj[vIdx];
		}
		throw new ArrayIndexOutOfBoundsException();
	}

}
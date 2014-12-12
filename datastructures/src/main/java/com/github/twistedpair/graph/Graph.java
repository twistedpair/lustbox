package com.github.twistedpair.graph;



interface Graph {

	int vCount();
	int eCount();

	/**
	 * Add edge between vertexes
	 * @param v index
	 * @param w index
	 */
	void addEdge(final int v, final int w);

	/**
	 * Edges of a given vertex
	 * @param v vertex number
	 * @return
	 */
	Iterable<Integer> adj(final int v);
}
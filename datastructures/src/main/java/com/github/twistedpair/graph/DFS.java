package com.github.twistedpair.graph;

import java.util.Stack;




final class DFS {

	private final boolean[] marked; // has this vertex been visited
	private final int[] edgeTo; // last vertex on known path to this vertex
	private final int s; // source

	/**
	 * @param g
	 * @param s
	 */
	public DFS(final Graph g, final int s) {
		marked = new boolean[g.vCount()];
		edgeTo = new int[g.vCount()];
		this.s = s;

		dfs(g, s);
	}

	/**
	 * Find all points reachable from this source
	 * 
	 * @param g
	 * @param v
	 *            source
	 */
	private void dfs(final Graph g, final int v) {

		marked[v] = true; // mark visited
		// visit children
		for (final int w : g.adj(v)) {
			if (!marked[w]) { // been here?
				edgeTo[w] = v;
				dfs(g, w); // will stop when all marked or none adj
			}
		}
	}

	/**
	 * Is vertex reachable from source?
	 * 
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(final int v) {
		return marked[v];
	}

	public Iterable<Integer> pathTo(final int v) {
		if (!hasPathTo(v)) { return null; } // TODO return empty
		final Stack<Integer> path = new Stack<>();
		for (int x = v; x != s; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(s);
		return path;
	}
}
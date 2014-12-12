package com.github.twistedpair.graph;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;




final class BFS {

	private final boolean[] marked; // has this vertex been visited
	private final int[] edgeTo; // last vertex on known path to this vertex
	private final int s; // source

	/**
	 * @param g
	 * @param s
	 */
	public BFS(final Graph g, final int s) {
		marked = new boolean[g.vCount()];
		edgeTo = new int[g.vCount()];
		this.s = s;

		bfs(g, s);
	}

	/**
	 * Find all points reachable from this source
	 * 
	 * @param g
	 * @param s
	 *            source
	 */
	private void bfs(final Graph g, final int s) {

		final Queue<Integer> queue = new ArrayDeque<>();
		marked[s] = true; // mark visited
		queue.offer(s);

		// continue until cleared of points to visit
		while (!queue.isEmpty()) {
			final int v = queue.poll();
			for (final int w : g.adj(v)) { // visit all children
				if (!marked[w]) {
					marked[w] = true;
					edgeTo[w] = v;
					queue.offer(w);
				}
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
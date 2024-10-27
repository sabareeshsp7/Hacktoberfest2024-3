// Boruvka's algorithm to find Minimum Spanning
// Tree of a given connected, undirected and weighted graph
import java.util.*;

// Class to represent a graph
class Graph {
private int V; // No. of vertices
private List<List<Integer> >
	graph; // default dictionary to store graph

Graph(int vertices)
{
	V = vertices;
	graph = new ArrayList<>();
}

// function to add an edge to graph
void addEdge(int u, int v, int w)
{
	graph.add(Arrays.asList(u, v, w));
}

// A utility function to find set of an element i
// (uses path compression technique)
private int find(List<Integer> parent, int i)
{
	if (parent.get(i) == i) {
	return i;
	}
	return find(parent, parent.get(i));
}

// A function that does union of two sets of x and y
// (uses union by rank)
private void unionSet(List<Integer> parent,
						List<Integer> rank, int x, int y)
{
	int xroot = find(parent, x);
	int yroot = find(parent, y);

	// Attach smaller rank tree under root of high rank
	// tree (Union by Rank)
	if (rank.get(xroot) < rank.get(yroot)) {
	parent.set(xroot, yroot);
	}
	else if (rank.get(xroot) > rank.get(yroot)) {
	parent.set(yroot, xroot);
	}

	// If ranks are same, then make one as root and
	// increment its rank by one
	else {
	parent.set(yroot, xroot);
	rank.set(xroot, rank.get(xroot) + 1);
	}
}

// The main function to construct MST using Kruskal's
// algorithm
void boruvkaMST()
{
	List<Integer> parent = new ArrayList<>();

	// An array to store index of the cheapest edge of
	// subset. It store [u,v,w] for each component
	List<Integer> rank = new ArrayList<>();

	List<List<Integer> > cheapest = new ArrayList<>();

	// Initially there are V different trees.
	// Finally there will be one tree that will be MST
	int numTrees = V;
	int MSTweight = 0;

	// Create V subsets with single elements
	for (int node = 0; node < V; node++) {
	parent.add(node);
	rank.add(0);
	cheapest.add(Arrays.asList(-1, -1, -1));
	}

	// Keep combining components (or sets) until all
	// components are not combined into single MST
	while (numTrees > 1) {

	// Traverse through all edges and update
	// cheapest of every component
	for (List<Integer> edge : graph) {

		// Find components (or sets) of two corners
		// of current edge
		int u = edge.get(0), v = edge.get(1),
		w = edge.get(2);
		int set1 = find(parent, u),
		set2 = find(parent, v);

		// If two corners of current edge belong to
		// same set, ignore current edge. Else check
		// if current edge is closer to previous
		// cheapest edges of set1 and set2
		if (set1 != set2) {
		if (cheapest.get(set1).get(2) == -1
			|| cheapest.get(set1).get(2) > w) {
			cheapest.set(
			set1, Arrays.asList(u, v, w));
		}
		if (cheapest.get(set2).get(2) == -1
			|| cheapest.get(set2).get(2) > w) {
			cheapest.set(
			set2, Arrays.asList(u, v, w));
		}
		}
	}

	// Consider the above picked cheapest edges and
	// add them to MST
	for (int node = 0; node < V; node++) {

		// Check if cheapest for current set exists
		if (cheapest.get(node).get(2) != -1) {
		int u = cheapest.get(node).get(0),
		v = cheapest.get(node).get(1),
		w = cheapest.get(node).get(2);
		int set1 = find(parent, u),
		set2 = find(parent, v);
		if (set1 != set2) {
			MSTweight += w;
			unionSet(parent, rank, set1, set2);
			System.out.printf(
			"Edge %d-%d with weight %d included in MST\n",
			u, v, w);
			numTrees--;
		}
		}
	}
	for (List<Integer> list : cheapest) {
		// reset cheapest array
		list.set(2, -1);
	}
	}
	System.out.printf("Weight of MST is %d\n",
					MSTweight);
}
}

class GFG {
public static void main(String[] args)
{
	Graph g = new Graph(4);
	g.addEdge(0, 1, 10);
	g.addEdge(0, 2, 6);
	g.addEdge(0, 3, 5);
	g.addEdge(1, 3, 15);
	g.addEdge(2, 3, 4);
	g.boruvkaMST();
}
}
// This code is contributed by prasad264

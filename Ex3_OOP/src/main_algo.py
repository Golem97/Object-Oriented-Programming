from GraphAlgo import GraphAlgo
from DiGraph import DiGraph
if __name__ == '__main__':
    # algo = GraphAlgo()
    #
    # print(algo.load_from_json("../data/T0.json"))
    #
    # graph = algo.get_graph()
    #
    # print("***********get edge/vertex**************")
    # vertexs = graph.get_all_v()
    # for x in vertexs:
    #     print("key: ",x,"values: ",vertexs[x])
    #     for y in graph.all_out_edges_of_node(x):
    #         print("key: ", y, "values: ", graph.all_out_edges_of_node(x)[y])
    #
    # print("***********finish**************")

    graph = DiGraph()
    p=(1,2,3)
    print("***********add node**************")
    print("add node:", graph.add_node(0, p))
    print("add node:", graph.add_node(0, p))
    print("add node:", graph.add_node(1))
    print("mc:", graph.get_mc())
    print("vsize:", graph.v_size())
    print("esize:", graph.e_size())
    print("***********finish**************")
    print("                                ")
    print("***********add edge**************")
    print("add edge:", graph.add_edge(0, 2, 500))
    print("add edge:", graph.add_edge(0, 1, 500))
    print("add edge:", graph.add_edge(0, 1, 300))
    print("add edge:", graph.add_edge(1, 0, 300))
    print("mc:", graph.get_mc())
    print("vsize:", graph.v_size())
    print("esize:", graph.e_size())
    print("***********finish**************")

    algo = GraphAlgo(graph)

    print(algo.save_to_json("../data/Test.json"))

    print(algo.load_from_json("../data/Test.json"))

    graph1 = algo.get_graph()
    print("mc:", graph1.get_mc())
    print("vsize:", graph1.v_size())
    print("esize:", graph1.e_size())
    print(graph1)


    graph_shrtest_path = DiGraph()
    print("add node:", graph_shrtest_path.add_node(1, p))
    print("add node:", graph_shrtest_path.add_node(2, p))
    print("add node:", graph_shrtest_path.add_node(3, p))
    print("add node:", graph_shrtest_path.add_node(4, p))
    print("add edge:", graph_shrtest_path.add_edge(1, 2, 2))
    print("add edge:", graph_shrtest_path.add_edge(2, 3, 3))
    print("add edge:", graph_shrtest_path.add_edge(3, 4, 4))
    print("add edge:", graph_shrtest_path.add_edge(4, 2, 9))



    algo1=GraphAlgo(graph_shrtest_path)



    print(algo1.shortest_path(1,4))
    print(algo1.connected_component(4))
    print(algo1.connected_components())


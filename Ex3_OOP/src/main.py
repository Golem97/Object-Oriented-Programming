from DiGraph import DiGraph
if __name__ == '__main__':
    p=(5,4)
    # node=Node_data(0,p)
    # node.weight=500
    # print(node.getKey())
    # print(node)
    # edge=Edge_data(0,1,500)
    # print(edge)
    graph=DiGraph()
    print("***********add node**************")
    print("add node:",graph.add_node(0,p))
    print("add node:",graph.add_node(0,p))
    print("add node:", graph.add_node(1, p))
    print("mc:", graph.get_mc())
    print("vsize:",graph.v_size())
    print("esize:",graph.e_size())
    print("***********finish**************")
    print("                                ")
    print("***********add edge**************")
    print("add edge:",graph.add_edge(0,2,500))
    print("add edge:",graph.add_edge(0,1,500))
    print("add edge:", graph.add_edge(0, 1, 300))
    print("add edge:", graph.add_edge(1, 0, 300))
    print("mc:", graph.get_mc())
    print("vsize:",graph.v_size())
    print("esize:",graph.e_size())
    print("***********finish**************")

    print("                                ")
    print("***********get edge/vertex**************")
    vertexs = graph.get_all_v()
    for x in vertexs:
        print("key: ",x,"values: ",vertexs[x])

    insid=graph.all_in_edges_of_node(0)
    out=graph.all_out_edges_of_node(0)
    for x in insid:
        print("key: ",x,"values: ",insid[x])
    for x in out:
        print("key: ",x,"values: ",out[x])
    print("***********finish**************")
    print("                                ")
    print("***********add edge**************")
    print("remove edge:",graph.remove_edge(0,2))
    print("remove edge:",graph.remove_edge(0,1))
    print("remove edge:", graph.remove_edge(0, 1))
    print("mc:", graph.get_mc())
    print("vsize:",graph.v_size())
    print("esize:",graph.e_size())
    print("***********finish**************")

    print("                                ")
    print("***********add edge**************")
    print("add edge:", graph.add_edge(0, 1, 500)) # 6
    print("remove node:",graph.remove_node(2))
    print("remove node:",graph.remove_node(1))
    print("remove node:", graph.remove_node(1))
    print("mc:", graph.get_mc()) # 9
    print("vsize:",graph.v_size()) #1
    print("esize:",graph.e_size()) #0
    print("***********finish**************")

    print("                                ")
    print("***********get edge/vertex**************")
    vertexs = graph.get_all_v()
    for x in vertexs:
        print("key: ",x,"values: ",vertexs[x])

    insid=graph.all_in_edges_of_node(0)
    out=graph.all_out_edges_of_node(0)
    for x in insid:
        print("key: ",x,"values: ",insid[x])
    for x in out:
        print("key: ",x,"values: ",out[x])
    print("***********finish**************")
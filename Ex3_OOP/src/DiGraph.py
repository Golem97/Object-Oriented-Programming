from src.GraphInterface import GraphInteface
from typing import Dict
from src.NodeData import Node_data
from src.EdgeData import Edge_data


# TODO: IMPLEMENT
class DiGraph(GraphInteface):

    def __init__(self):  # initialize self
        self.__V: Dict[int, Node_data] = dict()
        self.__N_out: Dict[int, Dict[int, Edge_data]] = dict()
        self.__N_in: Dict[int, Dict[int, Edge_data]] = dict()
        self.__edgeSize = 0
        self.__mc = 0

    # Returns the number of vertices in this graph
    def v_size(self) -> int:
        return len(self.__V)

    # Returns the number of edges in this graph
    def e_size(self) -> int:
        return self.__edgeSize

    # Returns the current version of this graph (MC is incremented for every change)
    def get_mc(self) -> int:
        return self.__mc

    # Adds an edge to the graph
    def add_edge(self, id1: int, id2: int, weight: float) -> bool:
        src = id1
        dest = id2
        if src is not dest and src in self.__V and dest in self.__V and dest not in self.__N_out.get(src):
            edge = Edge_data(src, dest, weight)
            self.__N_out.get(src).update({dest: edge})
            self.__N_in.get(dest).update({src: edge})
            self.__mc += 1
            self.__edgeSize += 1
            return True
        else:
            return False

    # Adds a node to the graph
    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        if node_id not in self.__V:
            node = Node_data(node_id, pos)
            self.__V.update({node_id: node})
            self.__N_out.update({node_id: dict()})
            self.__N_in.update({node_id: dict()})
            self.__mc += 1
            return True
        else:
            return False

    def remove_node(self, node_id: int) -> bool:
        if node_id in self.__V:
            len_in = len(self.__N_in.get(node_id))
            len_out = len(self.__N_out.get(node_id))
            self.__mc += len_in + len_out
            self.__edgeSize -= len_in + len_out
            for x in self.__N_out.get(node_id):
                self.__N_out.get(x).pop(node_id)
            for x in self.__N_in.get(node_id):
                self.__N_in.get(x).pop(node_id)
            self.__N_in.pop(node_id)
            self.__N_out.pop(node_id)
            self.__mc += 1
            self.__V.pop(node_id)
            return True
        else:
            return False

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        src = node_id1
        dest = node_id2
        if src is not dest and src in self.__V and dest in self.__V and dest in self.__N_out.get(src):
            self.__N_out.get(src).pop(dest)
            self.__N_in.get(dest).pop(src)
            self.__mc += 1
            self.__edgeSize -= 1
            return True
        else:
            return False

    def get_all_v(self) -> dict:
        return self.__V

    def all_in_edges_of_node(self, id1: int) -> dict:
        return self.__N_in.get(id1)

    def all_out_edges_of_node(self, id1: int) -> dict:
        return self.__N_out.get(id1)

    def get_node(self, node_id: int) -> Node_data:
        return self.__V.get(node_id)

    def __repr__(self):
        return "Graph: |V|=%s , |E|=%s" % (
            self.v_size(), self.e_size())

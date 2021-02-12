from typing import List
from src.DiGraph import DiGraph
from src.GraphAlgoInterface import GraphAlgoInterface
from src import GraphInterface
import json
import math
import random
import matplotlib.pyplot as matplot
from queue import PriorityQueue
from src.NodeData import Node_data
from typing import Dict
# global variable's
ids = {}
list_path = []
lists_path_path = []


# TODO: IMPLEMENT add comments
class GraphAlgo(GraphAlgoInterface):

    def __init__(self, other=DiGraph()):
        self.graph = other

    def get_graph(self) -> GraphInterface:
        return self.graph

    def load_from_json(self, file_name: str) -> bool:

        try:
            with open(file_name, 'r') as filep:
                graphfromjson = json.load(filep)
            edges = graphfromjson.get('Edges')
            vertexs = graphfromjson.get('Nodes')
            self.graph = DiGraph()
            for x in vertexs:
                pos = x.get('pos')
                if pos is None:
                    self.graph.add_node(x.get('id'))
                else:
                    posi = tuple(map(float, x.get('pos').split(",")))
                    self.graph.add_node(x.get('id'), posi)
            for x in edges:
                self.graph.add_edge(x.get('src'), x.get('dest'), x.get('w'))

        except FileExistsError:
            return False
        return True

    def save_to_json(self, file_name: str) -> bool:

        Node = []
        for n in self.graph.get_all_v().values():
            if n.getpos() is None:
                Node.append({'id': n.getKey()})
            else:
                posi = str(n.getpos()[0]) + "," + str(n.getpos()[1]) + "," + str(n.getpos()[2])
                Node.append({'pos': posi, 'id': n.getKey()})

        Edge = []
        for key in self.graph.get_all_v().keys():
            for edges in self.graph.all_out_edges_of_node(key).values():
                Edge.append({"src": edges.getsrc(), "w": edges.getweight(), "dest": edges.getdest()})

        answer = {'Edges': Edge, 'Nodes': Node}
        try:
            with open(file_name, 'w') as file:
                json.dump(answer, file)
            return True
        except FileNotFoundError:
            return False

    def shortest_path(self, id1: int, id2: int) -> (float, list):
        if id1 not in self.graph.get_all_v() or id2 not in self.graph.get_all_v():
            return math.inf, []

        self.__Dijkstra(id1)

        dest = self.graph.get_all_v().get(id2)
        if dest.weight is math.inf:
            return math.inf, []

        path_dikstra = []
        path_dikstra.insert(0, id2)

        parent = dest.info
        while parent != "":
            node = self.graph.get_all_v().get(parent)
            path_dikstra.insert(0, node.getKey())
            parent = node.info

        return dest.weight, path_dikstra

    # help function for shortest path dijkstra
    def __Dijkstra(self, src: int):
        # init
        for node in self.graph.get_all_v().values():
            node.tag = 0
            node.info = ""
            node.weight = math.inf

        # start dikstra
        q = PriorityQueue()
        node = self.graph.get_all_v().get(src)
        node.weight = 0
        q.put(node)
        # update parents and weight per node
        while not q.empty():
            node1 = q.get()
            for edge_out in self.graph.all_out_edges_of_node(node1.getKey()).values():
                node2 = self.graph.get_all_v().get(edge_out.getdest())
                weight_src_edge = node1.weight + edge_out.getweight()
                if weight_src_edge < node2.weight:
                    node2.weight = weight_src_edge
                    node2.info = node1.getKey()
                    q.put(node2)

    # tarjan O(|v| + |E|)
    def connected_component(self, id1: int) -> list:

        if self.graph is None or id1 not in self.graph.get_all_v():
            return []

        self.init_varibales()
        global ids,list_path,lists_path_path
        ids = {}
        list_path = []
        lists_path_path = []

        for x in self.graph.get_all_v().keys():
            ids.update({x:0})

        self.__Tarjan(id1)

        return list_path


    def init_varibales(self):
        # init
        for node in self.graph.get_all_v().values():
            node.tag = 0
            node.info = ""
            node.weight = math.inf

    def connected_components(self) -> List[list]:

        if self.graph is None :
            return []

        self.init_varibales()
        global ids,list_path,lists_path_path
        ids = {}
        list_path = []
        lists_path_path = []

        for x in self.graph.get_all_v().keys():
            ids.update({x:0})
        for node in self.graph.get_all_v().keys():
            if ids.get(node)==0:
                self.__Tarjan(node)
        return lists_path_path

    # # Recursive Tarjan - Causing Recursion Depth issue
    # def __Tarjan(self, at: int):
    #     global s, onStack, id, list_path, lists_path_path
    #     node = self.graph.get_all_v().get(at)
    #     s.append(node)
    #     id += 1
    #     # ids = tag low = weight
    #     node.tag = id
    #     node.weight = id
    #     onStack.update({at: True})
    #
    #     for to in self.graph.all_out_edges_of_node(at):
    #         node1 = self.graph.get_all_v().get(to)
    #         if node1.tag == 0: self.__Tarjan(node1.getKey())
    #         if onStack.get(to) is True: node.weight = min(node.weight, node1.weight)
    #
    #     if node.tag == node.weight:
    #         list_path = []
    #         while s:
    #             node_pop = s.pop()
    #             list_path.insert(0, node_pop.getKey())
    #             onStack.update({node_pop.getKey(): False})
    #             node_pop.weight = node.tag
    #             if node_pop.getKey() is node.getKey(): break
    #         lists_path_path.insert(0, list_path)


    def __Tarjan(self, v: int):
        global ids, list_path, lists_path_path

        id=0
        s=[]
        recu = [(v,0)]
        while recu:
            v,i=recu[-1]
            del recu[-1]
            # onStack = info low = weight
            node: Node_data = self.graph.get_all_v().get(v)
            if i==0:
                s.append(v)
                id += 1
                ids.update({v: id})
                node.weight = id
                node.info="1"
            flag = False
            r=0
            for x in self.graph.all_out_edges_of_node(v):
                to:Node_data = self.graph.get_all_v().get(x)
                if ids.get(x) == 0:
                    recu.append((v,r+1))
                    recu.append((x,0))
                    flag=True
                    break
                elif to.info == "1":
                    node.weight=min(node.weight,to.weight)
            if flag == True: continue

            if node.weight == ids.get(v):
                list_path=[]
                while s:
                    node_pop = s.pop()
                    list_path.insert(0, node_pop)
                    node_data_pop:Node_data=self.graph.get_all_v().get(node_pop)
                    node_data_pop.info=""
                    node_data_pop.weight = ids.get(v)
                    if node_pop is v: break
                lists_path_path.insert(0,list_path)
            if recu:
                x=v
                v,_=recu[-1]
                to:Node_data = self.graph.get_all_v().get(x)
                node.weight = min(node.weight, to.weight)


    def plot_graph(self) -> None:
        graph = self.get_graph()

        for node in graph.get_all_v().values():
            node:Node_data=node
            if node.getpos() is None:
                node.setpos((random.uniform(35.18, 35.2), random.uniform(32.1, 32.2)))
            matplot.plot(node.getpos()[0], node.getpos()[1], 'or', markersize=8)
            matplot.text(node.getpos()[0], node.getpos()[1], str(node.getKey()))


        for id in graph.get_all_v().keys():
            for k, w in graph.all_out_edges_of_node(id).items():
                x1 = graph.get_node(id).getpos()[0]
                y1 = graph.get_node(id).getpos()[1]
                x2 = graph.get_node(k).getpos()[0]
                y2 = graph.get_node(k).getpos()[1]

                matplot.arrow(x1, y1, (x2 - x1), (y2 - y1), length_includes_head=True, width=0.00003,
                              head_width=0.00025)
        matplot.show()

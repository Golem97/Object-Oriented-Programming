import unittest
from src.NodeData import Node_data
from src.DiGraph import DiGraph


class Test(unittest.TestCase):

    def test_add_node(self):
        p = (5, 4)
        self.graph = DiGraph()

        bool = self.graph.add_node(0, p)
        self.assertTrue(bool)

        bool = self.graph.add_node(0, p)
        self.assertFalse(bool)

        bool = self.graph.add_node(1, p)
        self.assertTrue(bool)

        version = self.graph.get_mc()
        self.assertEqual(version, 2)

        vsize = self.graph.v_size()
        self.assertEqual(vsize, 2)

        esize = self.graph.e_size()
        self.assertEqual(esize, 0)

    def test_add_edge(self):
        self.graph = DiGraph()
        p = (5, 4)
        self.graph.add_node(0, p)
        self.graph.add_node(1, p)

        bool = self.graph.add_edge(0, 2, 500)
        self.assertFalse(bool)

        bool = self.graph.add_edge(0, 1, 500)
        self.assertTrue(bool)

        bool = self.graph.add_edge(0, 1, 500)
        self.assertFalse(bool)

        bool = self.graph.add_edge(1, 0, 500)
        self.assertTrue(bool)

        version = self.graph.get_mc()
        self.assertEqual(version, 4)

        vsize = self.graph.v_size()
        self.assertEqual(vsize, 2)

        esize = self.graph.e_size()
        self.assertEqual(esize, 2)

    def test_remove_edge(self):
        self.graph = DiGraph()
        p = (5, 4)
        self.graph.add_node(0, p)
        self.graph.add_node(1, p)
        self.graph.add_edge(0, 1, 500)
        self.graph.add_edge(1, 0, 500)

        bool = self.graph.remove_edge(0, 2)
        self.assertFalse(bool)

        bool = self.graph.remove_edge(0, 1)
        self.assertTrue(bool)

        bool = self.graph.remove_edge(0, 1)
        self.assertFalse(bool)

        version = self.graph.get_mc()
        self.assertEqual(version, 5)

        vsize = self.graph.v_size()
        self.assertEqual(vsize, 2)

        esize = self.graph.e_size()
        self.assertEqual(esize, 1)

    def test_remove_node(self):
        self.graph = DiGraph()
        p = (5, 4)
        self.graph.add_node(0, p)
        self.graph.add_node(1, p)
        self.graph.add_edge(0, 1, 500)
        self.graph.add_edge(1, 0, 500)
        self.graph.remove_node(1)

        version = self.graph.get_mc()
        self.assertEqual(version, 7)

        vsize = self.graph.v_size()
        self.assertEqual(vsize, 1)

        esize = self.graph.e_size()
        self.assertEqual(esize, 0)


if __name__ == '__main__':
    unittest.main()
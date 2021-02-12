import math


class Node_data:

    def __init__(self, key: int, pos: tuple):
        self.__key = key
        self.__pos = pos
        self.tag = 0
        self.info = ""
        self.weight = 0

    def getKey(self) -> int:
        return self.__key

    def getpos(self) -> tuple:
        return self.__pos

    def setpos(self,pos:tuple):
        self.__pos=pos

    # comparator for Priority Queue in Djikstra
    def __lt__(self, other):
        selfPriority = (self.weight, self.__key)
        otherPriority = (other.weight, other.getKey())
        return selfPriority < otherPriority

    def __repr__(self):
        return "(id: %s)" % (self.__key)

import math

def addDict(dict1, dict2):
    newDict = {}
    for i in dict1:
        newDict[i] = dict1[i]
    for i in dict2: 
        newDict[i] = dict2[i]
    return newDict

def copyDict(d):
    newDict = {}
    for i in d:
        newDict[i] = d[i]
    return newDict

def sqrList(l):
    myList = []
    i = 0
    for i in range(len(l)):
        myList.append(math.sqrt(l[i]))
    newList = []
    newList = [math.sqrt(x) for x in l]
    print(newList)

    newList2 = list(map(lambda x: math.sqrt(x), l))
    print(newList2)
    return myList
        

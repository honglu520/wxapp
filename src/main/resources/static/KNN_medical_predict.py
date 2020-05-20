#-- coding:UTF-8 --
import csv  # 用于处理csv文件
import random  # 用于随机数
import math
import operator  #
from sklearn import neighbors
import sys	 
from ast import literal_eval

# 加载数据集
def loadDataset(filename, trainingSet=[]):
    with open(filename, "rt",encoding="UTF-8") as csvfile:#打开CSV文件 
        lines = csv.reader(csvfile)
        dataset = list(lines)
  
        for x in range(len(dataset)):
            for y in range(9):                        #range参数为几个属性
                dataset[x][y] = float(dataset[x][y])
 
            trainingSet.append(dataset[x])



def euclideanDistance(instance1, instance2, length):           #计算实例距离
    distance = 0
    for x in range(length):
        distance += pow((instance1[x] - instance2[x]), 2)
    return math.sqrt(distance)


# 返回K个最近邻实例
def getNeighbors(trainingSet, testInstance, k):
    distances = []
    length = len(testInstance) - 1
    # 计算每一个测试实例到训练集实例的距离
    for x in range(len(trainingSet)):
        dist = euclideanDistance(testInstance, trainingSet[x], length)
        distances.append((trainingSet[x], dist))
        # 对所有的距离进行排序
    distances.sort(key=operator.itemgetter(1))
    neighbors = []
    # 返回k个最近邻
    for x in range(k):
        neighbors.append(distances[x][0])
    return neighbors


# 对k个近邻进行合并，返回value最大的key
def getResponse(neighbors):
    classVotes = {}
    for x in range(len(neighbors)):
        response = neighbors[x][-1]
        if response in classVotes:
            classVotes[response] += 1
        else:
            classVotes[response] = 1
            # 排序
    sortedVotes = sorted(classVotes.items(), key=operator.itemgetter(1), reverse=True)
    return sortedVotes[0][0]



# def main():
#     trainingSet = []  # 训练数据集
#     loadDataset(r"data/data2.txt",  trainingSet)
#     predictions = []
#     k = 2
#     #testSet=[36.5,0.94,5.0,12.1,120,80,300,1.5,7,'脑血管']
#     neighbors = getNeighbors(trainingSet, testSet, k)
#     result = getResponse(neighbors)
#     predictions.append(result)
#     print(        ">predicted = " + repr(result) + ",actual = " + repr(testSet[-1]))




if __name__ == "__main__":
    #main()
    trainingSet = []  # 病例样本数据集
    loadDataset(sys.argv[2], trainingSet)
    predictions = []
    k = 2              #k邻近K取值
    #testSet="[36.5,0.94,5.0,12.1,120,80,300,1.5,7]"
    testSet=sys.argv[1]    #接受病人指标参数例如testSet=[36.5,0.94,5.0,12.1,120,80,300,1.5,7]
    #print(type(testSet))
    mlist = literal_eval(testSet)
    #print(type(mlist))
    neighbors = getNeighbors(trainingSet, mlist, k)         #返回K个最近邻实例
    result = getResponse(neighbors)          #返回预测病类型
    #predictions.append(result)
    #print(">predicted = " + repr(result) + ",actual = " + repr(testSet[-1]))
    print(result)                             #输出病类型


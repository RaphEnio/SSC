from matplotlib import pyplot as plt
import pathlib
import numpy as np

def calculate_prob(v, numberAccesses):
    result = np.zeros(v.shape[0])
    for i in range(v.shape[0]):
        result[i] = (v[i]/numberAccesses)
    return result

def getBiggerStash(stashes):
    result = np.zeros(len(stashes))
    count = 0
    for stash in stashes:
        tmp = 0
        for stashes2 in stashes:
            if stashes2 > stash:
                tmp += 1
        result[count] = tmp
        count += 1
    return result

# get path of the working directory
wpath = pathlib.Path(__file__).parent.absolute()
print(wpath)
filepath = str(wpath) + "\pathORAM\log.txt"

stash = {}
with open(filepath, "r") as f:
    for line in f:
        stashSize = line.split(",")[0]
        ac = int(line.split(",")[1])
        if (int(stashSize) == -1):
            continue
        if stashSize not in stash:
            stash.update({stashSize: ac})
        else:
            stash[stashSize] += ac

stashvalues = list(stash.values())
biggerStashes =  np.array(getBiggerStash(stashvalues))
probabilities = calculate_prob(biggerStashes, sum(stashvalues))
x = list(stash.keys())

fig = plt.figure(figsize=(30,8))
plt.title("Probability that the stash size is larger")
plt.xlabel("stash size")
plt.ylabel("probabilty")
plt.bar(x,np.array(probabilities))
plt.xticks(x)
plt.xticks(rotation = 90)
plt.savefig("graph.png")
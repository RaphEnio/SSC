from matplotlib import pyplot as plt
import pathlib
import numpy as np

def calculate_prob(data, numAccess):
    numStashes = data.shape[0]
    result = np.zeros(numStashes)
    for i in range(numStashes):
        result[i] = (data[i]/numAccess)
    return result

def getBiggerStash(stashes):
    keys = list(stashes.keys())
    result = np.zeros(len(keys))
    count = 0
    for i in range(0, len(keys)):
        tmp = 0
        for j in range(0, len(keys)):
            if int(keys[j]) > int(keys[i]):
                tmp += stashes.get(keys[j])
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
        if (int(stashSize) == -1 or int(stashSize) == 0):
            continue
        if stashSize not in stash:
            stash.update({stashSize: ac})
        else:
            stash[stashSize] += ac

biggerStashes =  np.array(getBiggerStash(stash))
probabilities = calculate_prob(biggerStashes, sum(stash.values()))
x = list(stash.keys())

fig = plt.figure(figsize=(30,8))
plt.title("Probability that the stash size is larger")
plt.xlabel("stash size")
plt.ylabel("probabilty")
#plt.plot(x,np.array(probabilities)) # line plot
plt.bar(x,np.array(probabilities)) # bar plot
plt.xticks(x)
plt.xticks(rotation = 90)
plt.grid(axis="y")
plt.savefig("graph.png")
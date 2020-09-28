from matplotlib import pyplot as plt
import pathlib
import numpy as np

# counts the number of stash accesses of larger stasher per stash
def getBiggerStash(stashes):
    keys = list(stashes.keys())
    result = np.zeros(len(keys))
    for i in range(0, len(keys)):
        tmp = 0
        for j in range(0, len(keys)):
            if int(keys[j]) > int(keys[i]):
                tmp += stashes.get(keys[j])
        result[i] = tmp
    return result

# get path of the working directory
wpath = pathlib.Path(__file__).parent.absolute()
print(wpath)
filepath = str(wpath) + "\pathORAM\log.txt"

# read the logfile and save it in a dictonary, where the stash size is the key
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

# call function
biggerStashes =  np.array(getBiggerStash(stash))
# transform stash accesses to probabilities
probabilities = biggerStashes/sum(stash.values())

# plot the data
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
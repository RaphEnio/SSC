###############################################################
#                                                             #
#                        TASK 3                               # 
#                                                             #                          
###############################################################

# Note: For binary strings a and b the Hamming 
# distance is equal to the number of ones (population count) in a XOR b

# So to calculate hamming distance, calculate XOR. Only problem, XOR produces a lot of noise
import support_functions as sf


data = sf.read_json(r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task3_v2.json")

#get needed variables
pk = list(map(int, data["SWHE"]["Public Parameters"]["pk"])) 

v1 = data["Ciphertext Collection"]["Encrypted V1"]
v2 = data["Ciphertext Collection"]["Encrypted V2"]

b1_0 = bin(int(v1[0]))
b2_0 = bin(int(v2[0]))

hamming_0 = bin(int(b1_0,2) ^ int(b2_0, 2))
print(hamming_0.count('1'))
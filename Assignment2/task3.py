###############################################################
#                                                             #
#                        TASK 3                               # 
#                                                             #                          
###############################################################

# Note: For binary strings a and b the Hamming 
# distance is equal to the number of ones (population count) in a XOR b (wiki)

# So to calculate hamming distance, calculate XOR. Only problem, XOR produces a lot of noise
import support_functions as sf

path = r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task3_v2_test.json"
data = sf.read_json(path)

#get needed variables
pk = list(map(int, data["SWHE"]["Public Parameters"]["pk"]))
rho = int(data["SWHE"]["Public Parameters"]["rho"])
tau = int(data["SWHE"]["Public Parameters"]["tau"]) 

v1 = data["Ciphertext Collection"]["Encrypted V1"]
v2 = data["Ciphertext Collection"]["Encrypted V2"]


#calculate the hamming distance between the individual ciphertexts of each collection
final_distance = 0
for i in range(0, len(v1)):
    b1_i = bin(int(v1[i]))
    b2_i = bin(int(v2[i]))
    # add the distance to the total distance
    # Use XOR and then count the number of ones
    final_distance += bin(int(b1_i, 2) ^ int(b2_i, 2)).count('1')

print("Hamming distance of ciphertexts: {}".format(final_distance))
print("Hamming distance of ciphertexts (binary): {}".format(sf.decimalToBinary(final_distance)))


# perform bitwise enryption of the hamming distance
bin_hd = list(sf.decimalToBinary(final_distance))
ciphertext_vector = []
for bit in bin_hd:
    cipher_bit = sf.encrypt_message(int(bit), rho, tau, pk)
    ciphertext_vector.append(cipher_bit)

# write to json file
entry = {"Encrypted Hamming Distance" : ciphertext_vector}
sf.write_json(entry, path)

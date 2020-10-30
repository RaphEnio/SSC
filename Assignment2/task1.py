###############################################################
#                                                             #
#                        TASK 1                               # 
#                                                             #                          
###############################################################

import support_functions as sf

data = sf.read_json(r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task1.json")

#get needed variables
#convert list of binary values into an integer
plaintext = data["Plaintext Vector"]
eta = int(data["SWHE"]["Public Parameters"]["eta"])
gamma  = int(data["SWHE"]["Public Parameters"]["gamma"])
rho = int(data["SWHE"]["Public Parameters"]["rho"])

# perform bitwise enryption of the plaintext vector
ciphertext_vector = []
# set to true for binary representation of the ciphertext
binary_repre = False
for bit in plaintext:
    cipher_bit = sf.encrypt_message(int(bit), eta, gamma, rho)
    if binary_repre == True:
        cipher_bit = sf.decimalToBinary(cipher_bit)
    ciphertext_vector.append(cipher_bit)

print(ciphertext_vector)

#need to add it to the json file

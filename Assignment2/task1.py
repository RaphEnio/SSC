###############################################################
#                                                             #
#                        TASK 1                               # 
#                                                             #                          
###############################################################

import support_functions as sf
path = r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task1.json"
data = sf.read_json(path)

#get needed variables
#convert list of binary values into an integer
plaintext = data["Plaintext Vector"]
#eta = int(data["SWHE"]["Public Parameters"]["eta"])
#gamma  = int(data["SWHE"]["Public Parameters"]["gamma"])
rho = int(data["SWHE"]["Public Parameters"]["rho"])
pk = list(map(int, data["SWHE"]["Public Parameters"]["pk"])) 
tau = int(data["SWHE"]["Public Parameters"]["tau"])

# perform bitwise enryption of the plaintext vector
ciphertext_vector = []
# set to true for binary representation of the ciphertext
binary_repre = False
for bit in plaintext:
    cipher_bit = sf.encrypt_message(int(bit), rho, tau, pk)
    if binary_repre == True:
        cipher_bit = sf.decimalToBinary(cipher_bit)
    ciphertext_vector.append(cipher_bit)

#write the encrypted vector to the json file
entry = {"Encrypted Vector" : ciphertext_vector}
sf.write_json(entry, path)
"""This python file holds the fuctions and other implementation needed for the SSC Assignment 2 """

import json
import secrets 
import math



# reads a given json file and returns it
def read_json(path_to_file):
    # open file
    file = open(path_to_file,"r")
    # load file into dict
    json_data = json.load(file)
    # close file
    file.close()
    return json_data


#encrpyts a given bit according to the SWHE scheme.
# calculates c = pq + 2r + m
def encrypt_message(plain_bit, eta, gamma, rho):

    # sample p. Do this until p is odd
    is_odd = False
    while(not is_odd):
        p = secrets.SystemRandom().randrange(math.pow(2, eta-1), math.pow(2,eta) - 1)
        is_odd = (p % 2 == 0)
    # sample q
    q = secrets.SystemRandom().randrange(0, math.pow(2, gamma)/p)
    # sample r. Do this until r < p/2
    is_valid = False
    while(not is_valid):
        r = secrets.SystemRandom().randrange(0, math.pow(2,rho))
        is_valid = (r < (p/2))
    # encrypt message bit  
    c =  p * q + 2 * r + plain_bit
    return c

# function to convert a number from decimal to binary representation
def decimalToBinary(n):
   return bin(n).replace("0b", "")


###############################################################
#                                                             #
#                        TASK 1                               # 
#                                                             #                          
###############################################################

data = read_json(r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task1.json")

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
    cipher_bit = encrypt_message(int(bit), eta, gamma, rho)
    if binary_repre == True:
        cipher_bit = decimalToBinary(cipher_bit)
    ciphertext_vector.append(cipher_bit)

print(ciphertext_vector)

#need to add it to the json file



###############################################################
#                                                             #
#                        TASK 2                               # 
#                                                             #                          
###############################################################

















###############################################################
#                                                             #
#                        TASK 3                               # 
#                                                             #                          
###############################################################

"""This python file holds the fuctions needed for the SSC Assignment 2 """

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

# write something to a given json file
def write_json(data, path_to_file):
    # open file
    file = open(path_to_file, "r+")
    #load file
    json_data = json.load(file)
    #update the json
    json_data.update(data)
    file.seek(0)
    #write to file
    json.dump(json_data, file)

#encrpyts a given bit according to the SWHE scheme.
# calculates c = pq + 2r + m
def encrypt_message(plain_bit, rho, tau, pk):
    # first sample S
    s = secrets.SystemRandom().randrange(1,tau)
    # Then sample r
    r = secrets.SystemRandom().randrange(- math.pow(2, 2 * rho), math.pow(2, 2 * rho))
    # encrypt message bit  
    c = (plain_bit + 2 * r + 2 * sum(pk[1:s])) % pk[0]
    return c

# function to convert a number from decimal to binary representation
def decimalToBinary(n):
   return bin(n).replace("0b", "")


# decrypt a ciphertext according to m' = (c mod p) mod 2
def decrypt_message(ciphertext, p):
    return (ciphertext % p) % 2

#performs the evaluation step and then checks wether the resultin ciphertext
# is decrypted as 1 and then returns the number of operations performed
def eval_c(pk, operation, ciphertext, sk):
    iterations = 0
    result = 0
    op_cipher = ciphertext
    # do the evaluation
    # perform additional additional additions or mutiplication until
    # the resulting ciphertext is 1
    while(result != 1):
        if operation == "XOR":
            op_cipher = op_cipher + ciphertext
        else:
            op_cipher = op_cipher * ciphertext
        c_ = op_cipher % pk[0]
        iterations += 1
        # decrypt the ciphertext
        result = decrypt_message(c_,sk)
        if result != 0 and result != 1:
            print("DEBUG: ", result)
    return iterations
    
    











 # sample p. Do this until p is odd
    #is_odd = False
    #while(not is_odd):
        #p = secrets.SystemRandom().randrange(math.pow(2, eta-1), math.pow(2,eta) - 1)
        #is_odd = (p % 2 == 0)
    # sample q
    #q = secrets.SystemRandom().randrange(0, math.pow(2, gamma)/p)
    # sample r. Do this until r < p/2
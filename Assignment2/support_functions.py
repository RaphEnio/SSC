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
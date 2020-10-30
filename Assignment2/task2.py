###############################################################
#                                                             #
#                        TASK 2                               # 
#                                                             #                          
###############################################################


import support_functions as sf

data = sf.read_json(r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task2.json")

#get needed variables
#convert list of binary values into an integer
ciphertexts = data["Ciphertext Collection"]
p = int(data["SWHE"]["sk"])


c1 = int(ciphertexts[0]["Ciphertext"])

m1 = sf.decrypt_message(c1,p)

print(m1)
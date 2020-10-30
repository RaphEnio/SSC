###############################################################
#                                                             #
#                        TASK 2                               # 
#                                                             #                          
###############################################################


import support_functions as sf

data = sf.read_json(r"C:\Users\Raphael\github\SSC\Assignment2\json_files\swhe-task2.json")

#get needed variables
pk = list(map(int, data["SWHE"]["Public Parameters"]["pk"])) 
ciphertexts = data["Ciphertext Collection"]
p = int(data["SWHE"]["sk"])


c1 = int(ciphertexts[0]["Ciphertext"])

m1 = sf.decrypt_message(c1,p)

print(m1)


intertions = sf.eval_c(pk, "AND", c1, p)

print("Needed iterations: {}".format(intertions))

#print("Evauleted ciphertext = {}".format(sf.decrypt_message(c,p)))

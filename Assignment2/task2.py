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

for i in range (0,len(ciphertexts)):
    ciphertext = int(ciphertexts[i]["Ciphertext"])
    noise = int(ciphertexts[i]["Noise Bitlength"])
    print("----------{} bit noise----------".format(noise))
    for op in ["XOR", "AND"]:
        intertions = sf.eval_c(pk, op, ciphertext, p)
        print("Max. {} {} operations".format(intertions, op))
    

def hw1(input, output):
    print("Hello World")
    alphabet = "abcdefghijklmnopqrstuvwxyz"
    target_list = []
    alphabet_list = []
    f_in = open(input, 'r')
    f_out = open(output, 'w')
    # populate alphabet list
    for c in alphabet:
        alphabet_list.append(c)
    # scan each line of file to see if it is a pangram
    for line in f_in:
        for c in line:
            if c not in target_list:
                target_list.append(c)
            # exclude non letters
            if c in ",.'?_ /!:;\n\t\"":
                target_list.remove(c)
        target_list.sort()
        if alphabet_list == target_list:
            f_out.write('true\n')
        else:
            f_out.write('false\n')
        target_list.clear()
    f_in.close()
    f_out.close()

hw1("input.txt", "output.txt")

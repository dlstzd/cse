import re
from collections import OrderedDict


class LastUpdatedOrderedDict(OrderedDict):
    'Store items in the order the keys were last added'
    def __setitem__(self, key, value):
        if key in self:
            del self[key]
        OrderedDict.__setitem__(self, key, value)


def hw3(input, output):
    f_in = open(input, 'r')
    f_out = open(output, 'w')
    stack = []
    env_stack = []
    vDict = LastUpdatedOrderedDict()
    for line in f_in:
        splitted = line.split()
        command = splitted[0]
        if command == "quit":
            break
        else:
            ops(command, splitted, stack, vDict, env_stack)
    stack.reverse()
    print("Bind stack ---")

    print(vDict)

    for b in stack:
        b = str(b)
        if re.match(r'\"(.+?)\"', b):  # b is a string, strip quotes
            b = b[1:-1]
        f_out.write(b)
        f_out.write('\n')

    f_in.close()
    f_out.close()


def ops(operator, element, stack, vDict, env_stack):
    env_count = 0
    # push
    if operator == "push":
            if re.match(r'\"(.+?)\"', element[1]):
                stack.append(element[1])
            elif re.search('[a-zA-Z]+[0-9]*', element[1]):
                stack.append(element[1])
            else:
                try:

                    stack.append(str(int(element[1])))
                except ValueError:
                    stack.append(":error:")
    # pop
    elif operator == "pop":
        if not stack:
            stack.append(":error:")
        else:
            stack.pop()
    elif operator == ":true:":
        stack.append(":true:")
    elif operator == ":false:":
        stack.append(":false:")
    elif operator == ":error:":
        stack.append(":error:")
    elif operator == "add":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            add_check(x, y, stack, vDict)
    elif operator == "sub":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            sub_check(x, y, stack, vDict)
    elif operator == "mul":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            mul_check(x, y, stack, vDict)
    elif operator == "div":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            div_check(x, y, stack, vDict)
    elif operator == "rem":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            rem_check(x, y, stack, vDict)
    elif operator == "neg":
        if len(stack) < 1:
            stack.append(":error:")
        else:
            x = stack.pop()
            tempx = x
            aflag = False
            if re.match('(^[a-zA-Z]+[0-9]*)', x):
                aflag = True
                x = vDict.get(x)
            if x == 0:
                stack.append(x)
            else:
                try:
                    x = int(x) * -1
                    stack.append(str(x))
                except ValueError:
                    if aflag is True:
                        stack.append(tempx)
                        stack.append(":error:")
                    else:
                        stack.append(x)
                        stack.append(":error:")
    elif operator == "swap":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            stack.append(x)
            stack.append(y)
    elif operator == "lessThan":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            less_than(x, y, stack, vDict)

    elif operator == "equal":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            equal(x, y, stack, vDict)
    elif operator == "not":
        if len(stack) < 1:
            stack.append(":error:")
        else:
            x = stack.pop()
            tempx = x
            aflag = False
            if re.match('(^[a-zA-Z]+[0-9]*)', x):
                aflag = True
                x = vDict.get(x)
            if x == ":false:":
                x = ":true:"
                stack.append(x)
            elif x == ":true:":
                x = ":false:"
                stack.append(x)
            else:
                if aflag is True:
                    stack.append(tempx)
                    stack.append(":error:")
                else:
                    stack.append(x)
                    stack.append(":error:")
    elif operator == "and":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            tempx = x
            tempy = y
            aflag = False
            bflag = False
            if re.match('(^[a-zA-Z]+[0-9]*)', x):
                aflag = True
                x = vDict.get(x)
            if re.match('(^[a-zA-Z]+[0-9]*)', y):
                bflag = True
                y = vDict.get(y)
            if x == ":false:" and y == ":false:":
                stack.append(":false:")
            elif x == ":false:" and y == ":true:":
                stack.append(":false:")
            elif x == ":true:" and y == ":false:":
                stack.append(":false:")
            elif x == ":true:" and y == ":true:":
                stack.append(":true:")
            else:
                if aflag is True or bflag is True:
                    stack.append(tempy)
                    stack.append(tempx)
                    stack.append(":error:")
                else:
                    stack.append(y)
                    stack.append(x)
                    stack.append(":error:")
    elif operator == "or":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            tempx = x
            tempy = y
            aflag = False
            bflag = False
            if re.match('(^[a-zA-Z]+[0-9]*)', x):
                aflag = True
                x = vDict.get(x)
            if re.match('(^[a-zA-Z]+[0-9]*)', y):
                bflag = True
                y = vDict.get(y)
            if x == ":true:" or x == ":false:":
                if y == ":false:" or y == ":true:":
                    if x == ":false:" and y == ":false:":
                        stack.append(":false:")
                    else:
                        stack.append(":true:")
            else:
                if aflag is True or bflag is True:
                    stack.append(tempy)
                    stack.append(tempx)
                    stack.append(":error:")
                else:
                    stack.append(y)
                    stack.append(x)
                    stack.append(":error:")
    elif operator == "if":
        if len(stack) < 3:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            z = stack.pop()
            if_check(x, y, z, stack)
    elif operator == "bind":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            if re.match('^[a-zA-Z]+[0-9]*', y):
                if re.match('^[a-zA-Z]+[0-9]*', x):  # another var check bind stack for x value
                    temp = vDict.get(x)
                    vDict[y] = temp
                    stack.append(":unit:")
                elif x == ":error:":
                    stack.append(":error:")
                elif x == ":unit:":
                    stack.append(":unit:")
                elif y in vDict:
                    vDict[y] = x
                else:
                    vDict[y] = x
                    stack.append(":unit:")
            else:
                stack.append(y)
                stack.append(x)
                stack.append(":error:")
    elif operator == "let":
        stack.append("let")

        print(len(stack))
    elif operator == "end":
        return_val = stack.pop()
        throw = return_val
        while(throw != "let"):
            throw = stack.pop()

        stack.append(return_val)
        print("close let")
    else:
        print("Command not recognized")


def add_check(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        stack.append(str(int(a)+int(b)))
    except ValueError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def sub_check(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        stack.append(str(int(b)-int(a)))
    except ValueError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def mul_check(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        stack.append(str(int(a)*int(b)))
    except ValueError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def div_check(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        stack.append(str(int(b)//int(a)))
    except ValueError or ZeroDivisionError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def rem_check(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    if b == 0:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")
    else:
        try:
            stack.append(str(int(b) % int(a)))
        except ValueError:
            if aflag is True or bflag is True:
                stack.append(tempb)
                stack.append(tempa)
                stack.append(":error:")
            else:
                stack.append(b)
                stack.append(a)
                stack.append(":error:")


def equal(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        if int(a) == int(b):
            stack.append(":true:")
        else:
            stack.append(":false:")
    except ValueError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def less_than(a, b, stack, vDict):
    tempa = a
    tempb = b
    aflag = False
    bflag = False
    if re.match('(^[a-zA-Z]+[0-9]*)', a):
        tempa = a
        aflag = True
        a = vDict.get(a)
    if re.match('(^[a-zA-Z]+[0-9]*)', b):
        tempb = b
        bflag = True
        b = vDict.get(b)
    try:
        if int(b) < int(a):
            stack.append(":true:")
        else:
            stack.append(":false:")
    except ValueError or TypeError:
        if aflag is True or bflag is True:
            stack.append(tempb)
            stack.append(tempa)
            stack.append(":error:")
        else:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


def if_check(a, b, c, stack):
    try:
        if c == ":true:":
            stack.append(a)
        elif c == ":false:":
            stack.append(b)
        else:
            stack.append(":error:")
    except ValueError:
        stack.append(c)
        stack.append(b)
        stack.append(a)
        stack.append(":error:")
def hw2(input, output):
    f_in = open(input, 'r')
    f_out = open(output, 'w')
    stack = []
    for line in f_in:
        splitted = line.split()
        command = splitted[0]
        if command == "quit":
            break
        else:
            ops(command, splitted, stack)
    stack.reverse()
    for b in stack:
        b = str(b)
        f_out.write(b)
        f_out.write('\n')

    f_in.close()
    f_out.close()


def ops(operator, element, stack):
    # push
    if operator == "push":
        try:
            val = int(element[1])
            stack.append(val)
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
            add_check(x, y, stack)
    elif operator == "sub":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            sub_check(x, y, stack)
    elif operator == "mul":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            mul_check(x, y, stack)
    elif operator == "div":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            div_check(x, y, stack)
    elif operator == "rem":
        if len(stack) < 2:
            stack.append(":error:")
        else:
            x = stack.pop()
            y = stack.pop()
            rem_check(x, y, stack)
    elif operator == "neg":
        if len(stack) < 1:
            stack.append(":error:")
        else:
            x = stack.pop()
            if x == 0:
                stack.append(x)
            else:
                try:
                    x = int(x)
                    stack.append(-x)
                except ValueError:
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
    else:
        print("Command not recognized")


def add_check(a, b, stack):
    try:
        stack.append(int(a)+int(b))
    except ValueError:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")


def sub_check(a, b, stack):
    try:
        stack.append(int(b)-int(a))
    except ValueError:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")


def mul_check(a, b, stack):
    try:
        stack.append(int(a)*int(b))
    except ValueError:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")


def div_check(a, b, stack):
    try:
        stack.append(int(b)//int(a))
    except ValueError:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")
    except ZeroDivisionError:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")


def rem_check(a, b, stack):
    if b == 0:
        stack.append(b)
        stack.append(a)
        stack.append(":error:")
    else:
        try:
            stack.append(int(b) % int(a))
        except ValueError:
            stack.append(b)
            stack.append(a)
            stack.append(":error:")


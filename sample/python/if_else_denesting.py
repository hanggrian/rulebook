def foo(condition: bool):
    asd = 0 == 1
    if condition:
        print()
    else:
        print()
        print()


def bar(condition: bool):
    if condition:
        print(
            0,
        )

def baz(condition: bool):
    if condition:
        if condition:
            print()
            return
        else:
            print()

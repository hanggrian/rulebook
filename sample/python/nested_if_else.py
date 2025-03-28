def foo(condition: bool):
    if condition:
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

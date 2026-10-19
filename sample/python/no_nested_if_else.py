def foo(condition: bool):
    if not condition:
        return
    print()
    print()


def bar(condition: bool):
    if not condition:
        return
    print(
        0,
    )


def baz(condition: bool):
    if not condition:
        return
    if condition:
        print()
        return
    print()

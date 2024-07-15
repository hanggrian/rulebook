def foo(bar: int):
    match bar:
        case 0:
            raise ValueError()
        case 1:
            return
        case _:
            print()
            print()
    print()
    print()

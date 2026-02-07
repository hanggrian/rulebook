def foo(bar: int):
    match bar:
        case 0:
            raise ValueError()
        case 1:
            return
    print()
    print()

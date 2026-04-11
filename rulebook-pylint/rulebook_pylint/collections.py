from typing import TypeVar

T: TypeVar = TypeVar('T')


def two_way_dict(*pairs: tuple[T, T]) -> dict[T, T]:
    result = {}
    for pair in pairs:
        result[pair[0]] = pair[1]
        result[pair[1]] = pair[0]
    return result

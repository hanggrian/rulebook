from json import load
from os.path import join, dirname

from astroid import Assign, AnnAssign, AssignName


class Messages:
    FILENAME: str = 'rulebook_pylint.json'
    counter: int = 7531  # arbitrary number to distinguish from PEP

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        bundle: object = load(file)

    @staticmethod
    def get(*keys: str) -> dict[str, tuple[str, str, str]]:
        result: dict[str, tuple[str, str, str]] = {}
        for key in keys:
            Messages.counter = Messages.counter + 1
            result[f'E{Messages.counter:04d}'] = (
                Messages.bundle[key],
                key,
                'https://github.com/hendraanggrian/rulebook',
            )
        return result


def get_assignname(node: Assign) -> AssignName | None:
    target: AssignName
    if isinstance(node, AnnAssign) and isinstance(node.target, AssignName):
        return node.target
    elif isinstance(node.targets[0], AssignName):
        return node.targets[0]
    return None

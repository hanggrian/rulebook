from json import load
from os.path import join, dirname

from astroid import NodeNG, Name, Assign, AnnAssign, AssignName, FunctionDef, ClassDef
from pylint.typing import MessageDefinitionTuple


class Messages:
    FILENAME: str = 'resources/messages.json'
    counter: int = 7531  # arbitrary number to distinguish from PEP

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        bundle: object = load(file)

    @staticmethod
    def get(key: str) -> str:
        return Messages.bundle[key]

    @staticmethod
    def of(*keys: str) -> dict[str, MessageDefinitionTuple]:
        result: dict[str, MessageDefinitionTuple] = {}
        for key in keys:
            Messages.counter = Messages.counter + 1
            result[f'E{Messages.counter:04d}'] = \
                (
                    Messages.bundle[key],
                    key,
                    'https://github.com/hendraanggrian/rulebook',
                )
        return result


def has_decorator(node: FunctionDef | ClassDef, name: str) -> bool:
    if node.decorators:
        for decorator in node.decorators.nodes:
            if isinstance(decorator, Name) and decorator.name == name:
                return True
    return False


def get_assignname(node: Assign) -> AssignName | None:
    target: NodeNG
    if isinstance(node, AnnAssign):
        target = node.target
    elif isinstance(node.targets[0], AssignName):
        target = node.targets[0]
    else:
        return None

    if isinstance(target, AssignName):
        return target
    return None

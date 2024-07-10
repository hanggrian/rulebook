from json import load
from os.path import join, dirname

from pylint.typing import MessageDefinitionTuple


class Messages:
    FILENAME: str = '../resources/messages.json'
    counter: int = 7531  # arbitrary number to distinguish from PEP

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        bundle: object = load(file)

    @staticmethod
    def get(key: str) -> str:
        return Messages.bundle[key]

    @staticmethod
    def of(*keys: str) -> dict[str, MessageDefinitionTuple]:
        result: dict[str, MessageDefinitionTuple] = {}
        key: str
        for key in keys:
            Messages.counter = Messages.counter + 1
            result[f'E{Messages.counter:04d}'] = \
                (
                    Messages.bundle[key],
                    key,
                    'https://github.com/hanggrian/rulebook',
                )
        return result

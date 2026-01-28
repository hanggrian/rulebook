from configparser import ConfigParser
from os.path import join, dirname

from pylint.typing import MessageDefinitionTuple


class _Messages:
    FILENAME: str = 'resources/messages.cnf'
    counter: int = 6142  # arbitrary number to distinguish from PEP

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        parser: ConfigParser = ConfigParser(interpolation=None)
        parser.read_string(file.read())

    @staticmethod
    def get(key: str) -> str:
        return _Messages.parser.get('default', key)

    @staticmethod
    def of(*keys: str) -> dict[str, MessageDefinitionTuple]:
        result: dict[str, MessageDefinitionTuple] = {}
        key: str
        for key in keys:
            _Messages.counter = _Messages.counter + 1
            result[f'E{_Messages.counter:04d}'] = (
                _Messages.parser.get('default', key),
                key,
                'https://github.com/hanggrian/rulebook',
            )
        return result

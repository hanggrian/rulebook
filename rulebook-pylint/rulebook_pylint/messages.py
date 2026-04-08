from configparser import ConfigParser
from importlib.resources import files


class _Messages:
    counter: int = 6142  # arbitrary number to distinguish from PEP

    with files('rulebook_pylint.resources') \
        .joinpath('pylint_messages.cnf') \
        .open('r', encoding='UTF-8') as file:
        parser: ConfigParser = ConfigParser(interpolation=None)
        parser.read_string(file.read())

    @staticmethod
    def get(key: str) -> str:
        return _Messages.parser.get('default', key)

    @staticmethod
    def of(*keys: str) -> dict[str, tuple[str, str, str]]:
        result: dict[str, tuple[str, str, str]] = {}
        key: str
        for key in keys:
            _Messages.counter = _Messages.counter + 1
            result[f'E{_Messages.counter:04d}'] = (
                _Messages.parser.get('default', key),
                key,
                'https://github.com/hanggrian/rulebook',
            )
        return result

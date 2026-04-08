from configparser import ConfigParser
from importlib.resources import files


class _Messages:
    with files('rulebook_cppcheck.resources') \
        .joinpath('cppcheck_messages.cnf') \
        .open('r', encoding='UTF-8') as file:
        parser: ConfigParser = ConfigParser(interpolation=None)
        parser.read_string(file.read())

    @staticmethod
    def get(key: str, *args) -> str:
        template: str = _Messages.parser.get('default', key)
        if not args:
            return template
        return template.format(*args)

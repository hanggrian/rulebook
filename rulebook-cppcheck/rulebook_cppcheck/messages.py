from configparser import ConfigParser
from os.path import join, dirname


class _Messages:
    FILENAME: str = 'resources/messages.cnf'

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        parser: ConfigParser = ConfigParser(interpolation=None)
        parser.read_string(file.read())

    @staticmethod
    def get(key: str, *args) -> str:
        template: str = _Messages.parser.get('default', key)
        if not args:
            return template
        return template.format(*args)

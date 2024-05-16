from json import load
from os.path import join, dirname


class Messages:
    FILENAME: str = 'rulebook_pylint.json'
    counter: int = 0

    with open(join(dirname(__file__), FILENAME), 'r', encoding='UTF-8') as file:
        bundle: object = load(file)

    @staticmethod
    def get(key: str) -> dict[str, tuple[str, str, str]]:
        Messages.counter = Messages.counter + 1
        return {
            f'W{Messages.counter:04d}': (
                Messages.bundle[key],
                key,
                'https://github.com/hendraanggrian/rulebook',
            )
        }

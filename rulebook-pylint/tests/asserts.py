from pylint.checkers import BaseChecker


def assert_properties(checker_class: type[BaseChecker]):
    line = str(checker_class).rsplit("<class 'rulebook_pylint.checkers.", 1)[1].split("'>", 1)[0]
    module = line.split('.', 1)[0]
    cls = line.rsplit('.', 1)[1]

    assert checker_class.name == module.split('_checker')[0].replace('_', '-')
    assert checker_class.name == \
           ''.join(['_' + c.lower() if c.isupper() else c for c in cls]) \
               .lstrip('_') \
               .split('_checker', 1)[0] \
               .replace('_', '-')

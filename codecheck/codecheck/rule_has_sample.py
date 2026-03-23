from __future__ import annotations

from os.path import exists

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class RuleHasSampleChecker(RulebookFileChecker):
    name: str = 'rule-has-sample'
    msgs = {
        'W9905': ('Missing sample.', 'missing-sample', 'Add a sample.'),
    }

    _PYLINT_PATH: str = '/rulebook-pylint/rulebook_pylint/checkers/'
    _CPPCHECK_PATH: str = '/rulebook-cppcheck/rulebook_cppcheck/checkers/'

    def process_module(self, node: Module) -> None:
        file: str = node.file
        if file.endswith('__init__.py') or file.endswith('rulebook_checkers.py'):
            return
        if self._PYLINT_PATH in file:
            if exists(file.replace(self._PYLINT_PATH, '/sample/python/')):
                return
            if exists(file.replace(self._PYLINT_PATH, '/sample/tests/python/test_')):
                return
            self.add_message('missing-sample', line=1)
        elif self._CPPCHECK_PATH in file:
            file = file.replace('.py', '')
            if exists(file.replace(self._CPPCHECK_PATH, '/sample/c/') + '.c') or \
                exists(file.replace(self._CPPCHECK_PATH, '/sample/cpp/') + '.cpp'):
                return
            self.add_message('missing-sample', line=1)


def register(linter: PyLinter) -> None:
    linter.register_checker(RuleHasSampleChecker(linter))

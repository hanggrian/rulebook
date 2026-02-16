from pathlib import Path
from subprocess import run
from tempfile import TemporaryDirectory
from typing import Any
from unittest import TestCase
from unittest.mock import MagicMock

from rulebook_cppcheck.checkers.rulebook_checkers import BaseChecker

try:
    from cppcheckdata import parsedump
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import parsedump


def assert_properties(checker_class: type[BaseChecker]):
    line = str(checker_class).rsplit("<class 'rulebook_cppcheck.checkers.", 1)[1].split("'>", 1)[0]
    module = line.split('.', 1)[0]
    cls = line.rsplit('.', 1)[1]

    assert checker_class.ID == module.split('_checker')[0].replace('_', '-')
    assert checker_class.ID == \
           ''.join(['_' + c.lower() if c.isupper() else c for c in cls]) \
               .lstrip('_') \
               .split('_checker', 1)[0] \
               .replace('_', '-')


class CheckerTestCase(TestCase):
    CHECKER_CLASS: Any

    def setUp(self):
        self.checker = self.CHECKER_CLASS()
        self.temp_dir = None

    def dump_code(self, code: str):
        if not self.temp_dir:
            # pylint: disable=consider-using-with
            self.temp_dir = TemporaryDirectory()
        temp_dir_name = self.temp_dir.name
        source_file = Path(temp_dir_name) / 'test.cpp'
        source_file.write_text(code)
        run(
            ['cppcheck', '--dump', '--quiet', str(source_file)],
            cwd=temp_dir_name,
            check=True,
            capture_output=True,
        )
        tokens = []
        scopes = []
        for config in parsedump(str(source_file) + '.dump').configurations:
            tokens.extend(config.tokenlist)
            scopes.extend(config.scopes)
        return tokens, scopes

    def tearDown(self):
        if self.temp_dir:
            self.temp_dir.cleanup()

    @staticmethod
    def mock_file():
        return MagicMock(file='test.cpp')

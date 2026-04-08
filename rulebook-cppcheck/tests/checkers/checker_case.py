from pathlib import Path
from subprocess import run
from tempfile import TemporaryDirectory
from typing import Any
from unittest import TestCase
from unittest.mock import MagicMock

try:
    from cppcheckdata import parsedump
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import parsedump


class CheckerTestCase(TestCase):
    CHECKER_CLASS: Any

    def setUp(self):
        self.checker = self.CHECKER_CLASS()
        self.temp_dir = None

    def dump_code(self, code):
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

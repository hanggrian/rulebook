from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.file_name import FileNameChecker
from ..tests import CheckerTestCase, assert_properties


class TestFileNameChecker(CheckerTestCase):
    CHECKER_CLASS = FileNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(FileNameChecker, 'report_error')
    def test_correct_name(self, report_error):
        self.checker.check_file(MagicMock(file='hello_world.c'), '')
        report_error.assert_not_called()

    @patch.object(FileNameChecker, 'report_error')
    def test_incorrect_names(self, report_error):
        self.checker.check_file(MagicMock(file='helloWorld.c'), '')
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Rename file to 'helloworld.c'.")


if __name__ == '__main__':
    main()

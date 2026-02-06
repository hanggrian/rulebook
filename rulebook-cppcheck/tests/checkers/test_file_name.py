from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.file_name import FileNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestFileNameChecker(CheckerTestCase):
    CHECKER_CLASS = FileNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(FileNameChecker, 'report_error')
    def test_correct_name(self, mock_report):
        self.checker.check_file(MagicMock(file='hello_world.c'), '')
        mock_report.assert_not_called()

    @patch.object(FileNameChecker, 'report_error')
    def test_incorrect_names(self, mock_report):
        self.checker.check_file(MagicMock(file='helloWorld.c'), '')
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'helloworld.c'))


if __name__ == '__main__':
    main()

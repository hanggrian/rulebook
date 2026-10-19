from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import MaxFileSizeChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


# noinspection PyUnresolvedReferences
class TestMaxFileSizeChecker(CheckerTestCase):
    CHECKER_CLASS = MaxFileSizeChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MaxFileSizeChecker, 'report_error')
    def test_small_file(self, report_error):
        self.checker.check_file(self.mock_file(), 'int x = 0;')
        report_error.assert_not_called()

    @patch.object(MaxFileSizeChecker, 'report_error')
    def test_large_file(self, report_error):
        self.checker.check_file(self.mock_file(), '// a\n' * 1001)
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Reduce file size to '1000'.")


if __name__ == '__main__':
    main()

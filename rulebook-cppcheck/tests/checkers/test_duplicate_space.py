from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import DuplicateSpaceChecker
from ..tests import CheckerTestCase, assert_properties


class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_single_space_between_token(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo(int bar, int baz) {
                    int qux = 1 + 2;
                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_multiple_spaces_between_token(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo(int bar,  int baz) {
                    int qux = 1 +\t 2;
                }
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], 'Remove consecutive whitespace.')
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], 'Remove consecutive whitespace.')
        self.assertEqual(calls[1][0][2], 3)

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_string_content(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    char *bar = R("  ");
                    char *baz = "  ";
                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_block_comment_asterisk(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 * @return the new size
                 *     of the group.
                 */
                void foo() {}
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_pointers(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                ssize_t read(struct device *dev, struct device_attribute *attr, char *buf) {
                    char *device_type;
                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_arrays(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                static const struct hid_device_id razer_devices[] = {};
                ''',
            ),
        )
        report_error.assert_not_called()


if __name__ == '__main__':
    main()

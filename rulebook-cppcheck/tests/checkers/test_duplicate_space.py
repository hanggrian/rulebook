from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_single_space_between_token(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo(int bar, int baz) {
                    int qux = 1 + 2;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_multiple_spaces_between_token(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo(int bar, int baz) {
                    int qux =  1  +  2;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '1'),
                    'Remove consecutive space.',
                ),
            ],
            any_order=True,
        )

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_pointers(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                ssize_t read(struct device *dev, struct device_attribute *attr, char *buf) {
                    char *device_type;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_skip_arrays(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                static const struct hid_device_id razer_devices[] = {};
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()

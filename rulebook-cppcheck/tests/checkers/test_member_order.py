from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MemberOrderChecker, 'report_error')
    def test_correct_member_layout(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                class MyClass {
                public:
                    int bar = 0;
                    MyClass() {
                        cout << "Hello World!";
                    }
                    void baz() {}
                };
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(MemberOrderChecker, 'report_error')
    def test_property_after_constructor(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                class MyClass {
                public:
                    MyClass() {
                        cout << "Hello World!";
                    }
                    int bar = 0;
                };
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'bar'),
            _Messages.get(self.checker.MSG, 'property', 'constructor'),
        )

    @patch.object(MemberOrderChecker, 'report_error')
    def test_constructor_after_function(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                class MyClass {
                public:
                    void baz() {}
                    MyClass() {
                        cout << "Hello World!";
                    }
                };
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'MyClass' and t.linenr == 5),
            _Messages.get(self.checker.MSG, 'constructor', 'function'),
        )


if __name__ == '__main__':
    main()

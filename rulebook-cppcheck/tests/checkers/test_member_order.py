from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from ..tests import CheckerTestCase, assert_properties


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MemberOrderChecker, 'report_error')
    def test_correct_member_layout(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(MemberOrderChecker, 'report_error')
    def test_property_after_constructor(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'bar'),
            "Arrange 'property' before 'constructor'.",
        )

    @patch.object(MemberOrderChecker, 'report_error')
    def test_constructor_after_function(self, report_error):
        tokens, _ = \
            self.dump_code(
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
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'MyClass' and t.linenr == 5),
            "Arrange 'constructor' before 'function'.",
        )


if __name__ == '__main__':
    main()

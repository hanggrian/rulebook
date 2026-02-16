from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.member_separator import MemberSeparatorChecker
from ..tests import assert_properties, CheckerTestCase


class TestMemberSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = MemberSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_single_line_members_with_separator(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class Foo {
                public:
                    int bar = 0;

                    Foo() {}

                    void baz() {}
                };
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_single_line_members_without_separator(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                class Foo {
                public:
                    int bar = 0;
                    Foo() {}
                    void baz() {}
                };
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == ';' and t.linenr == 4 and t.column == 32),
                    "Add blank line after 'property'.",
                ),
                call(
                    next(t for t in tokens if t.str == '}' and t.linenr == 5),
                    "Add blank line after 'constructor'.",
                ),
            ],
            any_order=True,
        )

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_multiline_members_with_separator(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class Foo {
                public:
                    int bar =
                        1 +
                        2;

                    Foo() {
                        cout << "Hello world";
                    }

                    void baz() {
                        cout << "Hello world";
                    }
                };
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_multiline_members_without_separator(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                class Foo {
                public:
                    int bar =
                        1 +
                        2;
                    Foo() {
                        cout << "Hello world";
                    }
                    void baz() {
                        cout << "Hello world";
                    }
                };
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == ';' and t.linenr == 6),
                    "Add blank line after 'property'.",
                ),
                call(
                    next(t for t in tokens if t.str == '}' and t.linenr == 9),
                    "Add blank line after 'constructor'.",
                ),
            ],
            any_order=True,
        )

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_skip_fields_grouped_together(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class Foo {
                public:
                    int bar = 1;
                    int baz = 2;
                    int qux =
                        3 +
                        4;
                };
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

if __name__ == '__main__':
    main()

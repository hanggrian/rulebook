from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from ..tests import CheckerTestCase, assert_properties


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_at_the_bottom(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                class Foo {
                    int bar = 0;
                    void baz() {}
                    class Inner {}
                    class AnotherInner {}
                };
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_before_members(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                class Foo {
                    class Inner {}
                    int bar = 0;
                    class AnotherInner {}
                    void baz() {}
                };
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'bar'),
            'Move inner class to the bottom.',
        )

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_skip_anonymous_inner_scope(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                class Foo {
                    class {
                        void bar() {}
                    } anonymous
                };
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_target_all_class_like_declarations(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                struct OuterStruct {
                    struct InnerStruct {}
                    int member = 0;
                };
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'member' and t.linenr == 4),
            'Move inner class to the bottom.',
        )


if __name__ == '__main__':
    main()

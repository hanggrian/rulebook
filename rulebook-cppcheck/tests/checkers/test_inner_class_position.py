from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_invalid_inner_class_position(self, mock_report):
        outer_scope = MagicMock()
        outer_scope.type = 'Class'

        class2 = MagicMock(str='class')

        name = MagicMock()
        class2.next = name

        inner_scope = MagicMock()
        inner_scope.nestedIn = outer_scope
        name.typeScope = inner_scope

        function = MagicMock()
        function.scope = outer_scope
        function.function.tokenDef = function
        name.next = function
        function.next = MagicMock(str='}')

        body_start = MagicMock(next=class2)
        body_start.scope = outer_scope
        outer_scope.bodyStart = body_start
        outer_scope.bodyEnd = function.next

        self.checker.process_token(body_start)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_valid_inner_class_position(self, mock_report):
        outer_scope = MagicMock()
        outer_scope.type = 'Class'

        function = MagicMock()
        function.scope = outer_scope
        function.function.tokenDef = function

        class2 = MagicMock(str='class')
        function.next = class2

        body_start = MagicMock(next=function)
        body_start.scope = outer_scope
        outer_scope.bodyStart = body_start

        outer_scope.bodyEnd = MagicMock(str='}')
        class2.next = outer_scope.bodyEnd

        self.checker.process_token(body_start)
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()

from sys import argv

from rulebook_cppcheck.checkers import \
    AbbreviationAsWordChecker, \
    AssignmentWrapChecker, \
    BlockCommentSpacesChecker, \
    BlockCommentTrimChecker, \
    BlockTagIndentationChecker, \
    BlockTagPunctuationChecker, \
    CaseSeparatorChecker, \
    ChainCallWrapChecker, \
    ClassNameChecker, \
    CommentSpacesChecker, \
    CommentTrimChecker, \
    ComplicatedAssignmentChecker, \
    DuplicateBlankLineChecker, \
    DuplicateBlankLineInBlockCommentChecker, \
    DuplicateBlankLineInCommentChecker, \
    DuplicateSpaceChecker, \
    FileNameChecker, \
    FileSizeChecker, \
    FinalNewlineChecker, \
    GenericNameChecker, \
    IdentifierNameChecker, \
    IllegalCatchChecker, \
    IllegalThrowChecker, \
    IllegalVariableNameChecker, \
    ImportOrderChecker, \
    IndentStyleChecker, \
    InnerClassPositionChecker, \
    LineLengthChecker, \
    LonelyCaseChecker, \
    LowercaseFChecker, \
    LowercaseHexadecimalChecker, \
    MeaninglessWordChecker, \
    MemberOrderChecker, \
    MemberSeparatorChecker, \
    OperatorWrapChecker, \
    PackageNameChecker, \
    ParameterWrapChecker, \
    ParenthesesClipChecker, \
    ParenthesesTrimChecker, \
    RedundantDefaultChecker, RedundantElseChecker, \
    TodoCommentChecker, \
    UnnecessaryReturnChecker, \
    UnnecessaryTrailingWhitespaceChecker, \
    UppercaseLChecker
from rulebook_cppcheck.checkers.rulebook_checkers import BaseChecker

try:
    from cppcheckdata import CppcheckData
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import CppcheckData

if __name__ == '__main__':
    checkers: list[BaseChecker] = [
        BlockTagPunctuationChecker(),
        TodoCommentChecker(),
        # Clipping
        ParenthesesClipChecker(),
        # Declaring
        LowercaseFChecker(),
        LowercaseHexadecimalChecker(),
        UppercaseLChecker(),
        # Formatting
        FileSizeChecker(),
        FinalNewlineChecker(),
        IndentStyleChecker(),
        LineLengthChecker(),
        UnnecessaryTrailingWhitespaceChecker(),
        # Naming
        AbbreviationAsWordChecker(),
        ClassNameChecker(),
        FileNameChecker(),
        IdentifierNameChecker(),
        IllegalVariableNameChecker(),
        GenericNameChecker(),
        MeaninglessWordChecker(),
        PackageNameChecker(),
        # Ordering
        ImportOrderChecker(),
        InnerClassPositionChecker(),
        MemberOrderChecker(),
        # Spacing
        BlockCommentSpacesChecker(),
        BlockTagIndentationChecker(),
        CaseSeparatorChecker(),
        CommentSpacesChecker(),
        MemberSeparatorChecker(),
        # Stating
        ComplicatedAssignmentChecker(),
        IllegalCatchChecker(),
        IllegalThrowChecker(),
        LonelyCaseChecker(),
        RedundantDefaultChecker(),
        RedundantElseChecker(),
        UnnecessaryReturnChecker(),
        # Trimming
        BlockCommentTrimChecker(),
        CommentTrimChecker(),
        DuplicateBlankLineChecker(),
        DuplicateBlankLineInBlockCommentChecker(),
        DuplicateBlankLineInCommentChecker(),
        DuplicateSpaceChecker(),
        ParenthesesTrimChecker(),
        # Wrapping
        AssignmentWrapChecker(),
        ChainCallWrapChecker(),
        OperatorWrapChecker(),
        ParameterWrapChecker(),
    ]

    system_args: list[str] = argv
    for checker in checkers:
        mapped_args: dict[str, str] = {}
        must_continue: bool = False
        for arg in checker.ARGS:
            for system_arg in system_args.copy():
                if not system_arg.startswith(f'--{arg}='):
                    continue
                mapped_args[arg] = system_arg.split('=')[1]
                system_args.remove(system_arg)
                must_continue = True
            if must_continue:
                continue
        if mapped_args:
            checker.before_run(mapped_args)

    for dump_file in [arg for arg in argv[1:] if arg.endswith('.dump')]:
        for config in CppcheckData(dump_file).iterconfigurations():
            [checker.run_check(config) for checker in checkers]

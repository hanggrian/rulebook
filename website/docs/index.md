## Features

!!! features1 "What is Rulebook?"
    <div class="grid cards" markdown>

    - <div style="text-align: center">:material-puzzle:{ .xxxl #primary }<br><b id="primary">Library extension</b><br>A set of additional rules for static code analysis tools</div>
    - <div style="text-align: center">:material-translate:{ .xxxl #primary }<br><b id="primary">Multiple languages</b><br>Originally written for Kotlin, it now supports Java, Groovy and Python</div>
    </div>
!!! features2 "Features"
    <div class="grid cards" markdown>

    - <div style="text-align: center">:material-creation:{ .xxxl #primary }<br><b id="primary">Follows guidelines</b><br>Works together with standard rules from the linters and respects common coding convention</div>
    - <div style="text-align: center">:material-mirror:{ .xxxl #primary }<br><b id="primary">Shared configuration</b><br>Available in standard and Google Style Guide variants with adjusted defaults.</div>
    </div>

## Compatibility table

!!! legend
    <div class="grid" markdown>

    :material-check-all:{ .xl .middle }&ensp;The rule is fully implemented
    { .card }

    :material-check:{ .xl .middle }&ensp;Functionality already exists in other rules
    { .card }

    [:material-check:{ .xl .middle }](#)&ensp;The rule is already supported by the linter
    { .card }

    [:material-close:{ .xl .middle #accent }](#)&ensp;Not supported due to technical limitations
    { .card }

    > **Empty** &mdash; not applicable to this language
    </div>

Rule | Java | Groovy | Kotlin | Python
--- | :---: | :---: | :---: | :---:
Block tag punctuation[^oracle-javadoc-tool] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Built-in types[^kotlin-mapped-types] | | | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/deprecated-typing-alias.html)
Confusing predicate[^kotlin-filter-by-predicate] | | | :material-check-all:{ .lg }
Null equality[^kotlin-structural-equality] | | | :material-check-all:{ .lg }
Redundant qualifier[^jetbrains-remove-redundant-qualifier-name] | :material-check-all:{ .lg } | :material-check-all:{ .lg }
TODO comment[^jetbrains-using-todo] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/misc/todocomment.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Trailing comma in call[^kotlin-trailing-commas] | | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-call-site) | :material-check-all:{ .lg }
Trailing comma in collection[^python-when-to-use-trailing-commas] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-convention.html#trailingcomma-rule) | | :material-check:{ .lg }
Trailing comma in declaration[^kotlin-trailing-commas] | | | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-declaration-site) | :material-check:{ .lg }
Unused import[^remove-unused-imports] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/imports/unusedimports.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-imports.html#unusedimport-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-unused-imports) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/unused-import.html)
Wildcard import[^google-import-statements] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-imports.html#nowildcardimports-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-wildcard-imports) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/wildcard-import.html)
<span class="material-symbols-outlined lg middle">format_text_clip</span> **Clipping group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Empty braces clip[^google-braces-empty-blocks] | [:material-check:{ .lg }](https://checkstyle.org/checks/regexp/regexpsinglelinejava.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Empty brackets clip[^concise-brackets-parentheses-and-tags] | | :material-check-all:{ .lg } | | :material-check:{ .lg }
Empty parentheses clip[^concise-brackets-parentheses-and-tags] | [:material-check:{ .lg }](https://checkstyle.org/checks/regexp/regexpmultiline.html) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#parenthesis-spacing)[:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-spacing) | :material-check:{ .lg }
Empty tags clip[^concise-brackets-parentheses-and-tags] | :material-check:{ .lg } | :material-check-all:{ .lg }
Short block comment clip[^kotlin-documentation-comments] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/javadoc/singlelinejavadoc.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">data_object</span> **Declaring group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Abstract class definition[^common-abstract-class-require-abstract-method] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-design.html#abstractclasswithoutabstractmethod-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Contract function definition[^kotlin-kotlin-contracts] | | | :material-check-all:{ .lg }
Exception inheritance[^oracle-throwing] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendserror-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendsthrowable-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } |
Number suffix for double[^groovy-number-type-suffixes] | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Number suffix for float[^groovy-number-type-suffixes] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Number suffix for integer[^groovy-number-type-suffixes] | | :material-check-all:{ .lg }
Number suffix for long[^groovy-number-type-suffixes] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/misc/upperell.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-convention.html#longliteralwithlowercasel-rule)
String quotes[^google-features-strings-use-single-quotes] | | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-unnecessary.html#unnecessarygstring-rule) | | :material-check-all:{ .lg }
Unnecessary parentheses in lambda[^oracle-lambdaexpressions] | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Utility class definition[^hide-utility-class-instance] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html) | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">draft</span> **Formatting group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
File size[^kotlin-source-file-organization] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/sizes/filelength.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-size.html#classsize-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Final newline[^editorconfig-file-format] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/misc/newlineatendoffile.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#fileendswithoutnewline-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#final-newline) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/missing-final-newline.html)
Indent style[^editorconfig-file-format] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/whitespace/filetabcharacter.html#FileTabCharacter) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-convention.html#notabcharacter-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#indentation) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/bad-indentation.html)
Line length[^google-column-limit] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/sizes/linelength.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#linelength-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#max-line-length) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/line-too-long.html)
<span class="material-symbols-outlined lg middle">label_important</span> **Naming group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Class name acronym[^kotlin-choose-good-names] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Class name[^oracle-codeconventions-namingconventions] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/typename.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#classname-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#interfacename-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#class-naming) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/invalid-name.html)
Constant property name[^oracle-codeconventions-namingconventions] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/constantname.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#fieldname-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#propertyname-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#property-naming)
File name[^kotlin-source-file-names] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#classnamesameasfilename-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#file-name)
Identifier name[^oracle-codeconventions-namingconventions] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/catchparametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/lambdaparametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/localvariablename.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/localfinalvariablename.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/membername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/methodname.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/parametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/patternvariablename.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/recordcomponentname.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/staticvariablename.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#methodname-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#parametername-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#variablename-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#function-naming) | :material-check:{ .lg }
Illegal class final name[^kotlin-choose-good-names] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Illegal variable name[^avoid-primitive-names] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/convention/disallowed-name.html)
Package name[^oracle-codeconventions-namingconventions] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/packagename.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-naming.html#packagename-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#package-name) | :material-check:{ .lg }
Property name interop[^kotlin-properties] | | | :material-check-all:{ .lg }
Required generics name[^oracle-types] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/classtypeparametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/interfacetypeparametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/methodtypeparametername.html)[:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/naming/recordtypeparametername.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">swap_vert</span> **Ordering group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Block tag order[^android-block-tags] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/javadoc/atclauseorder.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Built-in function position[^common-place-built-in-methods-last] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Import order[^google-import-ordering-and-spacing] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/imports/customimportorder.html) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#import-ordering) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/wrong-import-order.html)
Inner class position[^kotlin-class-layout] | [:material-check:{ .lg }](https://checkstyle.org/checks/design/innertypelast.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Member order[^kotlin-class-layout] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Overload function position[^kotlin-overload-layout] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/coding/overloadmethodsdeclarationorder.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Static import position[^google-import-ordering-and-spacing] | :material-check:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-imports.html#misorderedstaticimports-rule)
<span class="material-symbols-outlined lg middle">format_letter_spacing_2</span> **Spacing group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Block comment spaces[^oracle-javadoc] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingwhitespaceafterasterisk.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-comments.html#spaceaftercommentdelimiter-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-comments.html#spacebeforecommentdelimiter-rule) | :material-check-all:{ .lg }
Block tag indentation[^google-javadoc-block-tags] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/javadoc/javadoctagcontinuationindentation.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Case separator[^kotlin-control-flow-statements] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Comment spaces[^android-horizontal] | :material-check-all:{ .lg } | :material-check:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#comment-spacing) | [:material-check:{ .lg }](https://peps.python.org/pep-0008/#inline-comments)
Member separator[^google-vertical-whitespace] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/whitespace/emptylineseparator.html) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#blank-line-before-declarations) | :material-check-all:{ .lg }
Missing blank line before block tags[^kotlin-kdoc-syntax] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/javadoc/requireemptylinebeforeblocktaggroup.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">code_blocks</span> **Stating group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Illegal catch[^oracle-throwing] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#catcherror-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#catchexception-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#catchthrowable-rule) | [:material-close:{ .lg #accent }](https://discuss.kotlinlang.org/t/does-kotlin-have-multi-catch/486/) | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/bare-except.html)
Illegal throw[^oracle-throwing] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#throwerror-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#throwexception-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-exceptions.html#throwthrowable-rule) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/warning/broad-exception-caught.html)
Missing braces[^android-braces] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html) | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-braces.html#ifstatementbraces-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-braces.html#forstatementbraces-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-braces.html#whilestatementbraces-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-if-else)[:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-loop)
Nested if-else[^jetbrains-invert-if] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Redundant default[^jetbrains-redundant-if-else-block] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Redundant else[^jetbrains-redundant-if-else-block] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-raise.html)[:material-check:{ .lg }](https://pylint.pycqa.org/en/stable/user_guide/messages/refactor/no-else-return.html)
Unnecessary switch[^jetbrains-switch-statement-with-too-few-branches] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">format_letter_spacing_standard</span> **Trimming group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Block comment trim[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-comments.html#javadocemptyfirstline-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-comments.html#javadocemptylastline-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Braces trim[^google-blocks-k-r-style] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#blockendswithblankline-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#blockstartswithblankline-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#classendswithblankline-rule)[:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#classstartswithblankline-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-empty-first-line-at-start-in-class-body)[:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-before) | :material-check:{ .lg }
Brackets trim[^strip-multiline-brackets-parentheses-and-tags] | | :material-check-all:{ .lg } | | :material-check:{ .lg }
Comment trim[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Duplicate blank line[^google-vertical-whitespace] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#consecutiveblanklines-rule) | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-consecutive-blank-lines) | :material-check-all:{ .lg }
Duplicate blank line in block comment[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-comments.html#javadocconsecutiveemptylines-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Duplicate blank line in comment[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Duplicate space[^kotlin-horizontal-whitespace] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/whitespace/singlespaceseparator.html) | :material-close:{ .lg #accent } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-multi-spaces) | :material-check-all:{ .lg }
Parentheses trim[^strip-multiline-brackets-parentheses-and-tags] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-in-list) | :material-check-all:{ .lg }
Tags trim[^strip-multiline-brackets-parentheses-and-tags] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check:{ .lg }
Unnecessary blank line after colon[^google-blocks-k-r-style] | | | | :material-check-all:{ .lg }
Unnecessary blank line before package[^google-source-file-structure] | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://codenarc.org/codenarc-rules-formatting.html#blanklinebeforepackage-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">format_text_wrap</span> **Wrapping group** | :material-language-java:{ .lg } | :simple-apachegroovy:{ .lg } | :material-language-kotlin:{ .lg } | :material-language-python:{ .lg }
Assignment wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-expression-wrapping) | :material-close:{ .lg #accent }
Chain call wrap[^kotlin-wrap-chained-calls] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#chain-method-continuation) | :material-close:{ .lg #accent }
Elvis wrap[^move-trailing-elvis-to-the-next-line] | | | :material-check-all:{ .lg }
Infix call wrap[^android-where-to-break] | | | :material-check-all:{ .lg }
Lambda wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | :material-close:{ .lg #accent }
Operator wrap[^android-where-to-break] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/whitespace/operatorwrap.html) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#condition-wrapping) | :material-close:{ .lg #accent }
Parameter wrap[^kotlin-class-headers] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#argument-list-wrapping)[:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-wrapping) | :material-check-all:{ .lg }
Statement wrap[^codeconventions-statements] | [:material-check:{ .lg }](https://checkstyle.sourceforge.io/checks/coding/onestatementperline.html) | :material-check-all:{ .lg } | [:material-check:{ .lg }](https://pinterest.github.io/ktlint/latest/rules/standard/#statement-wrapping) | [:material-check:{ .lg }](https://peps.python.org/pep-0008/#other-recommendations)

## Download

!!! download1 "Integrate the linter tools"
    <div class="grid cards" markdown>

    -   :material-language-java:{ .lg .middle } **Java**

        ---

        <a href="https://checkstyle.org/">
          <img
            width="45px"
            alt="Checkstyle Logo"
            title="Checkstyle Logo"
            src="images/checkstyle.png">
        </a>

        Checkstyle is a development tool to help programmers write Java code that
        adheres to a coding standard.

        [Maven Plugin :material-open-in-new:](https://maven.apache.org/plugins/maven-checkstyle-plugin/)&emsp;[Gradle Plugin :material-open-in-new:](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)

    -   :simple-apachegroovy:{ .lg .middle } **Groovy**

        ---

        <a href="https://codenarc.org/">
          <img
            width="100px"
            alt="CodeNarc Logo"
            title="CodeNarc Logo"
            src="images/codenarc.png">
        </a>

        CodeNarc is similar to popular static analysis tools such as PMD or Checkstyle.

        [Maven Plugin :material-open-in-new:](https://gleclaire.github.io/codenarc-maven-plugin/)&emsp;[Gradle Plugin :material-open-in-new:](https://docs.gradle.org/current/userguide/codenarc_plugin.html)

    -   :material-language-kotlin:{ .lg .middle } **Kotlin**

        ---

        <a href="https://pinterest.github.io/ktlint/">
          <img
            width="120px"
            alt="Ktlint Logo"
            title="Ktlint Logo"
            src="images/ktlint.svg">
        </a>

        An anti-bikeshedding Kotlin linter with built-in formatter.

        [Gradle Plugin (Unofficial) :material-open-in-new:](https://github.com/JLLeitschuh/ktlint-gradle/)

    -   :material-language-python:{ .lg .middle } **Python**

        ---

        <a href="https://www.pylint.org/">
            <img
              width="110px"
              alt="Pylint Logo"
              title="Pylint Logo"
              src="images/pylint.png">
        </a>

        It's not just a linter that annoys you!

        [PyPI :material-open-in-new:](https://pypi.org/project/pylint/)
    </div>
!!! download2 "Download the library"
    [:simple-apachemaven:{ .lg .middle } Maven](https://repo1.maven.org/maven2/com/hanggrian/rulebook/){ .md-button .md-button--primary }&emsp;[:simple-pypi:{ .lg .middle } PyPI](https://pypi.org/project/rulebook-pylint/){ .md-button }

[^common-abstract-class-require-abstract-method]: [Abstract class require abstract method](rationales/index.md#abstract-class-require-abstract-method)
[^avoid-primitive-names]: [Avoid primitive names](rationales/index.md#avoid-primitive-names)
[^concise-brackets-parentheses-and-tags]: [Concise brackets, parentheses and tags](rationales/index.md#concise-brackets-parentheses-and-tags)
[^hide-utility-class-instance]: [Hide utility class instance](rationales/index.md#hide-utility-class-instance)
[^move-trailing-elvis-to-the-next-line]: [Move trailing elvis to the next line](rationales/index.md#move-trailing-elvis-to-the-next-line)
[^no-blank-lines-at-initial-final-and-consecutive-comments]: [No blank lines at initial, final and consecutive comments](rationales/index.md#no-blank-lines-at-initial-final-and-consecutive-comments)
[^common-place-built-in-methods-last]: [Place built-in methods last](rationales/index.md#place-built-in-methods-last)
[^remove-unused-imports]: [Remove unused imports](rationales/index.md#remove-unused-imports)
[^strip-multiline-brackets-parentheses-and-tags]: [Strip multiline brackets, parentheses and tags](rationales/index.md#strip-multiline-brackets-parentheses-and-tags)
[^android-block-tags]: :simple-android:{ #android .lg .middle } [Android: Block tags :material-open-in-new:](https://developer.android.com/kotlin/style-guide#block_tags)
[^android-braces]: :simple-android:{ #android .lg .middle } [Android: Braces :material-open-in-new:](https://developer.android.com/kotlin/style-guide#braces)
[^android-horizontal]: :simple-android:{ #android .lg .middle } [Android: Horizontal whitespace :material-open-in-new:](https://developer.android.com/kotlin/style-guide#horizontal)
[^android-where-to-break]: :simple-android:{ #android .lg .middle } [Android: Where to break :material-open-in-new:](https://developer.android.com/kotlin/style-guide#where_to_break)
[^editorconfig-file-format]: :simple-editorconfig:{ #editorconfig .lg .middle } [EditorConfig: What's an EditorConfig file look like? :material-open-in-new:](https://editorconfig.org/#file-format)
[^google-blocks-k-r-style]: :simple-google:{ #google .lg .middle } [Google: Nonempty blocks :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s4.1.2-blocks-k-r-style)
[^google-braces-empty-blocks]: :simple-google:{ #google .lg .middle } [Google: Empty blocks :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s4.1.3-braces-empty-blocks)
[^google-column-limit]: :simple-google:{ #google .lg .middle } [Google: Column limit: 100 :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s4.4-column-limit)
[^google-features-strings-use-single-quotes]: :simple-google:{ #google .lg .middle } [Google: Use single quotes :material-open-in-new:](https://google.github.io/styleguide/jsguide.html#features-strings-use-single-quotes)
[^google-javadoc-block-tags]: :simple-google:{ #google .lg .middle } [Google: Block tags :material-open-in-new:](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s7.1.3-javadoc-block-tags)
[^google-line-wrapping-where-to-break]: :simple-google:{ #google .lg .middle } [Google: Where to break :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s4.5.1-line-wrapping-where-to-break)
[^google-import-ordering-and-spacing]: :simple-google:{ #google .lg .middle } [Google: Ordering and spacing :material-open-in-new:](https://checkstyle.sourceforge.io/styleguides/google-java-style-20220203/javaguide.html#s3.3.3-import-ordering-and-spacing)
[^google-import-statements]: :simple-google:{ #google .lg .middle } [Google: Import statements :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s3.3-import-statements)
[^google-source-file-structure]: :simple-google:{ #google .lg .middle } [Google: Source file structure :material-open-in-new:](https://google.github.io/styleguide/javaguide.html#s3-source-file-structure)
[^google-vertical-whitespace]: :simple-google:{ #google .lg .middle } [Google: Vertical whitespace :material-open-in-new:](https://google.github.io/styleguide/jsguide.html#formatting-vertical-whitespace)
[^groovy-number-type-suffixes]: :simple-gradle:{ #groovy .lg .middle } [Groovy: Number type suffixes :material-open-in-new:](https://groovy-lang.org/syntax.html#_number_type_suffixes)
[^jetbrains-invert-if]: :simple-jetbrains:{ #jetbrains .lg .middle } [JetBrains: Invert if statement :material-open-in-new:](https://www.jetbrains.com/help/resharper/InvertIf.html)
[^jetbrains-redundant-if-else-block]: :simple-jetbrains:{ #jetbrains .lg .middle } [JetBrains: Redundant else keyword :material-open-in-new:](https://www.jetbrains.com/help/resharper/RedundantIfElseBlock.html)
[^jetbrains-remove-redundant-qualifier-name]: :simple-jetbrains:{ #jetbrains .lg .middle } [JetBrains: Redundant qualifier name :material-open-in-new:](https://www.jetbrains.com/help/inspectopedia/RemoveRedundantQualifierName.html)
[^jetbrains-switch-statement-with-too-few-branches]: :simple-jetbrains:{ #jetbrains .lg .middle } [JetBrains: Minimum switch branches :material-open-in-new:](https://www.jetbrains.com/help/inspectopedia/SwitchStatementWithTooFewBranches.html)
[^jetbrains-using-todo]: :simple-jetbrains:{ #jetbrains .lg .middle } [JetBrains: TODO comments :material-open-in-new:](https://www.jetbrains.com/help/idea/using-todo.html)
[^kotlin-choose-good-names]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Choose good names :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#choose-good-names)
[^kotlin-class-headers]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Class headers :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#class-headers)
[^kotlin-class-layout]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Class layout :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#class-layout)
[^kotlin-control-flow-statements]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Control flow statements :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#control-flow-statements)
[^kotlin-documentation-comments]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Documentation comments :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#documentation-comments)
[^kotlin-filter-by-predicate]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Filter by predicate :material-open-in-new:](https://kotlinlang.org/docs/collection-filtering.html#filter-by-predicate)
[^kotlin-horizontal-whitespace]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Horizontal whitespace :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#horizontal-whitespace)
[^kotlin-kdoc-syntax]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: KDoc syntax :material-open-in-new:](https://kotlinlang.org/docs/kotlin-doc.html#kdoc-syntax)
[^kotlin-kotlin-contracts]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Kotlin contracts :material-open-in-new:](https://github.com/Kotlin/KEEP/blob/master/proposals/kotlin-contracts.md)
[^kotlin-mapped-types]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Mapped types :material-open-in-new:](https://kotlinlang.org/docs/java-interop.html#mapped-types)
[^kotlin-overload-layout]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Overload layout :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#overload-layout)
[^kotlin-properties]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Calling Kotlin from Java :material-open-in-new:](https://kotlinlang.org/docs/java-to-kotlin-interop.html#properties)
[^kotlin-source-file-names]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Source file names :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#source-file-names)
[^kotlin-source-file-organization]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Source file organization :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#source-file-organization)
[^kotlin-structural-equality]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Structural equality :material-open-in-new:](https://kotlinlang.org/docs/equality.html#structural-equality)
[^kotlin-trailing-commas]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Trailing commas :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#trailing-commas)
[^kotlin-wrap-chained-calls]: :simple-kotlin:{ #kotlin .lg .middle } [Kotlin: Wrap chained calls :material-open-in-new:](https://kotlinlang.org/docs/coding-conventions.html#wrap-chained-calls)
[^oracle-codeconventions-namingconventions]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Naming conventions :material-open-in-new:](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
[^codeconventions-statements]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Simple statements :material-open-in-new:](https://www.oracle.com/java/technologies/javase/codeconventions-statements.html)
[^oracle-javadoc]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Javadoc reference :material-open-in-new:](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)
[^oracle-javadoc-tool]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Javadoc tool :material-open-in-new:](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
[^oracle-lambdaexpressions]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Lambda expressions :material-open-in-new:](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
[^oracle-throwing]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: How to Throw Exceptions :material-open-in-new:](https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html)
[^oracle-types]: :simple-openjdk:{ #oracle .lg .middle } [Oracle: Generic types :material-open-in-new:](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
[^python-when-to-use-trailing-commas]: :simple-python:{ #python .lg .middle } [Python: When to use trailing commas :material-open-in-new:](https://peps.python.org/pep-0008/#when-to-use-trailing-commas)

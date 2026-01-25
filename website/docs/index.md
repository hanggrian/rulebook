---
hide:
  - navigation
---

!!! features1 "What is Rulebook?"
    <div class="grid cards" markdown>

    - <div style="text-align: center">
        :material-puzzle:{ .xxxl #primary }<br>
        <b id="primary">Library extension</b><br>
        A set of additional rules for static code analysis tools
      </div>
    - <div style="text-align: center">
        :material-translate:{ .xxxl #primary }<br>
        <b id="primary">Multiple languages</b><br>
        Originally written for Kotlin, it now supports Java, Groovy and Python
      </div>
    </div>
!!! features2 "Features"
    <div class="grid cards" markdown>

    - <div style="text-align: center">
        :material-creation:{ .xxxl #primary }<br>
        <b id="primary">Follows guidelines</b><br>
        Works together with standard rules from the linters and respects common
        coding convention
      </div>
    - <div style="text-align: center">
        :material-mirror:{ .xxxl #primary }<br>
        <b id="primary">Shared configuration</b><br>
        Available in standard and Google Style Guide variants with adjusted
        defaults.
      </div>
    </div>

## Compatibility table

!!! legend
    <div class="grid" markdown>

    :material-check-all:{ .xl .middle }&ensp;The rule is fully implemented
    { .card }

    :material-check:{ .xl .middle }&ensp;Functionality already exists in other
    rules
    { .card }

    :material-information-outline:{ .xl .middle }&ensp;The rule is already
    supported by the linter
    { .card }

    :material-close:{ .xl .middle }&ensp;Not supported due to technical
    limitations
    { .card }

    :material-help:{ .xl .middle }&ensp;Feature currently unexplored
    { .card }

    > **Empty** &mdash; not applicable to this language
    </div>

Rule | Java | Groovy | Kotlin | Python | JavaScript | TypeScript
--- | :---: | :---: | :---: | :---: | :---: | :---:
Block tag punctuation[^oracle-javadoc-tool] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Built-in types[^kotlin-mapped-types] | | | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="deprecated-typing-alias" }](https://pylint.pycqa.org/en/latest/user_guide/messages/warning/deprecated-typing-alias.html)
Confusing predicate[^kotlin-filter-by-predicate] | | | :material-check-all:{ .lg }
Null equality[^kotlin-structural-equality] | | | :material-check-all:{ .lg }
Redundant qualifier[^jetbrains-remove-redundant-qualifier-name] | :material-check-all:{ .lg } | :material-check-all:{ .lg }
TODO comment[^jetbrains-using-todo] | [:material-information-outline:{ .lg title="TodoComment" }](https://checkstyle.sourceforge.io/checks/misc/todocomment.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check:{ .lg }
Trailing comma in call[^kotlin-trailing-commas] | | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="trailing-comma-on-call-site" }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-call-site) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/) | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/)
Trailing comma in collection[^python-when-to-use-trailing-commas] | [:material-information-outline:{ .lg title="ArrayTrailingComma" }](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html) | [:material-information-outline:{ .lg title="TrailingComma" }](https://codenarc.org/codenarc-rules-convention.html#trailingcomma-rule) | | :material-check:{ .lg } | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/) | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/)
Trailing comma in declaration[^kotlin-trailing-commas] | | | [:material-information-outline:{ .lg title="trailing-comma-on-declaration-site" }](https://pinterest.github.io/ktlint/latest/rules/standard/#trailing-comma-on-declaration-site) | :material-check:{ .lg } | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/) | [:material-information-outline:{ .lg title="comma-dangle" }](https://eslint.style/rules/comma-dangle/)
Unused import[^remove-unused-imports] | [:material-information-outline:{ .lg title="UnusedImports" }](https://checkstyle.sourceforge.io/checks/imports/unusedimports.html) | [:material-information-outline:{ .lg title="UnusedImport" }](https://checkstyle.sourceforge.io/checks/coding/unusedimport.html) | [:material-information-outline:{ .lg title="no-unused-imports" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-unused-imports) | [:material-information-outline:{ .lg title="unused-import" }](https://pylint.pycqa.org/en/latest/messages/warning/unused-import.html) | [:material-information-outline:{ .lg title="no-unused-vars" }](https://eslint.org/docs/latest/rules/no-unused-vars/) | [:material-information-outline:{ .lg title="no-unused-vars" }](https://eslint.org/docs/latest/rules/no-unused-vars/)
Wildcard import[^google-import-statements] | [:material-information-outline:{ .lg title="AvoidStarImport" }](https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html) | [:material-information-outline:{ .lg title="NoWildcard=Imports" }](https://codenarc.org/codenarc-rules-imports.html#nowildcardimports-rule) | [:material-information-outline:{ .lg title="no-wildcard-imports" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-wildcard-imports) | [:material-information-outline:{ .lg title="wildcard-import" }](https://pylint.pycqa.org/en/latest/messages/warning/wildcard-import.html) | :material-check-all:{ .lg } | :material-check:{ .lg }
<span class="material-symbols-outlined lg middle">format_text_clip</span> **Clipping group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Empty braces clip[^google-braces-empty-blocks] | [:material-information-outline:{ .lg title="RegexpSinglelineJava" }](https://checkstyle.org/checks/regexp/regexpsinglelinejava.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="object-curly-spacing" }](https://eslint.style/rules/object-curly-spacing/) | [:material-information-outline:{ .lg title="object-curly-spacing" }](https://eslint.style/rules/object-curly-spacing/)
Empty brackets clip[^concise-brackets-parentheses-and-tags] | | :material-check-all:{ .lg } | | :material-check:{ .lg } | [:material-information-outline:{ .lg title="array-bracket-spacing" }](https://eslint.style/rules/array-bracket-spacing/) | [:material-information-outline:{ .lg title="array-bracket-spacing" }](https://eslint.style/rules/array-bracket-spacing/)
Empty parentheses clip[^concise-brackets-parentheses-and-tags] | [:material-information-outline:{ .lg title="RegexpMultiline" }](https://checkstyle.org/checks/regexp/regexpmultiline.html) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="parameter-list-spacing" }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-spacing) | :material-check:{ .lg } | [:material-information-outline:{ .lg title="space-in-parens" }](https://eslint.style/rules/space-in-parens/) | [:material-information-outline:{ .lg title="space-in-parens" }](https://eslint.style/rules/space-in-parens/)
Empty tags clip[^concise-brackets-parentheses-and-tags] | :material-check:{ .lg } | :material-check-all:{ .lg }
Short block comment clip[^kotlin-documentation-comments] | [:material-information-outline:{ .lg title="SinglelineJavadoc" }](https://checkstyle.sourceforge.io/checks/javadoc/singlelinejavadoc.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="multiline-blocks" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/multilineBlocks.js) | [:material-information-outline:{ .lg title="multiline-blocks" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/multilineBlocks.js)
<span class="material-symbols-outlined lg middle">data_object</span> **Declaring group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Abstract class definition[^common-abstract-class-require-abstract-method] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="AbstractClassWithoutAbstractMethod" }](https://codenarc.org/codenarc-rules-design.html#abstractclasswithoutabstractmethod-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | :material-check-all:{ .lg }
Contract function definition[^kotlin-kotlin-contracts] | | | :material-check-all:{ .lg }
Exception inheritance[^oracle-throwing] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="ExceptionExtendsError" }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendserror-rule) [:material-information-outline:{ .lg title="ExceptionExtendsThrowable" }](https://codenarc.org/codenarc-rules-exceptions.html#exceptionextendsthrowable-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } |
Number suffix for double[^groovy-number-type-suffixes] | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Number suffix for float[^groovy-number-type-suffixes] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg }
Number suffix for integer[^groovy-number-type-suffixes] | | :material-check-all:{ .lg }
Number suffix for long[^groovy-number-type-suffixes] | [:material-information-outline:{ .lg title="UpperEll" }](https://checkstyle.sourceforge.io/checks/misc/upperell.html) | [:material-information-outline:{ .lg title="LongLiteralwithLowercaseL" }](https://codenarc.org/codenarc-rules-convention.html#longliteralwithlowercasel-rule)
String quotes[^google-features-strings-use-single-quotes] | | [:material-information-outline:{ .lg title="UnnecessaryGString" }](https://codenarc.org/codenarc-rules-unnecessary.html#unnecessarygstring-rule) | | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="quotes" }](https://eslint.style/rules/quotes/) | [:material-information-outline:{ .lg title="quotes" }](https://eslint.style/rules/quotes/)
Unnecessary parentheses in lambda[^oracle-lambdaexpressions] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | | [:material-information-outline:{ .lg title="arrow-parens" }](https://eslint.style/rules/arrow-parens/) | [:material-information-outline:{ .lg title="arrow-parens" }](https://eslint.style/rules/arrow-parens/)
Utility class definition[^hide-utility-class-instance] | [:material-information-outline:{ .lg title="HideUtilityClassConstructor" }](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html) | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">draft</span> **Formatting group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
File size[^kotlin-source-file-organization] | [:material-information-outline:{ .lg title="FileLength" }](https://checkstyle.sourceforge.io/checks/sizes/filelength.html) | [:material-information-outline:{ .lg title="ClassSize" }](https://codenarc.org/codenarc-rules-size.html#classsize-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="max-lines" }](https://eslint.org/docs/latest/rules/max-lines) | [:material-information-outline:{ .lg title="max-lines" }](https://eslint.org/docs/latest/rules/max-lines)
Final newline[^editorconfig-file-format-details] | [:material-information-outline:{ .lg title="NewlineAtEndOfFile" }](https://checkstyle.sourceforge.io/checks/misc/newlineatendoffile.html) | [:material-information-outline:{ .lg title="FileEndsWithoutNewline" }](https://codenarc.org/codenarc-rules-formatting.html#fileendswithoutnewline-rule) | [:material-information-outline:{ .lg title="final-newline" }](https://pinterest.github.io/ktlint/latest/rules/standard/#final-newline) | [:material-information-outline:{ .lg title="missing-final-newline" }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/missing-final-newline.html) | [:material-information-outline:{ .lg title="eol-last" }](https://eslint.style/rules/eol-last/) | [:material-information-outline:{ .lg title="eol-last" }](https://eslint.style/rules/eol-last/)
Indent style[^editorconfig-file-format-details] | [:material-information-outline:{ .lg title="FileTabCharacter" }](https://checkstyle.sourceforge.io/checks/whitespace/filetabcharacter.html) | [:material-information-outline:{ .lg title="NoTabCharacter" }](https://codenarc.org/codenarc-rules-convention.html#notabcharacter-rule) | [:material-information-outline:{ .lg title="indentation" }](https://pinterest.github.io/ktlint/latest/rules/standard/#indentation) | [:material-information-outline:{ .lg title="bad-indentation" }](https://pylint.pycqa.org/en/latest/messages/warning/bad-indentation.html) | [:material-information-outline:{ .lg title="indent" }](https://eslint.style/rules/indent/) | [:material-information-outline:{ .lg title="indent" }](https://eslint.style/rules/indent/)
Line length[^google-column-limit] | [:material-information-outline:{ .lg title="LineLength" }](https://checkstyle.sourceforge.io/checks/sizes/linelength.html) | [:material-information-outline:{ .lg title="LineLength" }](https://codenarc.org/codenarc-rules-formatting.html#linelength-rule) | [:material-information-outline:{ .lg title="max-line-length" }](https://pinterest.github.io/ktlint/latest/rules/standard/#max-line-length) | [:material-information-outline:{ .lg title="line-too-long" }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/line-too-long.html) | [:material-information-outline:{ .lg title="max-len" }](https://eslint.style/rules/max-len/) | [:material-information-outline:{ .lg title="max-len" }](https://eslint.style/rules/max-len/)
<span class="material-symbols-outlined lg middle">label</span> **Naming group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Class name abbreviation[^kotlin-choose-good-names] | [:material-information-outline:{ .lg title="AbbreviationAsWordInName" }](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-close:{ .lg } | [:material-information-outline:{ .lg title="naming-convention" }](https://typescript-eslint.io/rules/naming-convention/)
Class name[^oracle-codeconventions-namingconventions] | [:material-information-outline:{ .lg title="TypeName" }](https://checkstyle.sourceforge.io/checks/naming/typename.html) | [:material-information-outline:{ .lg title="ClassName" }](https://codenarc.org/codenarc-rules-naming.html#classname-rule) [:material-information-outline:{ .lg title="InterfaceName" }](https://codenarc.org/codenarc-rules-naming.html#interfacename-rule) | [:material-information-outline:{ .lg title="class-naming" }](https://pinterest.github.io/ktlint/latest/rules/standard/#class-naming) | [:material-information-outline:{ .lg title="invalid-name" }](https://pylint.pycqa.org/en/latest/messages/convention/invalid-name.html) | :material-close:{ .lg } | [:material-information-outline:{ .lg title="naming-convention" }](https://typescript-eslint.io/rules/naming-convention/)
Constant property name[^oracle-codeconventions-namingconventions] | [:material-information-outline:{ .lg title="ConstantName" }](https://checkstyle.sourceforge.io/checks/naming/constantname.html) | [:material-information-outline:{ .lg title="FieldName" }](https://codenarc.org/codenarc-rules-naming.html#fieldname-rule) [:material-information-outline:{ .lg title="PropertyName" }](https://codenarc.org/codenarc-rules-naming.html#propertyname-rule) | [:material-information-outline:{ .lg title="property-naming" }](https://pinterest.github.io/ktlint/latest/rules/standard/#property-naming)
File name[^kotlin-source-file-names] | [:material-information-outline:{ .lg title="OuterTypeFilename" }](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html) | [:material-information-outline:{ .lg title="ClassNameSameAsFilename" }](https://codenarc.org/codenarc-rules-naming.html#classnamesameasfilename-rule) | [:material-information-outline:{ .lg title="file-name" }](https://pinterest.github.io/ktlint/latest/rules/standard/#file-name) | | :material-help:{ .lg } | :material-help:{ .lg }
Identifier name[^oracle-codeconventions-namingconventions] | [:material-information-outline:{ .lg title="CatchParameterName" }](https://checkstyle.sourceforge.io/checks/naming/catchparametername.html) [:material-information-outline:{ .lg title="LambdaParameterName" }](https://checkstyle.sourceforge.io/checks/naming/lambdaparametername.html) [:material-information-outline:{ .lg title="LocalVariableName" }](https://checkstyle.sourceforge.io/checks/naming/localvariablename.html) [:material-information-outline:{ .lg title="LocalFinalVariableName" }](https://checkstyle.sourceforge.io/checks/naming/localfinalvariablename.html) [:material-information-outline:{ .lg title="MemberName" }](https://checkstyle.sourceforge.io/checks/naming/membername.html) [:material-information-outline:{ .lg title="MethodName" }](https://checkstyle.sourceforge.io/checks/naming/methodname.html) [:material-information-outline:{ .lg title="ParameterName" }](https://checkstyle.sourceforge.io/checks/naming/parametername.html) [:material-information-outline:{ .lg title="PatternVariableName" }](https://checkstyle.sourceforge.io/checks/naming/patternvariablename.html) [:material-information-outline:{ .lg title="RecordComponentName" }](https://checkstyle.sourceforge.io/checks/naming/recordcomponentname.html) [:material-information-outline:{ .lg title="StaticVariableName" }](https://checkstyle.sourceforge.io/checks/naming/staticvariablename.html) | [:material-information-outline:{ .lg title="MethodName" }](https://codenarc.org/codenarc-rules-naming.html#methodname-rule) [:material-information-outline:{ .lg title="ParameterName" }](https://codenarc.org/codenarc-rules-naming.html#parametername-rule) [:material-information-outline:{ .lg title="VariableName" }](https://codenarc.org/codenarc-rules-naming.html#variablename-rule) | [:material-information-outline:{ .lg title="function-naming" }](https://pinterest.github.io/ktlint/latest/rules/standard/#function-naming) | :material-check:{ .lg } | :material-close:{ .lg } | [:material-information-outline:{ .lg title="naming-convention" }](https://typescript-eslint.io/rules/naming-convention/)
Illegal class name suffix[^kotlin-choose-good-names] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Illegal variable name[^avoid-primitive-names] | [:material-information-outline:{ .lg title="IllegalIdentifierName" }](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="disallowed-name" }](https://pylint.pycqa.org/en/latest/messages/convention/disallowed-name.html) | [:material-information-outline:{ .lg title="id-denylist" }](https://eslint.org/docs/latest/rules/id-denylist/) | [:material-information-outline:{ .lg title="id-denylist" }](https://eslint.org/docs/latest/rules/id-denylist/)
Package name[^oracle-codeconventions-namingconventions] | [:material-information-outline:{ .lg title="PackageName" }](https://checkstyle.sourceforge.io/checks/naming/packagename.html) | [:material-information-outline:{ .lg title="PackageName" }](https://codenarc.org/codenarc-rules-naming.html#packagename-rule) | [:material-information-outline:{ .lg title="package-name" }](https://pinterest.github.io/ktlint/latest/rules/standard/#package-name) | :material-check:{ .lg }
Property name interop[^kotlin-properties] | | | :material-check-all:{ .lg }
Required generics name[^oracle-types] | [:material-information-outline:{ .lg title="ClassTypeParameterName" }](https://checkstyle.sourceforge.io/checks/naming/classtypeparametername.html) [:material-information-outline:{ .lg title="InterfaceTypeParameterName" }](https://checkstyle.sourceforge.io/checks/naming/interfacetypeparametername.html) [:material-information-outline:{ .lg title="MethodTypeParameterName" }](https://checkstyle.sourceforge.io/checks/naming/methodtypeparametername.html) [:material-information-outline:{ .lg title="RecordTypeParameterName" }](https://checkstyle.sourceforge.io/checks/naming/recordtypeparametername.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
<span class="material-symbols-outlined lg middle">swap_vert</span> **Ordering group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Block tag order[^android-block-tags] | [:material-information-outline:{ .lg title="AtClauseOrder" }](https://checkstyle.sourceforge.io/checks/javadoc/atclauseorder.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | [:material-information-outline:{ .lg title="sort-tags" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/sortTags.js) | [:material-information-outline:{ .lg title="sort-tags" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/sortTags.js)
Built-in function position[^openjdk-feedback] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/) | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/)
Import order[^google-import-ordering-and-spacing] | [:material-information-outline:{ .lg title="CustomImportOrder" }](https://checkstyle.sourceforge.io/checks/imports/customimportorder.html) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="import-ordering" }](https://pinterest.github.io/ktlint/latest/rules/standard/#import-ordering) | [:material-information-outline:{ .lg title="wrong-import-order" }](https://pylint.pycqa.org/en/latest/user_guide/messages/convention/wrong-import-order.html) | [:material-information-outline:{ .lg title="sort-imports" }](https://eslint.org/docs/latest/rules/sort-imports/) | [:material-information-outline:{ .lg title="sort-imports" }](https://eslint.org/docs/latest/rules/sort-imports/)
Inner class position[^kotlin-class-layout] | [:material-information-outline:{ .lg title="InnerTypeLast" }](https://checkstyle.sourceforge.io/checks/design/innertypelast.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/) | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/)
Member order[^kotlin-class-layout] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/) | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/)
Overload function position[^kotlin-overload-layout] | [:material-information-outline:{ .lg title="OverloadMethodsDeclarationOrder" }](https://checkstyle.sourceforge.io/checks/coding/overloadmethodsdeclarationorder.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/) | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/)
Static import position[^openjdk-feedback] | :material-check:{ .lg } | [:material-information-outline:{ .lg title="MisorderedStaticImports" }](https://codenarc.org/codenarc-rules-imports.html#misorderedstaticimports-rule) | | | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/) | [:material-information-outline:{ .lg title="sort-class-members" }](https://www.npmjs.com/package/eslint-plugin-sort-class-members/)
<span class="material-symbols-outlined lg middle">format_letter_spacing_2</span> **Spacing group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Block comment spaces[^oracle-javadoc] | [:material-information-outline:{ .lg title="JavadocMissingWhitespaceAfterAsterisk" }](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingwhitespaceafterasterisk.html) | [:material-information-outline:{ .lg title="SpaceAfterCommentDelimiter" }](https://codenarc.org/codenarc-rules-comments.html#spaceaftercommentdelimiter-rule) [:material-information-outline:{ .lg title="SpaceBeforeCommentDelimiter" }](https://codenarc.org/codenarc-rules-comments.html#spacebeforecommentdelimiter-rule) | :material-check-all:{ .lg } | | [:material-information-outline:{ .lg title="spaced-comment" }](https://eslint.style/rules/spaced-comment/) | [:material-information-outline:{ .lg title="spaced-comment" }](https://eslint.style/rules/spaced-comment/)
Block tag indentation[^google-javadoc-block-tags] | [:material-information-outline:{ .lg title="JavadocTagContinuationIndentation" }](https://checkstyle.sourceforge.io/checks/javadoc/javadoctagcontinuationindentation.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | [:material-information-outline:{ .lg title="check-indentation" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/checkIndentation.js) | [:material-information-outline:{ .lg title="check-indentation" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/checkIndentation.js)
Case separator[^kotlin-control-flow-statements] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="blank-line-between-when-conditions" }](https://pinterest.github.io/ktlint/latest/rules/standard/#blank-line-between-when-conditions) | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Comment space[^android-horizontal] | :material-check-all:{ .lg } | :material-check:{ .lg } | [:material-information-outline:{ .lg title="comment-spacing" }](https://pinterest.github.io/ktlint/latest/rules/standard/#comment-spacing) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="spaced-comment" }](https://eslint.style/rules/spaced-comment/) | [:material-information-outline:{ .lg title="spaced-comment" }](https://eslint.style/rules/spaced-comment/)
Member separator[^google-vertical-whitespace] | [:material-information-outline:{ .lg title="EmptyLineSeparator" }](https://checkstyle.sourceforge.io/checks/whitespace/emptylineseparator.html) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="blank-line-before-declarations" }](https://pinterest.github.io/ktlint/latest/rules/standard/#blank-line-before-declarations) | :material-check-all:{ .lg } | :material-close:{ .lg } | [:material-information-outline:{ .lg title="lines-between-class-members" }](https://typescript-eslint.io/rules/lines-between-class-members/)
Missing blank line before block tags[^kotlin-kdoc-syntax] | [:material-information-outline:{ .lg title="RequireEmptyLineBeforeBlockTagGroup" }](https://checkstyle.sourceforge.io/checks/javadoc/requireemptylinebeforeblocktaggroup.html) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | [:material-information-outline:{ .lg title="tag-lines" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/tagLines.js) | [:material-information-outline:{ .lg title="tag-lines" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/tagLines.js)
<span class="material-symbols-outlined lg middle">code_blocks</span> **Stating group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Illegal catch[^oracle-throwing] | [:material-information-outline:{ .lg title="IllegalCatch" }](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html) | [:material-information-outline:{ .lg title="CatchError" }](https://codenarc.org/codenarc-rules-exceptions.html#catcherror-rule) [:material-information-outline:{ .lg title="CatchException" }](https://codenarc.org/codenarc-rules-exceptions.html#catchexception-rule) [:material-information-outline:{ .lg title="CatchThrowable" }](https://codenarc.org/codenarc-rules-exceptions.html#catchthrowable-rule) | [:material-close:{ .lg title="Does Kotlin have multi-catch?" }](https://discuss.kotlinlang.org/t/does-kotlin-have-multi-catch/486/) | [:material-information-outline:{ .lg title="bare-except" }](https://pylint.pycqa.org/en/latest/user_guide/messages/warning/bare-except.html)
Illegal throw[^oracle-throwing] | [:material-information-outline:{ .lg title="IllegalThrows" }](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html) | [:material-information-outline:{ .lg title="ThrowError" }](https://codenarc.org/codenarc-rules-exceptions.html#throwerror-rule) [:material-information-outline:{ .lg title="ThrowException" }](https://codenarc.org/codenarc-rules-exceptions.html#throwexception-rule) [:material-information-outline:{ .lg title="ThrowThrowable" }](https://codenarc.org/codenarc-rules-exceptions.html#throwthrowable-rule) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="broad-exception-caught" }](https://pylint.pycqa.org/en/latest/user_guide/messages/warning/broad-exception-caught.html)
Missing braces[^android-braces] | [:material-information-outline:{ .lg title="NeedBraces" }](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html) | [:material-information-outline:{ .lg title="ElseStatementBraces" }](https://codenarc.org/codenarc-rules-braces.html#elsestatementbraces-rule) [:material-information-outline:{ .lg title="ForStatementBraces" }](https://codenarc.org/codenarc-rules-braces.html#forstatementbraces-rule) [:material-information-outline:{ .lg title="IfStatementBraces" }](https://codenarc.org/codenarc-rules-braces.html#ifstatementbraces-rule) [:material-information-outline:{ .lg title="WhileStatementBraces" }](https://codenarc.org/codenarc-rules-braces.html#whilestatementbraces-rule) | [:material-information-outline:{ .lg title="multiline-if-else" }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-if-else) [:material-information-outline:{ .lg title="multiline-loop" }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-loop) | | [:material-information-outline:{ .lg title="curly" }](https://eslint.org/docs/latest/rules/curly/) | [:material-information-outline:{ .lg title="curly" }](https://eslint.org/docs/latest/rules/curly/)
Nested if-else[^jetbrains-invert-if] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Redundant default[^jetbrains-redundant-if-else-block] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-close:{ .lg } | [:material-information-outline:{ .lg title="switch-exhaustiveness-check" }](https://typescript-eslint.io/rules/switch-exhaustiveness-check/)
Redundant else[^jetbrains-redundant-if-else-block] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-else-raise" }](https://pylint.pycqa.org/en/latest/user_guide/messages/refactor/no-else-raise.html) [:material-information-outline:{ .lg title="no-else-return" }](https://pylint.pycqa.org/en/latest/user_guide/messages/refactor/no-else-return.html) | [:material-information-outline:{ .lg title="no-else-return" }](https://eslint.org/docs/latest/rules/no-else-return/) | [:material-information-outline:{ .lg title="no-else-return" }](https://eslint.org/docs/latest/rules/no-else-return/)
Unnecessary switch[^jetbrains-switch-statement-with-too-few-branches] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-restricted-syntax" }](https://eslint.org/docs/latest/rules/no-restricted-syntax/) | [:material-information-outline:{ .lg title="no-restricted-syntax" }](https://eslint.org/docs/latest/rules/no-restricted-syntax/)
<span class="material-symbols-outlined lg middle">format_letter_spacing_standard</span> **Trimming group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Block comment trim[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="JavadocEmptyFirstLine" }](https://codenarc.org/codenarc-rules-comments.html#javadocemptyfirstline-rule) [:material-information-outline:{ .lg title="JavadocEmptyLastLine" }](https://codenarc.org/codenarc-rules-comments.html#javadocemptylastline-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-blank-block-descriptions" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/noBlankBlockDescriptions.js) | [:material-information-outline:{ .lg title="no-blank-block-descriptions" }](https://github.com/gajus/eslint-plugin-jsdoc/blob/main/src/rules/noBlankBlockDescriptions.js)
Braces trim[^google-blocks-k-r-style] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="BlockEndsWithBlankLine" }](https://codenarc.org/codenarc-rules-formatting.html#blockendswithblankline-rule) [:material-information-outline:{ .lg title="BlockStartsWithBlankLine" }](https://codenarc.org/codenarc-rules-formatting.html#blockstartswithblankline-rule) [:material-information-outline:{ .lg title="ClassEndsWithBlankLine" }](https://codenarc.org/codenarc-rules-formatting.html#classendswithblankline-rule) [:material-information-outline:{ .lg title="ClassStartsWithBlankLine" }](https://codenarc.org/codenarc-rules-formatting.html#classstartswithblankline-rule) | [:material-information-outline:{ .lg title="no-empty-first-line-at-start-in-class-body" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-empty-first-line-at-start-in-class-body) [:material-information-outline:{ .lg title="no-blank-lines-before" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-before) | :material-check:{ .lg } | [:material-information-outline:{ .lg title="padded-blocks" }](https://eslint.style/rules/padded-blocks/) | [:material-information-outline:{ .lg title="padded-blocks" }](https://eslint.style/rules/padded-blocks/)
Brackets trim[^trim-multiline-brackets-parentheses-and-tags] | | :material-check-all:{ .lg } | | :material-check:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Comment trim[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Duplicate blank line[^google-vertical-whitespace] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="ConsecutiveBlankLines" }](https://codenarc.org/codenarc-rules-formatting.html#consecutiveblanklines-rule) | [:material-information-outline:{ .lg title="no-consecutive-blank-lines" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-consecutive-blank-lines) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-multiple-empty-lines" }](https://eslint.style/rules/no-multiple-empty-lines/) | [:material-information-outline:{ .lg title="no-multiple-empty-lines" }](https://eslint.style/rules/no-multiple-empty-lines/)
Duplicate blank line in block comment[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="JavadocConsecutiveEmptyLines" }](https://codenarc.org/codenarc-rules-comments.html#javadocconsecutiveemptylines-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Duplicate blank line in comment[^no-blank-lines-at-initial-final-and-consecutive-comments] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Duplicate space[^kotlin-horizontal-whitespace] | [:material-information-outline:{ .lg title="SingleSpaceSeparator" }](https://checkstyle.sourceforge.io/checks/whitespace/singlespaceseparator.html) | :material-close:{ .lg } | [:material-information-outline:{ .lg title="no-multi-spaces" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-multi-spaces) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-multi-spaces" }](https://eslint.style/rules/no-multi-spaces/) | [:material-information-outline:{ .lg title="no-multi-spaces" }](https://eslint.style/rules/no-multi-spaces/)
Parentheses trim[^trim-multiline-brackets-parentheses-and-tags] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="no-blank-lines-in-list" }](https://pinterest.github.io/ktlint/latest/rules/standard/#no-blank-lines-in-list) | :material-check-all:{ .lg } | :material-help:{ .lg } | :material-help:{ .lg }
Tags trim[^trim-multiline-brackets-parentheses-and-tags] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | :material-check:{ .lg } | | :material-help:{ .lg } | :material-help:{ .lg }
Unnecessary blank line after colon[^google-blocks-k-r-style] | | | | :material-check-all:{ .lg }
Unnecessary blank line before package[^google-source-file-structure] | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="BlankLineBeforePackage" }](https://codenarc.org/codenarc-rules-formatting.html#blanklinebeforepackage-rule) | :material-check-all:{ .lg } | :material-check-all:{ .lg }
<span class="material-symbols-outlined lg middle">format_text_wrap</span> **Wrapping group** | **Java** | **Groovy** | **Kotlin** | **Python** | **JavaScript** | **TypeScript**
Assignment wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="multiline-expression-wrapping" }](https://pinterest.github.io/ktlint/latest/rules/standard/#multiline-expression-wrapping) | :material-close:{ .lg } | :material-check-all:{ .lg } | :material-check:{ .lg }
Chain call wrap[^kotlin-wrap-chained-calls] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="chain-method-continuation" }](https://pinterest.github.io/ktlint/latest/rules/standard/#chain-method-continuation) | :material-close:{ .lg } | :material-check-all:{ .lg } | :material-check:{ .lg }
Elvis wrap[^move-trailing-elvis-to-the-next-line] | | | :material-check-all:{ .lg }
Infix call wrap[^android-where-to-break] | | | :material-check-all:{ .lg }
Lambda wrap[^google-line-wrapping-where-to-break] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | | :material-close:{ .lg } | [:material-information-outline:{ .lg title="arrow-body-style" }](https://eslint.org/docs/latest/rules/arrow-body-style/) | [:material-information-outline:{ .lg title="arrow-body-style" }](https://eslint.org/docs/latest/rules/arrow-body-style/)
Operator wrap[^android-where-to-break] | [:material-information-outline:{ .lg title="OperatorWrap" }](https://checkstyle.sourceforge.io/checks/whitespace/operatorwrap.html) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="binary-expression-wrapping" }](https://pinterest.github.io/ktlint/latest/rules/standard/#binary-expression-wrapping) | :material-close:{ .lg } | [:material-information-outline:{ .lg title="operator-linebreak" }](https://eslint.style/rules/operator-linebreak/) | [:material-information-outline:{ .lg title="operator-linebreak" }](https://eslint.style/rules/operator-linebreak/)
Parameter wrap[^kotlin-class-headers] | :material-check-all:{ .lg } | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="argument-list-wrapping" }](https://pinterest.github.io/ktlint/latest/rules/standard/#argument-list-wrapping) [:material-information-outline:{ .lg title="parameter-list-wrapping" }](https://pinterest.github.io/ktlint/latest/rules/standard/#parameter-list-wrapping) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="array-element-newline" }](https://eslint.style/rules/array-element-newline/) [:material-information-outline:{ .lg title="function-call-argument-newline" }](https://eslint.style/rules/function-call-argument-newline/) [:material-information-outline:{ .lg title="function-paren-newline" }](https://eslint.style/rules/function-paren-newline/) | [:material-information-outline:{ .lg title="array-element-newline" }](https://eslint.style/rules/array-element-newline/) [:material-information-outline:{ .lg title="function-call-argument-newline" }](https://eslint.style/rules/function-call-argument-newline/) [:material-information-outline:{ .lg title="function-paren-newline" }](https://eslint.style/rules/function-paren-newline/)
Statement wrap[^oracle-codeconventions-statements] | [:material-information-outline:{ .lg title="OneStatementPerLine" }](https://checkstyle.sourceforge.io/checks/coding/onestatementperline.html) | :material-check-all:{ .lg } | [:material-information-outline:{ .lg title="statement-wrapping" }](https://pinterest.github.io/ktlint/latest/rules/standard/#statement-wrapping) | [:material-information-outline:{ .lg title="multiple-statements" }](https://pylint.readthedocs.io/en/stable/user_guide/messages/convention/multiple-statements.html) | [:material-information-outline:{ .lg title="max-statements-per-line" }](https://eslint.style/rules/max-statements-per-line/) | [:material-information-outline:{ .lg title="max-statements-per-line" }](https://eslint.style/rules/max-statements-per-line/)

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

        Checkstyle is a development tool to help programmers write Java code
        that adheres to a coding standard.

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

        CodeNarc is similar to popular static analysis tools such as PMD or
        Checkstyle.

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
    Get the artifacts from official package managers.

    [Maven Central](https://repo1.maven.org/maven2/com/hanggrian/rulebook/){ .md-button .md-button--primary }&emsp;[PyPI](https://pypi.org/project/rulebook-pylint/){ .md-button }

## FAQ

### Why is it necessary?

When working on a project with multiple programming languages, we often forget
to apply the same coding style and leave the validation to a linter tool.
However, the default behavior of these linters are not always consistent.
Consider the example below:

<table>
  <thead>
    <tr>
      <th>Java</th>
      <th>Groovy</th>
      <th>Kotlin</th>
      <th>Python</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        Java does not allow trailing commas except in array initializers.
      </td>
      <td>
        Groovy allows trailing commas in call sites, but CodeNarc does not
        natively support it.
      </td>
      <td>
        Trailing commas can be placed in call and declaration sites in Kotlin,
        the rule is provided by Ktlint.
      </td>
      <td>
        Python allows trailing commas but Pylint considers it optional in PEP.
        <i>Note that the comment spacing rule is different in Python.</i>
      </td>
    </tr>
    <tr>
      <td>
```java
void foo(
    int a,
    int b
) {
    bar(
        a,
        b
    )
}
```
      </td>
      <td>
```groovy
def foo(
    int a,
    int b
) {
    bar(
        a,
        b // no!
    )
}
```
      </td>
      <td>
```kotlin
fun foo(
    a: Int,
    b: Int // no!
) =
    bar(
        a,
        b // no!
    )
```
      </td>
      <td>
        <pre>
```python
def foo(
    a: int,
    b: int  # no!
):
    bar(
        a,
        b  # no!
    )
```
      </td>
    </tr>
  </tbody>
</table>

### How stable is it?

The rules are mostly work in progress and have not been tested against a large
codebase. Disable the rules individually if they behave unexpectedly.

=== "checkstyle.xml"
    ```xml
    <!--module name="CommentSpaces"/-->
    ```
=== "codenarc.xml"
    ```xml
    <!--rule class="com.hanggrian.rulebook.codenarc.CommentSpacesRule"/-->
    ```
=== ".editorconfig"
    ```ini
    ktlint_rulebook_comment-spaces = disabled
    ```
=== "pylintrc"
    ```python
    # rulebook_pylint.comment_spaces,
    ```

### What's next?

Although there is no timeline for the roadmap, more languages are planned:

- [X] Java
- [X] Groovy
- [X] Kotlin
- [X] Python
- [ ] JavaScript
- [ ] TypeScript

[^common-abstract-class-require-abstract-method]: [Abstract class require abstract method](rationales/index.md#abstract-class-require-abstract-method)
[^avoid-primitive-names]: [Avoid primitive names](rationales/index.md#avoid-primitive-names)
[^concise-brackets-parentheses-and-tags]: [Concise brackets, parentheses and tags](rationales/index.md#concise-brackets-parentheses-and-tags)
[^hide-utility-class-instance]: [Hide utility class instance](rationales/index.md#hide-utility-class-instance)
[^move-trailing-elvis-to-the-next-line]: [Move trailing elvis to the next line](rationales/index.md#move-trailing-elvis-to-the-next-line)
[^no-blank-lines-at-initial-final-and-consecutive-comments]: [No blank lines at initial, final and consecutive comments](rationales/index.md#no-blank-lines-at-initial-final-and-consecutive-comments)
[^remove-unused-imports]: [Remove unused imports](rationales/index.md#remove-unused-imports)
[^trim-multiline-brackets-parentheses-and-tags]: [Trim multiline brackets, parentheses and tags](rationales/index.md#trim-multiline-brackets-parentheses-and-tags)
[^android-block-tags]: :simple-android:{ #android .lg .middle } [Android: Block tags :material-open-in-new:](https://developer.android.com/kotlin/style-guide#block_tags)
[^android-braces]: :simple-android:{ #android .lg .middle } [Android: Braces :material-open-in-new:](https://developer.android.com/kotlin/style-guide#braces)
[^android-horizontal]: :simple-android:{ #android .lg .middle } [Android: Horizontal whitespace :material-open-in-new:](https://developer.android.com/kotlin/style-guide#horizontal)
[^android-where-to-break]: :simple-android:{ #android .lg .middle } [Android: Where to break :material-open-in-new:](https://developer.android.com/kotlin/style-guide#where_to_break)
[^editorconfig-file-format-details]: :simple-editorconfig:{ #light .lg .middle } [EditorConfig: What's an EditorConfig file look like? :material-open-in-new:](https://editorconfig.org/#file-format-details)
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
[^jetbrains-invert-if]: :simple-jetbrains:{ #dark .lg .middle } [JetBrains: Invert if statement :material-open-in-new:](https://www.jetbrains.com/help/resharper/InvertIf.html)
[^jetbrains-redundant-if-else-block]: :simple-jetbrains:{ #dark .lg .middle } [JetBrains: Redundant else keyword :material-open-in-new:](https://www.jetbrains.com/help/resharper/RedundantIfElseBlock.html)
[^jetbrains-remove-redundant-qualifier-name]: :simple-jetbrains:{ #dark .lg .middle } [JetBrains: Redundant qualifier name :material-open-in-new:](https://www.jetbrains.com/help/inspectopedia/RemoveRedundantQualifierName.html)
[^jetbrains-switch-statement-with-too-few-branches]: :simple-jetbrains:{ #dark .lg .middle } [JetBrains: Minimum switch branches :material-open-in-new:](https://www.jetbrains.com/help/inspectopedia/SwitchStatementWithTooFewBranches.html)
[^jetbrains-using-todo]: :simple-jetbrains:{ #dark .lg .middle } [JetBrains: TODO comments :material-open-in-new:](https://www.jetbrains.com/help/idea/using-todo.html)
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
[^openjdk-feedback]: :simple-openjdk:{ #openjdk .lg .middle } [OpenJDK: Java Style Guidelines feedback :material-open-in-new:](https://cr.openjdk.org/~alundblad/styleguide/feedback.html)
[^oracle-codeconventions-namingconventions]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Naming conventions :material-open-in-new:](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
[^oracle-codeconventions-statements]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Simple statements :material-open-in-new:](https://www.oracle.com/java/technologies/javase/codeconventions-statements.html)
[^oracle-javadoc]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Javadoc reference :material-open-in-new:](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)
[^oracle-javadoc-tool]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Javadoc tool :material-open-in-new:](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
[^oracle-lambdaexpressions]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Lambda expressions :material-open-in-new:](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
[^oracle-throwing]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: How to throw exceptions :material-open-in-new:](https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html)
[^oracle-types]: :fontawesome-brands-java:{ #oracle .lg .middle } [Oracle: Generic types :material-open-in-new:](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
[^python-when-to-use-trailing-commas]: :simple-python:{ #python .lg .middle } [Python: When to use trailing commas :material-open-in-new:](https://peps.python.org/pep-0008/#when-to-use-trailing-commas)

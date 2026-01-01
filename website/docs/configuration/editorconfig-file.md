Configuration starts with standard preset from [EditorConfig Wiki](https://github.com/editorconfig/editorconfig/wiki/EditorConfig-Properties).
Then, apply code snippets from sub-section according to which lint tools.

```ini title="Base configuration"
root = true

[*]
indent_style = space
indent_size = 2
end_of_line = lf
charset = utf-8
trim_trailing_whitespace = true
insert_final_newline = true
```

## Kotlin configuration

```ini title="Ktlint Official Style"
[*.kt]
indent_size = 4
max_line_length = 100
ktlint_code_style = ktlint_official
ktlint_standard = enabled
ij_kotlin_packages_to_use_import_on_demand = unset
ij_kotlin_name_count_to_use_star_import = 99
ij_kotlin_name_count_to_use_star_import_for_members = 99
ktlint_class_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than = unset
ktlint_function_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than = unset
ktlint_chain_method_rule_force_multiline_when_chain_operator_count_greater_or_equal_than = unset
ktlint_standard_when-entry-bracing = disabled
```

## Java configuration

```ini title="Sun Java Style"
[*.java]
indent_size = 4
max_line_length = 100
ij_java_use_single_class_imports = true
ij_java_class_count_to_use_import_on_demand = 99
ij_java_names_count_to_use_import_on_demand = 99
```

```ini title="Google Java Style"
[*.java]
indent_size = 4
max_line_length = 100
ij_java_use_single_class_imports = true
ij_java_class_count_to_use_import_on_demand = 99
ij_java_names_count_to_use_import_on_demand = 99
```

## Groovy configuration

```ini title="Groovy Style"
[*.groovy]
indent_size = 4
max_line_length = 100
ij_groovy_use_single_class_imports = true
ij_groovy_class_count_to_use_import_on_demand = 99
ij_groovy_names_count_to_use_import_on_demand = 99
```

## Python configuration

```ini title="Pylint Style and Google Python Style"
[*.groovy]
indent_size = 4
max_line_length = 100
```

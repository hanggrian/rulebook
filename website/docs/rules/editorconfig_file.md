Configuration starts with standard preset from [EditorConfig Wiki](https://github.com/editorconfig/editorconfig/wiki/EditorConfig-Properties).
Then, apply code snippets from sub-section according to which lint tools.

```ini
root = true

[*]
indent_style = space
indent_size = 2
end_of_line = lf
charset = utf-8
trim_trailing_whitespace = true
insert_final_newline = true
```

## Ktlint Official Style

```ini
[*.kt]
indent_size = 4
max_line_length = 100
ij_kotlin_code_style_defaults = KOTLIN_OFFICIAL
ij_kotlin_packages_to_use_import_on_demand = unset
ij_kotlin_name_count_to_use_star_import = 99
ij_kotlin_name_count_to_use_star_import_for_members = 99
ktlint_class_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than = unset
ktlint_function_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than = unset
ktlint_chain_method_rule_force_multiline_when_chain_operator_count_greater_or_equal_than = unset
```

## Sun Java Style

```ini
[*.java]
indent_size = 4
max_line_length = 100
ij_java_use_single_class_imports = true
ij_java_class_count_to_use_import_on_demand = 99
ij_java_names_count_to_use_import_on_demand = 99
```

## Groovy Style

```ini
[*.groovy]
indent_size = 4
max_line_length = 100
ij_groovy_use_single_class_imports = true
ij_groovy_class_count_to_use_import_on_demand = 99
ij_groovy_names_count_to_use_import_on_demand = 99
```

## Python Style

```ini
[*.groovy]
indent_size = 4
max_line_length = 100
```

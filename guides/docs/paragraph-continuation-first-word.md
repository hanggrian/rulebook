# Paragraph Continuation First Word

Main content of documentation uses markdown style and formatting. A first word of every new markdown
line (continuation of paragraph) cannot be a **link** or **code**. However, a fenced code block is
allowed.

```markdown
Lorem Ipsum is simply dummy text of the
[printing] and
`typesetting` industry. (violation)

Lorem Ipsum is simply dummy text of
the [printing]
and `typesetting` industry. (success)
```

This is a default behavior of markdown editor on *IntelliJ* IDEs.

# Lexical Specification of SPL'

## Operators
These are all regexes that match exactly the strings below:

- `\+`
- `-`
- `\*`
- `/`
- `=`
- `==`
- `!=`
- `>`
- `<`
- `>=`
- `<=`
- `!`

## Special Characters
These are all regexes that match exactly the strings below:

- `;`
- `\(`
- `\)`
- `\{`
- `\}`

## Keywords
These are all regexes that match exactly the strings below:

- `true`
- `false`
- `and`
- `or`
- `var`
- `print`
- `if`
- `else`
- `while`

## Literals
### Strings

- `\"[^"]*\"`

TODO: escape sequences?

### Numbers

- `[0-9]+(\.[0-9]+)?`

## Identifiers

- `[a-zA-Z][a-zA-Z0-9]*`
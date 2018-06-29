# KURI

KURI (kotlin uri) is intended to make and manage uri easily.

## Usage

### Path

You can make paths by using kotlin DSL.

```kotlin
with(KPath) {
  "parent" / "child" / "grandchild"

  "parent" / {
    - "child1"
    - "child2" / "grandchild1"
    - "child3" / {
      - "grandchild2"
      - "grandchild3"
    }
  }
}
```
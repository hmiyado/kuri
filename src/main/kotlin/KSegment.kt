sealed class KSegment {

    abstract override fun toString(): String

    class Constant(private val string: String) : KSegment() {
        init {
            assert(valid(string))
        }


        override fun toString(): String = string

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Constant

            if (string != other.string) return false

            return true
        }

        override fun hashCode(): Int {
            return string.hashCode()
        }

        companion object {
            fun valid(string: String): Boolean {
                return !string.contains("/")
            }
        }
    }

    class Variable<T>(private val id: String) : KSegment() {
        init {
            assert(valid(id))
        }

        fun assign(value: T) = Constant(value.toString())

        override fun toString(): String {
            return "{$id}"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Variable<*>

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }


        companion object {
            fun valid(id: String): Boolean {
                return id.all {
                    it !in setOf('{', '}', '/')
                }
            }
        }
    }

    object Factory {
        fun create(string: String): KSegment {
            return if (string.isNotEmpty() && string.first() == '{' && string.last() == '}' && Variable.valid(
                    string.substring(
                        1,
                        string.length - 1
                    )
                )
            ) {
                KSegment.Variable<Any>(string.substring(1, string.length - 1))
            } else {
                KSegment.Constant(string)
            }
        }
    }
}
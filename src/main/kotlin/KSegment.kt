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
            return "{:$id}"
        }

        companion object {
            fun valid(id: String): Boolean {
                return id.all {
                    it !in setOf('{', '}', ':', '/')
                }
            }
        }
    }
}
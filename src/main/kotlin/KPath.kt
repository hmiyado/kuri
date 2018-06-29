class KPath(string: String, subPath: KPath? = null) {
    private val string: String
    val subPath: KPath?

    init {
        val splittedPaths = string.split("/")
        this.string = splittedPaths[0]
        if (splittedPaths.size == 1) {
            this.subPath = subPath
        } else {
            val middlePath = KPath(splittedPaths.drop(1).joinToString("/"))
            this.subPath = subPath?.div(middlePath) ?: middlePath
        }
    }

    operator fun div(other: KPath): KPath = if (subPath == null) {
        KPath(string, other)
    } else {
        KPath(string, subPath / other)
    }

    operator fun div(subPathString: String): KPath = if (subPath == null) {
        KPath(string, KPath(subPathString))
    } else {
        KPath(string, subPath / subPathString)
    }

    operator fun div(builder: KNodeBuilder.() -> Unit): KNode {
        return KNodeBuilder(KPath(string, subPath)).apply(builder).build()
    }

    override fun equals(other: Any?): Boolean {
        val kpath = other as? KPath ?: return false
        if (this.string != kpath.string) return false
        if (this.subPath == null && kpath.subPath == null) return true
        return this.subPath == kpath.subPath
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (subPath?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "KPath($string, $subPath)"
    }
}
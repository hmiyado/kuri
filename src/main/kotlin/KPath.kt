class KPath(
    protected val segment: KSegment,
    protected val subPath: KPath? = null
) {

    operator fun div(other: KPath): KPath = KPath(
        segment, when {
            subPath != null -> subPath / other
            else -> other
        }
    )

    operator fun div(subPathString: String): KPath = Factory.create(this, Factory.create(subPathString))

    operator fun div(builder: KNodeBuilder.() -> Unit): KNode {
        return KNodeBuilder(Factory.create(this, subPath)).apply(builder).build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KPath

        if (segment != other.segment) return false
        if (subPath != other.subPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = segment.hashCode()
        result = 31 * result + (subPath?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return this.segment.toString() + (subPath?.let {
            "/" + it.toString()
        } ?: "")
    }

    internal class Builder(
        var string: String = "",
        var subPath: KPath? = null
    ) {
        init {
            val splittedPaths = string.split("/")
            this.string = splittedPaths[0]
            if (splittedPaths.size > 1) {
                val middlePath = Factory.create(splittedPaths.drop(1).joinToString("/"))
                this.subPath = subPath?.div(middlePath) ?: middlePath
            }
        }

        fun build() = KPath(KSegment.Constant(this.string), this.subPath)
    }

    object Factory {
        fun create(string: String, subPath: KPath? = null): KPath = Builder(string, subPath).build()

        fun create(kPath: KPath, subPath: KPath? = null): KPath {
            return KPath(
                kPath.segment, when {
                    kPath.subPath != null && subPath == null -> kPath.subPath
                    kPath.subPath != null && subPath != null -> kPath.subPath / subPath
                    kPath.subPath == null && subPath != null -> subPath
                    else -> null
                }
            )
        }

    }

    companion object {
        operator fun String.div(subPath: String): KPath = Factory.create(this) / subPath
    }
}
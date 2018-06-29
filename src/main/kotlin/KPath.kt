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

    operator fun div(subPathString: String): KPath = create(this, create(subPathString))

    operator fun div(builder: KNodeBuilder.() -> Unit): KNode {
        return KNodeBuilder(create(this, subPath)).apply(builder).build()
    }

    override fun equals(other: Any?): Boolean {
        val kpath = other as? KPath ?: return false
        if (this.subPath == null && kpath.subPath == null) return true
        return this.subPath == kpath.subPath
    }

    override fun hashCode(): Int {
        return (subPath?.hashCode() ?: 0)
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
                val middlePath = create(splittedPaths.drop(1).joinToString("/"))
                this.subPath = subPath?.div(middlePath) ?: middlePath
            }
        }

        fun build() = KPath(KSegment.Constant(this.string), this.subPath)
    }

    companion object {
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
}
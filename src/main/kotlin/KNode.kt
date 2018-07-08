class KNode(
    private val kpath: KPath,
    private val subNodes: Set<KNode> = emptySet()
) {
    constructor(path: String, subNodes: Set<KNode> = emptySet()) : this(KPath.Factory.create(path), subNodes)

    fun findKPathByTag(tag: String): KPath? {
        this.kpath.findKPathByTag(tag)?.let {
            return it
        }

        return when {
            subNodes.isNotEmpty() -> {
                for (node in subNodes) {
                    val found = node.findKPathByTag(tag)
                    if (found != null) {
                        return kpath / found
                    }
                }
                null
            }
            else -> null
        }
    }

    fun toPaths(): Set<KPath> {
        if (subNodes.isEmpty()) {
            return setOf(kpath)
        }
        return subNodes.flatMap {
            it.toPaths()
                .map { kpath / it }
        }.toSet()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KNode

        if (kpath != other.kpath) return false
        if (subNodes != other.subNodes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = kpath.hashCode()
        result = 31 * result + subNodes.hashCode()
        return result
    }

    override fun toString(): String {
        return "KNode(kpath=$kpath, subNodes=$subNodes)"
    }

    class Builder(
        private var kPath: KPath = KPath.Factory.create(""),
        private var subNodes: Set<KNode> = setOf()
    ) {
        data class KPathHolder(val kPath: KPath)

        operator fun String.unaryMinus() = KPath.Factory.create(this)
            .let { KPathHolder(it) }
            .also {
                subNodes = subNodes.plus(KNode(it.kPath))
            }

        operator fun KPathHolder.div(string: String): KPathHolder {
            return this / KPath.Factory.create(string)
        }

        operator fun KPathHolder.div(kPath: KPath): KPathHolder {
            subNodes = subNodes.map {
                if (this.kPath == it.kpath) {
                    KNode(it.kpath / kPath, it.subNodes)
                } else {
                    it
                }
            }.toSet()
            return KPathHolder(this.kPath / kPath)
        }

        operator fun KPathHolder.div(builder: Builder.() -> Unit) {
            subNodes = subNodes.map {
                if (this.kPath == it.kpath) {
                    Builder().apply(builder).apply {
                        kPath = this@div.kPath
                    }.build()
                } else {
                    it
                }
            }.toSet()
        }

        operator fun KPath.unaryMinus() {
            subNodes = subNodes.plus(KNode(this))
        }

        fun build(): KNode = KNode(kPath, subNodes)
    }
}
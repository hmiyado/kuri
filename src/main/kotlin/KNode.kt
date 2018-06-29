class KNode(
    internal val kpath: KPath,
    internal val subNodes: Set<KNode> = emptySet()
) {
    constructor(path: String, subNodes: Set<KNode> = emptySet()) : this(KPath.Factory.create(path), subNodes)

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
}
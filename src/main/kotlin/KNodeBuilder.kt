class KNodeBuilder(
    var kPath: KPath = KPath(""),
    var subPaths: Set<KNode> = emptySet()
) {

    operator fun String.unaryPlus() {
        subPaths += KNode(KPath(this))
    }

    fun build(): KNode = KNode(kPath, subPaths)
}

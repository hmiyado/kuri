class KNodeBuilder(
    var kPath: KPath = KPath.create(""),
    var subPaths: Set<KNode> = emptySet()
) {

    operator fun String.unaryPlus() {
        subPaths += KNode(KPath.create(this))
    }

    fun build(): KNode = KNode(kPath, subPaths)
}

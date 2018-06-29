class KNodeBuilder(
    var kPath: KPath = KPath.Factory.create(""),
    var subPaths: Set<KNode> = emptySet()
) {

    operator fun String.unaryPlus() {
        subPaths += KNode(KPath.Factory.create(this))
    }

    fun build(): KNode = KNode(kPath, subPaths)
}

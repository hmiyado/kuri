class KNodeBuilder(
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
        subNodes = subNodes.map {
            if (this.kPath == it.kpath) {
                KNode(it.kpath / string, it.subNodes)
            } else {
                it
            }
        }.toSet()
        return KPathHolder(kPath / string)
    }

//    operator fun KPathHolder.div(builder: KNodeBuilder.() -> Unit) {
//        subNodes = subNodes.map {
//            if (this.kPath == it.kpath) {
//                KNodeBuilder().apply(builder).build()
//            } else {
//                it
//            }
//        }.toSet()
//    }

    operator fun KPath.unaryMinus() {
        subNodes = subNodes.plus(KNode(this))
    }

    fun build(): KNode = KNode(kPath, subNodes)
}

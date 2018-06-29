import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object KPathSpec : Spek({
    describe("kpath") {
        it("渡された文字列が/区切りのパスだったら，subpathに分割する") {
            KPath("root/sub1/sub2/sub3") shouldBe KPath("root", KPath("sub1", KPath("sub2", KPath("sub3"))))
        }

        it("concatenates KPath by division") {
            KPath("root") / KPath("subPath1") / KPath("subPath2") / KPath("subPath3") shouldBe KPath(
                "root",
                KPath("subPath1", KPath("subPath2", KPath("subPath3")))
            )
        }

        it("concatenates string paths by division") {
            KPath("root") / KPath("subPath1") / KPath("subPath2") / KPath("subPath3") shouldBe KPath(
                "root",
                KPath("subPath1", KPath("subPath2", KPath("subPath3")))
            )
        }

        it("should be node when branches paths") {
            KPath("root") / {
                +"child1"
                +"child2"
            } shouldBe KNode("root", setOf(KNode("child1"), KNode("child2")))
        }

        it("subpath を連結できる") {
            val path = "root/sub1/sub2/sub3"
            KPath(path).joinSubPaths() shouldBe path
        }
    }
})
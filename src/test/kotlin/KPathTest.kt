import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object KPathSpec : Spek({
    describe("kpath") {
        it("String をスラッシュ区切りのパスにできる") {
            with(KPath) {
                "root" / "sub1" / "sub2" shouldBe create("root/sub1/sub2")
            }
        }

        it("渡された文字列が/区切りのパスだったら，subpathに分割する") {
            KPath.create("root/sub1/sub2/sub3") shouldBe KPath.create(
                "root",
                KPath.create("sub1", KPath.create("sub2", KPath.create("sub3")))
            )
        }

        it("concatenates KPath by division") {
            with(KPath) {
                create("root") / create("subPath1") / create("subPath2") / create("subPath3") shouldBe create(
                    "root",
                    create("subPath1", create("subPath2", create("subPath3")))
                )
            }
        }

        it("concatenates string paths by division") {
            with(KPath) {
                create("root") / "subPath1" / "subPath2" / "subPath3" shouldBe create(
                    "root",
                    create("subPath1", create("subPath2", create("subPath3")))
                )
            }
        }

        it("should be node when branches paths") {
            KPath.create("root") / {
                +"child1"
                +"child2"
            } shouldBe KNode("root", setOf(KNode("child1"), KNode("child2")))
        }

        it("subpath を連結できる") {
            val path = "root/sub1/sub2/sub3"
            KPath.create(path).toString() shouldBe path
        }
    }
})
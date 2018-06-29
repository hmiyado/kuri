import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object KNodeSpek : Spek({
    describe("KNode") {
        it("KPath のセットに展開できる") {
            val node = KPath.Factory.create("root") / {
                -"sub1"
                -"sub2" / "sub2-1"
//                -"sub3" / {
//                    -"sub3-1"
//                    -"sub3-2" / "sub3-2-1"
//                }
            }

            node.toPaths() shouldBe with(KPath) {
                setOf(
                    "root" / "sub1",
                    "root" / "sub2" / "sub2-1"//,
//                    "root" / "sub3" / "sub3-1",
//                    "root" / "sub3" / "sub3-2" / "sub3-2-1"
                )
            }
        }
    }
})
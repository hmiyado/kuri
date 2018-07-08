import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object KNodeSpek : Spek({
    describe("KNode") {
        it("KPath のセットに展開できる") {
            val actual = with(KPath) {
                "root" / {
                    -"sub1"
                    -"sub2" / "sub2-1"
                    -"sub3" / {
                        -"sub3-1"
                        -"sub3-2" / "sub3-2-1"
                    }
                }
            }

            val expected = with(KPath) {
                setOf(
                    "root" / "sub1",
                    "root" / "sub2" / "sub2-1",
                    "root" / "sub3" / "sub3-1",
                    "root" / "sub3" / "sub3-2" / "sub3-2-1"
                )
            }
            actual.toPaths() shouldBe expected
        }

        it("特定のタグのパスを検索できる") {
            val actual = with(KPath) {
                "root" / {
                    -"sub1"("tag1")
                    -"sub2" / "sub2-1"
                    -"sub3" / {
                        -"sub3-1"
                        -"sub3-2" / "sub3-2-1"("tag3-2-1")
                    }
                }
            }

            actual.findKPathByTag("tag1") shouldBe KPath.Factory.create("root/sub1")
            actual.findKPathByTag("tag2") shouldBe null
            actual.findKPathByTag("tag3-2-1") shouldBe KPath.Factory.create("root/sub3/sub3-2/sub3-2-1")
        }
    }
})
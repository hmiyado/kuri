import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object KSegmentSpek : Spek({
    describe("Constant Segment") {
        it("スラッシュ付きの文字列はエラーをだす") {
            shouldThrow<AssertionError> {
                KSegment.Constant("segmentWith/")
            }
        }
    }

    describe("Variable Segment") {
        it("不正なIDはエラーをだす") {
            shouldThrow<AssertionError> {
                KSegment.Variable<String>("{invalidId}")
            }
        }

        it("値を割り当てるとConstantになる") {
            KSegment.Variable<Int>("number").assign(3) shouldBe KSegment.Constant(3.toString())
        }
    }
})
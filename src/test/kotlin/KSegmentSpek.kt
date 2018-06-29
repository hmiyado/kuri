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
})
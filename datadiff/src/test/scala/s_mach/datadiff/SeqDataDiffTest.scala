/*
                    ,i::,
               :;;;;;;;
              ;:,,::;.
            1ft1;::;1tL
              t1;::;1,
               :;::;               _____       __  ___              __
          fCLff ;:: tfLLC         / ___/      /  |/  /____ _ _____ / /_
         CLft11 :,, i1tffLi       \__ \ ____ / /|_/ // __ `// ___// __ \
         1t1i   .;;   .1tf       ___/ //___// /  / // /_/ // /__ / / / /
       CLt1i    :,:    .1tfL.   /____/     /_/  /_/ \__,_/ \___//_/ /_/
       Lft1,:;:       , 1tfL:
       ;it1i ,,,:::;;;::1tti      s_mach.datadiff
         .t1i .,::;;; ;1tt        Copyright (c) 2014 S-Mach, Inc.
         Lft11ii;::;ii1tfL:       Author: lance.gatlin@gmail.com
          .L1 1tt1ttt,,Li
            ...1LLLL...
*/
package s_mach.datadiff

import org.scalatest.{Matchers, FlatSpec}

class SeqDataDiffTest extends FlatSpec with Matchers {

  val seq1 = Seq(1,2,3,4)
  val seq2 = Seq(2,7,4,5)

  "SeqDiff.diff" must "detect differences between the old and new value" in {
    val deltas = Vector(
      SeqPatch.Delta(
        _type = SeqPatch.Delete,
        original = SeqPatch.Chunk(0,Vector(1)),
        revised = SeqPatch.Chunk(0,Vector.empty)
      ),
      SeqPatch.Delta(
        _type = SeqPatch.Change,
        original = SeqPatch.Chunk(2,Vector(3)),
        revised = SeqPatch.Chunk(1,Vector(7))
      ),
      SeqPatch.Delta(
        _type = SeqPatch.Insert,
        original = SeqPatch.Chunk(4,Vector.empty),
        revised = SeqPatch.Chunk(3,Vector(5))
      )
    )

    seq1 -->? seq2 should equal(SeqPatch(deltas))
    seq1 -->? seq1 should equal(SeqPatch.noChange)
  }

  "SeqDiff.patch" must "apply changes to an old value to achieve new value" in {
    val d = seq1 -->? seq2
    seq1 |<-- d should equal(seq2)
  }

}

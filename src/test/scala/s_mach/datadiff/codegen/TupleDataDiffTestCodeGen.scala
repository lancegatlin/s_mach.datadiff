/*
                    ,i::,
               :;;;;;;;
              ;:,,::;.
            1ft1;::;1tL
              t1;::;1,
               :;::;               _____        __  ___              __
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
package s_mach.datadiff.codegen

object TupleDataDiffTestCodeGen {
  def gen(n: Int) = {
    val lcs = ('a' to 'z').map(_.toString).take(n)
    val ucs = ('A' to 'Z').map(_.toString).take(n)
    val allUcs = ucs.mkString(",")
    val allLcs = lcs.mkString(",")

s"""
  "Tuple${n}Diff.diff" must "detect differences between the old and new value" in {
    val tuple = (${(0 until n).map(i => "Random.nextInt()").mkString(",")})
    val modTuple = tuple.copy(_2 = Random.nextInt())
    tuple calcDiff modTuple should equal(Some((None,Some(modTuple._2)${if(n > 2 ) "," + (2 until n).map(i => "None").mkString(",") else ""})))
    tuple calcDiff tuple should equal(None)
  }

  "Simple${n}Diff.patch" must "apply changes to an old value to achieve new value" in {
    val tuple = (${(0 until n).map(i => "Random.nextInt()").mkString(",")})
    val modTuple = tuple.copy(_2 = Random.nextInt())
    val d = tuple calcDiff modTuple
    tuple applyPatch d should equal(modTuple)
  }
"""
  }
  
  def genToFile(path: String) : Unit = {
    val contents =
s"""package s_mach.datadiff

/* WARNING: Generated code. To modify see s_mach.datadiff.TupleDataDiffTestCodeGen */

import scala.util.Random
import org.scalatest.{Matchers, FlatSpec}

class TupleDataDiffTest extends FlatSpec with Matchers {
${(2 to 22).map(i => gen(i)).mkString("\n")}
}
"""
    
    import java.io._
    val out = new PrintWriter(new BufferedWriter(new FileWriter(path)))
    out.println(contents)
    out.close()
  }

}
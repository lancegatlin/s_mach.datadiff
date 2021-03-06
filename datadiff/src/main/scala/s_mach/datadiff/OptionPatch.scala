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

/**
 * A case class for a patch for an Option
 * @tparam A the value type
 * @tparam P the patch type for the value type
 */
sealed trait OptionPatch[+A,+P]

object OptionPatch {
  val noChange = NoChange
  case object NoChange extends OptionPatch[Nothing,Nothing]
  case object SetNone extends OptionPatch[Nothing,Nothing]
  case class SetValue[A](value: A) extends OptionPatch[A,Nothing]
  case class ApplyInnerPatch[P](patch: P) extends OptionPatch[Nothing,P]
}
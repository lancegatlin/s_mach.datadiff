package s_mach

import s_mach.datadiff.impl.{OptionDiff, SimpleDiff}

package object datadiff {
  implicit object Diff_Byte extends SimpleDiff[Byte]
  implicit object Diff_Short extends SimpleDiff[Short]
  implicit object Diff_Int extends SimpleDiff[Int]
  implicit object Diff_Long extends SimpleDiff[Long]
  implicit object Diff_Float extends SimpleDiff[Float]
  implicit object Diff_Double extends SimpleDiff[Double]
  implicit object Diff_String extends SimpleDiff[String]
  implicit def mkDiff_Option[A,Patch](implicit
    aDiff:Diff[A,Patch]
  ) = new OptionDiff[A,Patch]

  implicit class SMach_Datadiff_PimpEverything[A](val self: A) extends AnyVal {
    def diff[Patch](other: A)(implicit aDiff:Diff[A,Patch]) : Option[Patch] =
      aDiff.diff(self, other)
    def patch[Patch](
      optPatch: Option[Patch]
    )(implicit
      aDiff:Diff[A,Patch]
    ) : A =
      optPatch match {
        case Some(patch) => aDiff.applyPatch(self, patch)
        case None => self
      }
  }
}
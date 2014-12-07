package s_mach.datadiff

class PatchNotCompleteException extends Exception

/**
 * A type class for computing the differences between two instances of a type
 * @tparam A the type to compute differences on
 * @tparam P a type for a "patch" which represents the differences between any
 *           two instances of A
 */
trait Diff[A,P] {
  type Patch = P

  /**
   * Compute the difference between the original value and a new value. Result
   * is a "patch" that if applied to the original value results in the new
   * value.
   * @param oldValue the original value
   * @param newValue the new value
   * @return If oldValue and newValue are different, Some(patch) otherwise None
   *         if oldValue and newValue are the same
   */
  def diff(oldValue: A, newValue: A) : Option[Patch]

  /**
   * Apply a patch generated by a previous call to diff to a value
   * @param value the value to apply the patch to
   * @param patch the patch to apply
   * @return the new value with the patch applied
   */
  def applyPatch(value: A, patch: Patch) : A

  /**
   * Convert a value to a patch, such that if the patch was applied to any
   * other value, the result would be equal to the converted value
   * @param value the value to convert
   * @return a patch based on the value
   */
  def mkPatch(value: A) : Patch

  /**
   * Test if it is possible to convert a patch to a value
   * @param patch the patch to convert
   * @return TRUE if the patch can be converted to a value
   */
  def canMkValue(patch: Patch) : Boolean = false

  /**
   * Convert a patch to value
   * @param patch the patch to convert
   * @return the value based on the patch
   * @throws PatchNotCompleteException if the patch is not a complete value
   *         and cannot be converted to a value
   */
  def mkValue(patch: Patch) : A = throw new PatchNotCompleteException
}

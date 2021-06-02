package generic

package object impls {

  /** Syntax sugar for converting data to [[RefTree]] */
  implicit class RefTreeSyntax[A: ToRefTree](value: A) {
    def refTree = implicitly[ToRefTree[A]].refTree(value)
  }
}
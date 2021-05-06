package repls

/*
    Multiset is a Map of elements and their respective count.
    For example:
    {a,a,a,b,c,c} = Map('a'->3, 'b'->1, 'c'->2)
 */

case class MultiSet[T] (multiplicity: Map[T, Int]) {
    def *(that: MultiSet[T]): MultiSet[T] = this - (this - that)
    def +(that: MultiSet[T]): MultiSet[T] = MultiSet(this.toSeq ++ that.toSeq)
    def -(that: MultiSet[T]): MultiSet[T] = MultiSet(this.toSeq diff that.toSeq)
    def toSeq: Seq[T] = {
        var s : Seq[T] = Seq()
        multiplicity.keys.foreach{i=> for(_<-0 until multiplicity(i)) s = s:+i }
        s
    }

    // A toString has already been provided and relies on toSeq
    override def toString: String =
        "{" + toSeq.map(_.toString).sorted.mkString(",") + "}"

}
object MultiSet {
    def empty[T] : MultiSet[T] = MultiSet(Map[T,Int]())
    def apply[T](elements: Seq[T]): MultiSet[T] = {
        var map : Map[T,Int] = Map()
        for(i <- elements){
            var count: Int = 0
            if(map.contains(i)) count = map(i)
            else count = 0
            map = map + (i -> (count + 1))
        }
        MultiSet(map)
    }
}
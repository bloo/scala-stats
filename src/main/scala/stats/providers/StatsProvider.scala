package stats.providers

trait StatsProvider {
  def count[C](c: C)(implicit stat: String, num: Numeric[C])
  def value[V](v: V)(implicit stat: String, num: Numeric[V])
}

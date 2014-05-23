package stats.providers

trait StatsProvider {
  def value(c: Double)(implicit stat: String)
  def count(c: Double)(implicit stat: String)
}

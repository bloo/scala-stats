package stats.providers

import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging

class NoOpProvider extends StatsProvider with LazyLogging {
  def count(c: Double)(implicit stat: String) = logger info s"noop stats provider: $stat count: $c"
  def value(v: Double)(implicit stat: String) = logger info s"noop stats provider: $stat value: $v"
}

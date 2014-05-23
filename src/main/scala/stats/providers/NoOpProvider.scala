package stats.providers

import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging

class NoOpProvider extends StatsProvider with LazyLogging {
  def count[C](c: C)(implicit stat: String, num: Numeric[C]) =
    logger info s"noop stats provider: $stat count: $c"
  def value[V](v: V)(implicit stat: String, num: Numeric[V]) =
    logger info s"noop stats provider: $stat value: $v"
}

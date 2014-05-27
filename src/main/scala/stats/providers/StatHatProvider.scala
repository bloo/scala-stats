package stats.providers

import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging

class StatHatProvider(config: Config) extends StatsProvider with LazyLogging {

  val ezUrl = config getString "ezUrl"
  val ezKey = config getString "ezKey"
  val verbose = config getBoolean "verbose"

  def count[C](c: C)(implicit stat: String, num: Numeric[C]) = _post("count", c)
  def value[V](v: V)(implicit stat: String, num: Numeric[V]) = _post("value", v)

  import java.io.OutputStreamWriter
  import java.net.{ URL, URLEncoder }

  private def _post[X](valType: String, value: X)(implicit stat: String, num: Numeric[X]) = {

    val paramEz = URLEncoder.encode("ezkey", "UTF-8")
    val valEz = URLEncoder.encode(ezKey, "UTF-8")
    val paramStat = URLEncoder.encode("stat", "UTF-8")
    val valStat = URLEncoder.encode(stat, "UTF-8")
    val paramVal = URLEncoder.encode(valType, "UTF-8")
    val valVal = URLEncoder.encode(value.toString(), "UTF-8")
    //val valVal = URLEncoder.encode(num.toString(), "UTF-8")

    logger.info(s"value: $value")
    logger.info(s"num: $num")
    logger.info(s"vv: $valVal")

    val data = s"$paramEz=$valEz&$paramStat=$valStat&$paramVal=$valVal"
    
    val conn = new URL(ezUrl).openConnection()
    conn.setDoOutput(true)
    val wr = new OutputStreamWriter(conn.getOutputStream())
    wr.write(data)
    wr.flush()

    if (verbose) {
      logger.info(s"post to url: $ezUrl, data: $data")
      import java.io.{ BufferedReader, InputStreamReader }
      val rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))
      val strs = Stream.continually(rd.readLine()).takeWhile(_ != null)
      strs foreach { line => logger info line }
      rd.close()
    }
    wr.close()
  }

}

package stats.providers

import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging

class StatHatProvider(config: Config) extends StatsProvider with LazyLogging {

  private val ezUrl = config getString "stathat.ezUrl"
  private val ezKey = config getString "stathat.ezKey"
  private val verbose = config getBoolean "stathat.verbose"

  def count(c: Double)(implicit stat: String) = _post("count", c)
  def value(v: Double)(implicit stat: String) = _post("value", v)

  import java.io.OutputStreamWriter
  import java.net.{URL,URLEncoder}

  private def _post(valType: String, value: Double)(implicit stat: String) = {

    val paramEz = URLEncoder.encode("ezkey", "UTF-8")
    val valEz = URLEncoder.encode(ezKey, "UTF-8")
    val paramStat = URLEncoder.encode("stat", "UTF-8")
    val valStat = URLEncoder.encode(stat, "UTF-8")
    val paramVal = URLEncoder.encode(valType, "UTF-8")
    val valVal = URLEncoder.encode(value.toString(), "UTF-8")

    val data = s"$paramEz=$valEz&$paramStat=$valStat&$paramVal=$valVal"

    val conn = new URL(ezUrl).openConnection()
    conn.setDoOutput(true)
    val wr = new OutputStreamWriter(conn.getOutputStream())
    wr.write(data)
    wr.flush()

    if (verbose) {
      import java.io.{BufferedReader,InputStreamReader}
      val rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))
      val strs = Stream.continually(rd.readLine()).takeWhile(_ != null)
      strs foreach { line => logger info line }
      rd.close()
    }
    wr.close()
  }

}

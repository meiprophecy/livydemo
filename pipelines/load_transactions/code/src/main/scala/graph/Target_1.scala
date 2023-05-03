package graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._

object Target_1 {

  def apply(spark: SparkSession, in: DataFrame): Unit = {
    Config.fabricName match {
      case "anshuman2" =>
        in.write
          .format("parquet")
          .mode("append")
          .partitionBy("business_date", "import_ts")
          .save(
            "dbfs:/Prophecy/anshuman@simpledatalabs.com/fin_reporting/transactions/silver/"
          )
      case _ =>
        throw new Exception("No valid dataset present to read fabric")
    }
  }

}

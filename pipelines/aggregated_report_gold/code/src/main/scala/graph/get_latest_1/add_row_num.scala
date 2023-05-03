package graph.get_latest_1

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._
import udfs.UDFs._
import udfs._

object add_row_num {

  def apply(spark: SparkSession, in: DataFrame): DataFrame = {
    import org.apache.spark.sql.expressions.{Window, WindowSpec}
    val windowWithFrame: Option[WindowSpec] = Some(
      Window
        .partitionBy(col("tran_id"), col("business_date"))
        .orderBy(col("import_ts").desc)
    )
    if (windowWithFrame.isEmpty) in.withColumn("row_num", row_number().over())
    else in.withColumn("row_num",                         row_number().over(windowWithFrame.get))
  }

}

package com.sparkProject

import org.apache.spark.SparkConf
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Exercicebis {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAll(Map(
      "spark.scheduler.mode" -> "FIFO",
      "spark.speculation" -> "false",
      "spark.reducer.maxSizeInFlight" -> "48m",
      "spark.serializer" -> "org.apache.spark.serializer.KryoSerializer",
      "spark.kryoserializer.buffer.max" -> "1g",
      "spark.shuffle.file.buffer" -> "32k",
      "spark.default.parallelism" -> "12",
      "spark.sql.shuffle.partitions" -> "12"
    ))

    val spark = SparkSession
      .builder
      .config(conf)
      .appName("TP_SPARK")
      .getOrCreate()

    import spark.implicits._

    val df: DataFrame = spark
      .read
      .text("/Users/cochet/Documents/1_Work/TP_SPARK/DATAS/train.csv")

    val df2 = df
      .withColumn("replaced", regexp_replace($"value", "\"{2,}", " "))

    df2
      .select("replaced")
      .write
      .text("/Users/cochet/Documents/1_Work/TP_SPARK/DATAS/train_clean")

    val dfClean = spark
      .read
      .csv("/Users/cochet/Documents/1_Work/TP_SPARK/DATAS/train_clean")

    dfClean.show(50)

  }

}

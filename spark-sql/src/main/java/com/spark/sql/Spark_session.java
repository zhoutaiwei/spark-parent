package com.spark.sql;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Spark_session {
    public static void main(String[] args) {
        SparkSession ss = SparkSession.builder().appName("Spark_session").master("local[2]").getOrCreate();
        Dataset<Row> json = ss.read().json("file:///E:/hadoop_hdfs/spark-study/spark-parent/spark-sql/test.json");
        json.show();
        ss.stop();
    }
}

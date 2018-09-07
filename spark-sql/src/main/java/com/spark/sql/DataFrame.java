package com.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataFrame {
    public static void main(String[] args) {
        SparkSession dataframe = SparkSession.builder().appName("dataframe").master("local[2]").getOrCreate();
        Dataset<Row> json = dataframe.read().json("file:///E:/hadoop_hdfs/spark-study/spark-parent/spark-sql/test.json");
        json.show();
        json.select("name").show();
    }

}

package com.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class Spark_SQL_json {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Spark_SQL_json").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext context = new SQLContext(sc);
        Dataset<Row> json = context.read().format("json").load("file:///E:/hadoop_hdfs/spark-study/spark-parent/spark-sql/test.json");
        json.printSchema();
        json.show();

        sc.close();
    }
}

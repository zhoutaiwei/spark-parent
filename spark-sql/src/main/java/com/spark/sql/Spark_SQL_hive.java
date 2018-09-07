package com.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;

public class Spark_SQL_hive {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Spark_SQL_hive").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        HiveContext context = new HiveContext(sc);
      //  Dataset<Row> student = context.table("student");
        Dataset<Row> sql = context.sql("select * from student where id=1");
        sql.show();
        sc.close();
    }

}



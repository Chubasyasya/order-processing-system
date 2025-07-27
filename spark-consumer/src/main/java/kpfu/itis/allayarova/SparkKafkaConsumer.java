package kpfu.itis.allayarova;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkKafkaConsumer {
    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder()
                .appName("KafkaOrderAnalytics")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "kafka:9092")
                .option("subscribe", "axon-events")
                .option("startingOffsets", "latest")
                .load();

        Dataset<Row> json = df.selectExpr("CAST(value AS STRING)");

        json.writeStream()
                .outputMode("append")
                .format("console")
                .start()
                .awaitTermination();
    }
}

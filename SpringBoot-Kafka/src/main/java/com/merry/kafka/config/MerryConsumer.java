package com.merry.kafka.config;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/10/29 18:32
 * @Description: mm
 */
public class MerryConsumer {

    private static KafkaConsumer<String, String> consumer;
    private static Properties properties;
    //初始化
    static {
        properties = new Properties();
        //建立连接broker的地址
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        //kafka反序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //指定消费者组
        properties.put("group.id", "KafkaStudy");
    }

    //自动提交位移：由consume自动管理提交
    private static void generalConsumeMessageAutoCommit() {
        //配置
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);
        //指定topic
        consumer.subscribe(Collections.singleton("kafka-study-x"));
        try {
            while (true) {
                boolean flag = true;
                //拉取信息，超时时间100ms
                ConsumerRecords<String, String> records = consumer.poll(100);
                //遍历打印消息
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, value = %s",
                            record.topic(), record.partition(), record.key(), record.value()
                    ));
                    //消息发送完成
                    if (record.value().equals("done")) { flag = false; }
                }
                if (!flag) { break; }
            }
        } finally {
            consumer.close();
        }
    }

    //手动同步提交当前位移，根据需求提交，但容易发送阻塞，提交失败会进行重试直到抛出异常
    private static void generalConsumeMessageSyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));
        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(), record.key(), record.value()
                ));
                if (record.value().equals("done")) { flag = false; }
            }
            try {
                //手动同步提交
                consumer.commitSync();
            } catch (CommitFailedException ex) {
                System.out.println("commit failed error: " + ex.getMessage());
            }
            if (!flag) { break; }
        }
    }

    //手动异步提交当前位移，提交速度快，但失败不会记录
    private static void generalConsumeMessageAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));
        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(), record.key(), record.value()
                ));
                if (record.value().equals("done")) { flag = false; }
            }
            //手动异步提交
            consumer.commitAsync();
            if (!flag) { break; }
        }
    }

    //手动异步提交当前位移带回调
    private static void generalConsumeMessageAsyncCommitWithCallback() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));
        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(), record.key(), record.value()
                ));
                if (record.value().equals("done")) { flag = false; }
            }
            //使用java8函数式编程
            consumer.commitAsync((map, e) -> {
                if (e != null) {
                    System.out.println("commit failed for offsets: " + e.getMessage());
                }
            });
            if (!flag) { break; }
        }
    }

    //混合同步与异步提交位移
    @SuppressWarnings("all")
    private static void mixSyncAndAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));
        try {
            while (true) {
                //boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, " + "value = %s",
                            record.topic(), record.partition(),
                            record.key(), record.value()
                    ));
                    //if (record.value().equals("done")) { flag = false; }
                }
                //手动异步提交，保证性能
                consumer.commitAsync();
                //if (!flag) { break; }
            }
        } catch (Exception ex) {
            System.out.println("commit async error: " + ex.getMessage());
        } finally {
            try {
                //异步提交失败，再尝试手动同步提交
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        //自动提交位移
        generalConsumeMessageAutoCommit();
        //手动同步提交当前位移
        //generalConsumeMessageSyncCommit();
        //手动异步提交当前位移
        //generalConsumeMessageAsyncCommit();
        //手动异步提交当前位移带回调
        //generalConsumeMessageAsyncCommitWithCallback()
        //混合同步与异步提交位移
        //mixSyncAndAsyncCommit();
    }
}

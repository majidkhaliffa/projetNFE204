package com.example.projetnfe204;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class SpringKafkaApplication  implements CommandLineRunner {
    @Autowired
    private MessageProducer producer;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }
      //  ConfigurableApplicationContext context = SpringApplication.run(SpringKafkaApplication.class, args);
        
        //MessageProducer producer = context.getBean(MessageProducer.class);
       // MessageListener listener = context.getBean(MessageListener.class);
        /*
         * Sending a Hello World message to topic 'baeldung'. 
         * Must be recieved by both listeners with group foo
         * and bar with containerFactory fooKafkaListenerContainerFactory
         * and barKafkaListenerContainerFactory respectively.
         * It will also be recieved by the listener with
         * headersKafkaListenerContainerFactory as container factory
         */
        @Override
        public void run(String... strings) throws Exception {
            for (int j = 0; j < 2; j++) {
                final String uri = "https://api.jcdecaux.com/vls/v1/stations?apiKey=9eefa18a4c801a995561f0d3a5a518fd0d53f5a7";

                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(uri, String.class);

                System.out.println(result);
                //producer.sendMessage(result);
                // listener.latch.await(10, TimeUnit.SECONDS);
                /*
                 * Sending message to a topic with 5 partition,
                 * each message to a different partition. But as per
                 * listener configuration, only the messages from
                 * partition 0 and 3 will be consumed.
                 */
                for (int i = 0; i < 10; i++) {
                    producer.sendMessageToPartion(result, i);
                }
            }
        }


    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

       @Service
    class MessageProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;



        @Value(value = "${message.topic.name}")
        private String topicName;

        public void sendMessage(String message) {
            
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
            
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
                }
            });
        }

        public void sendMessageToPartion(String message, int partition) {
            kafkaTemplate.send(topicName, partition, null, message);
        }

    }


}

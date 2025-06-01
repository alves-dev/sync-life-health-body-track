package synclife.health.bodytrack.infrastructure.broker

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConnectionConfig {

    @Bean
    fun connectionNameStrategy(): ConnectionNameStrategy {
        return SimplePropertyValueConnectionNameStrategy("spring.application.name")
    }
}
package com.kokoo.schemaregistry.event

import com.kokoo.Member
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MemberProducer(
    private val kafkaTemplate: KafkaTemplate<String, Member>
) {
    fun produceMember(member: Member) {
        member.createdAt = LocalDateTime.now()

        kafkaTemplate.send("member-topic", member)
    }
}
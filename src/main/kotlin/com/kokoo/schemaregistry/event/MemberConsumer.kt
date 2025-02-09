package com.kokoo.schemaregistry.event

import com.kokoo.Member
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MemberConsumer {
    @KafkaListener(topics = ["member-topic"], groupId = "member-group")
    fun consumeMember(member: Member) {
        println("member: $member")
    }
}
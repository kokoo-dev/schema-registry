package com.kokoo.schemaregistry.event

import com.kokoo.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.apache.avro.AvroMissingFieldException
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.kafka.core.KafkaTemplate
import java.time.LocalDateTime

class MemberProducerTest : BehaviorSpec({

    val kafkaTemplate = mock<KafkaTemplate<String, Member>>()
    val memberProducer = MemberProducer(kafkaTemplate)

    beforeTest {
        MockitoAnnotations.openMocks(this)
    }

    given("a MemberProducer with a valid member") {
        val member = Member.newBuilder()
            .setAge(20)
            .setName("Gildong")
            .setCreatedAt(LocalDateTime.now())
            .build()

        `when`("produceMember is called") {
            memberProducer.produceMember(member)
            val captor = ArgumentCaptor.forClass(Member::class.java)
            verify(kafkaTemplate).send(eq("member-topic"), captor.capture())

            then("it should send member to Kafka successfully") {
                val capturedMember = captor.value
                capturedMember shouldBe member
            }
        }
    }

    given("a MemberProducer with KafkaTemplate failure") {
        val member = Member()

        `when`("produceMember is called") {
            val exception = AvroMissingFieldException("Kafka send failed", null)
            doThrow(exception).`when`(kafkaTemplate).send(eq("member-topic"), any())

            then("it should throw an AvroMissingFieldException") {
                shouldThrow<AvroMissingFieldException> {
                    memberProducer.produceMember(member)
                } shouldBe exception
            }
        }
    }
})
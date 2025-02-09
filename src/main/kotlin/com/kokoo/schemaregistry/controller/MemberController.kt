package com.kokoo.schemaregistry.controller

import com.kokoo.Member
import com.kokoo.schemaregistry.event.MemberProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberProducer: MemberProducer
) {
    @PostMapping
    fun createMember(@RequestBody member: Member) {
        memberProducer.produceMember(member)
    }
}
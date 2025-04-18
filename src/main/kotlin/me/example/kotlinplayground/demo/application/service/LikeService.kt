package me.example.kotlinplayground.demo.application.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    // 예: "like:postId" 키 아래에 사용자 ID를 저장
    fun like(postId: Long, userId: Long): Boolean {
        val key = "like:$postId" // java의 final
        val hasLiked = redisTemplate.opsForSet().isMember(key, userId.toString())

        return if (hasLiked == true) {
            // 이미 좋아요 눌렀음
            false
        } else {
            redisTemplate.opsForSet().add(key, userId.toString())
            true
        }
    }

    fun unlike(postId: Long, userId: Long): Boolean {
        val key = "like:$postId"
        return redisTemplate.opsForSet().remove(key, userId.toString())!! > 0
    }

    fun getLikeCount(postId: Long): Long {
        val key = "like:$postId"
        return redisTemplate.opsForSet().size(key) ?: 0
    }
}

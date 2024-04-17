package im.etap.forecast.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
internal abstract class AuditableEntity {
    /**
     * 생성일시
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime? = null
        private set

    /**
     * 수정일시
     */
    @LastModifiedDate
    @Column(insertable = false)
    var updatedAt: LocalDateTime? = null
        private set
}
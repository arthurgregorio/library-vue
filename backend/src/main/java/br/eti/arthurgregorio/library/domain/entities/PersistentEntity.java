package br.eti.arthurgregorio.library.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/01/2020
 */
@ToString
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"createdOn", "updatedOn"})
public abstract class PersistentEntity implements IPersistentEntity<Long> {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Getter
    @Setter
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    /**
     *
     */
    @PrePersist
    protected void beforeInsert() {
        this.createdOn = LocalDateTime.now();
    }

    /**
     *
     */
    @PreUpdate
    protected void beforeUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    /**
     * {@inheritDoc }
     *
     * @return
     */
    @Override
    public boolean isSaved() {
        return this.id != null && this.id != 0;
    }
}
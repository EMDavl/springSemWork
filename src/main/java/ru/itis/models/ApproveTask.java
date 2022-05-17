package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "approve_task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApproveTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    private User reviewer;
}

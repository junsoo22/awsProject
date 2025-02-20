package org.example.domain.posts;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;

@Getter
@NoArgsConstructor
@Entity          //테이블과 링크될 클래스
public class Posts {

    @Id     //해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)       //PK의 생성규칙
    private Long id;

    @Column(length=500, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder      //해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title=title;
        this.content=content;
    }
}

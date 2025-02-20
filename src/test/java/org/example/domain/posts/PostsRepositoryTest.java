package org.example.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest     //H2 데이터베이스를 자동으로 실행해줌
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach     //단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title="테스트 게시글";
        String content="테스트 본문";

        //테이블 posts에 insert/update 쿼리를 실행
        //id 값이 있다면 update가, 없다면 insert 쿼리가 실행
        postsRepository.save(Posts.builder().title(title)
                .content(content).author("kjs201105@naver.com").build());

        //when
        //테이블 posts에 있는 모든 데이터를 조회해오는 메서드
        List<Posts> postsList=postsRepository.findAll();

        //then
        Posts posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}

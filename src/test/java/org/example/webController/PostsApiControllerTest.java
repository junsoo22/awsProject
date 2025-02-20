package org.example.webController;


import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsRepository;
import org.example.webController.dto.PostsSaveRequestDto;
import org.example.webController.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//랜덤 port를 사용하여 래플리케이션 컨텍스트를 로드. 실제 서버 없이 APi 호출 가능
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  //HTTP 요청을 보내기 위한 의존성 주입.

    @Autowired
    private PostsRepository postsRepository;  //JPA를 활용해 DB에 접근할 수 있도록

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        //given  테스트용 게시글 데이터 생성
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder().title(title).content(content).author("author").build();
        String url="http://localhost:"+port+"/api/v1/posts";

        //when  post요청을 통해 게시글 등록
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url,requestDto,Long.class);

        //then  응답코드 및 데이터베이스 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List< Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception{
        //given
        Posts savedPosts=postsRepository.save(Posts.builder().title("title").content("content").author("author").build());
        Long updatedId=savedPosts.getId();
        String expectedTitle="title2";
        String expecteedContent="content2";

        PostsUpdateRequestDto requestDto=PostsUpdateRequestDto.builder().title(expectedTitle).content(expecteedContent).build();
        String url="http://localhost:"+port+"/api/v1/posts/"+updatedId;

        HttpEntity<PostsUpdateRequestDto> requestEntity=new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity=restTemplate.exchange(url, HttpMethod.PUT,requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts>all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expecteedContent);
    }
}

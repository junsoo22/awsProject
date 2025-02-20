package org.example.service.posts;


import lombok.RequiredArgsConstructor;
import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsRepository;
import org.example.webController.dto.PostsResponseDto;
import org.example.webController.dto.PostsUpdateRequestDto;
import org.example.webController.dto.PostsResponseDto;
import org.example.webController.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor    //final이 선언된 모든 필드를 인자값으로 하는 생성자를 이 어노테이션이 대신 생성해줌. 생성자로 Bean 객체를 받도록 함=@Autowired와 같은 효과
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }
}

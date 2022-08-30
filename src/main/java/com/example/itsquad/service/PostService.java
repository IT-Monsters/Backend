package com.example.itsquad.service;

import com.example.itsquad.controller.request.PostRequestDto;
import com.example.itsquad.controller.response.PostResponseDto;
import com.example.itsquad.domain.Folio;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Post;
import com.example.itsquad.repository.FolioRepository;
import com.example.itsquad.repository.PostRepository;
import com.example.itsquad.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FolioRepository folioRepository;

    @Transactional // 게시글 작성 // 기술스택 추가해야됨 !!
    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        postRepository.save(Post.builder()
            .member(member)
            .subject(postRequestDto.getSubject())
            .content(postRequestDto.getContent())
            .typeEnum(postRequestDto.getTypeEnum())
            .classEnum(postRequestDto.getClassEnum())
            .minPrice(postRequestDto.getMinPrice())
            .maxPrice(postRequestDto.getMaxPrice())
            .status(false)
            .build());

        // 빈 포트폴리오 생성
        folioRepository.save(Folio.builder()
            .title(member.getNickname()+"님의 포트폴리오입니다.")
            .member(member)
            .build());
    }

    @Transactional(readOnly = true) // 모든 게시글 조회 // 기술스택 추가해야됨 !!
    public List<PostResponseDto> readAllPost(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // 게시글 상세 조회 // 댓글조회, 기술스택 추가해야됨 !!
    public PostResponseDto readPost(Long postId){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        return new PostResponseDto(post);
    }

    @Transactional // 게시글 수정 // 기술스택 추가해야됨 !!
    public void updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        if(!member.getId().equals(post.getMember().getId())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        post.updatePost(postRequestDto.getSubject(), postRequestDto.getContent(),
            postRequestDto.getTypeEnum(), postRequestDto.getClassEnum(),
            postRequestDto.getMinPrice(), postRequestDto.getMaxPrice());
    }

    @Transactional // 게시글 삭제
    public void deletePost(Long postId, UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        if(!member.getId().equals(post.getMember().getId())){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        postRepository.deleteById(postId);
    }
}

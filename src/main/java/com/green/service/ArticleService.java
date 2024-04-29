package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

// @Service가 있어야 service 로 인식함
// ArticleApiController에서 ArticleService를 @Autowired 하기 위해
@Slf4j
@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	// article 목록 조회
	public List<Article> index() {
		// db 저장하기 전 작업할 코딩 넣는다
		// JPA 함수: findAll() -> 이미 만들어져 있기 때문에 따로 만들어서 쓸 필요가 없음
		return articleRepository.findAll();
	}
	
	// article id로 조회
	public Article show(Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		
		return article;
	}

	public Article create(ArticleForm dto) {
		
		// 입력 data dto : {"id":2", title":"새글", "content":"새글 내용"}
		Article article = dto.toEntity();
		
		// create 는 생성 요청이고 번호는 자동증가이므로 번호가 필요없다
		// 그래서 id 가 존재하면 안된다
		if(article.getId() != null)
			return null;
		
		Article saved = articleRepository.save(article);
		return saved;
	}

	public Article update(Long id, ArticleForm dto) {
		// 1. DTO를 저장하기 위한 Entity 로 변환
		Article article = dto.toEntity();
		log.info("id: {}, article:{}",id,article.toString()); // {}가 parameter
		
		// 2. target(기존글) 을 id 로 조회하기
		Article target = articleRepository.findById(id).orElse(null);
		
		// 3. 잘못된 요청을 처리
		if(target == null || id !=article.getId()) {
			log.info("id: {}, article:{}",id,article.toString());
			return null; // 조회한 자료가 없거나 id 가 틀리면
		}
		
		// 4. 업데이트 및 정상응답(ok)을 수행해야 한다
		target.patch(article);
		Article updated = articleRepository.save(target);
		
		return updated;
	}
}

package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	// article 목록 조회
	public List<Article> index() {
		// db 저장하기 전 작업할 코딩 넣는다
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
}

package com.green.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.green.entity.Article;

public interface ArticleRepository 
  extends CrudRepository<Article, Long>{
	// CrudRepository 안에 saved 가 만들어져 있어서 따라 안만들어도 되는거임
	// 다만 타입이 맞지 않는 경우가 생길 수 있음

	   // ArticleController 83 line 형변환 해결방법
	   // 상속관계를 이용하여 List 를 Iterable 로 UpCasting 하여 형변하지 않음
	   // Iterable(I) <- Collection(C) <- List(I) <- ArrayList(C)
	   // 아래 내용 추가
	
	   @Override
	   ArrayList<Article> findAll();
}

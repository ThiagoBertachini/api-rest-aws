package com.bertachiniprojetos.unitTests.mappers.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bertachiniprojetos.data.Vo.V1.BookVO;
import com.bertachiniprojetos.model.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setPrice(150.0);
        book.setLaunchDate(new Date());
        book.setId(number.longValue());
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setPrice(150.0);
        book.setKey(number.longValue());
        book.setLaunchDate(new Date());
        return book;
    }
}

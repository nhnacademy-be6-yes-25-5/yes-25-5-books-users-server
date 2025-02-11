package com.yes255.yes255booksusersserver.persistance.domain.index;

import com.yes255.yes255booksusersserver.persistance.domain.Book;
import com.yes255.yes255booksusersserver.persistance.domain.Review;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "yes255_book", createIndex = true)
@Setting(settingPath = "/" +
        "elasticsearch/settings/nori_setting.json")
public class BookIndex {

    @Id
    @Field(name = "book_id", type = FieldType.Keyword)
    private String bookId;

    @Field(name = "book_isbn", type = FieldType.Keyword)
    private String bookIsbn;

    @Field(name = "book_name", type = FieldType.Text, analyzer = "korean")
    private String bookName;

    @Field(name = "book_description", type = FieldType.Text, analyzer = "synonym_analyzer")
    private String bookDescription;

    @Field(name = "book_publisher", type = FieldType.Text)
    private String bookPublisher;

    @Field(name = "book_price", type = FieldType.Double)
    private BigDecimal bookPrice;

    @Field(name = "book_selling_price", type = FieldType.Double)
    private BigDecimal bookSellingPrice;

    @Field(name = "book_image", type = FieldType.Text)
    private String bookImage;

    @Field(name = "quantity", type = FieldType.Integer)
    private Integer quantity;

    @Field(name = "review_count", type = FieldType.Integer)
    private Integer reviewCount;

    @Field(name = "hits_count", type = FieldType.Integer)
    private Integer hitsCount;

    @Field(name = "search_count", type = FieldType.Integer)
    private Integer searchCount;

    @Field(name = "book_is_packable", type = FieldType.Boolean)
    private boolean bookIsPackable;

    @Field(name = "book_is_deleted", type = FieldType.Boolean)
    private boolean bookIsDeleted;

    @Field(name = "grade", type = FieldType.Double)
    private Double grade;

    @Field(name = "authors", type = FieldType.Text, analyzer = "korean")
    private List<String> authors;

    @Field(name = "tags", type = FieldType.Text, analyzer = "korean")
    private List<String> tags;

    @Field(name = "categories", type = FieldType.Text, analyzer = "korean")
    private List<String> categories;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date publishDate;


    public static BookIndex updateAuthorsAndTagsAndCategory(BookIndex book, List<AuthorIndex> authors, List<TagIndex> tags, List<CategoryIndex> categories) {
        return BookIndex.builder()
                .bookId(book.getBookId())
                .bookIsbn(book.getBookIsbn())
                .bookName(book.getBookName())
                .bookDescription(book.getBookDescription())
                .bookPublisher(book.getBookPublisher())
                .bookPrice(book.getBookPrice())
                .bookSellingPrice(book.getBookSellingPrice())
                .bookImage(book.getBookImage())
                .quantity(book.getQuantity())
                .reviewCount(book.getReviewCount())
                .hitsCount(book.getHitsCount())
                .searchCount(book.getSearchCount())
                .bookIsPackable(book.isBookIsPackable())
                .bookIsDeleted(book.isBookIsDeleted())
                .grade(book.getGrade())
                .authors(authors.stream().map(AuthorIndex::getAuthorName).toList())
                .tags(tags.stream().map(TagIndex::getTagName).toList())
                .categories(categories.stream().map(CategoryIndex::getCategoryName).toList())
                .publishDate(book.getPublishDate())
                .build();
    }

    public static BookIndex fromBook(Book book) {
        return BookIndex.builder()
                .bookId(book.getBookId().toString())
                .bookIsbn(book.getBookIsbn())
                .bookName(book.getBookName())
                .bookDescription(book.getBookDescription())
                .bookPublisher(book.getBookPublisher())
                .bookPrice(book.getBookPrice())
                .bookSellingPrice(book.getBookSellingPrice())
                .bookImage(book.getBookImage())
                .quantity(book.getQuantity())
                .reviewCount(book.getReviews().size())
                .hitsCount(book.getHitsCount())
                .searchCount(book.getSearchCount())
                .bookIsPackable(book.isBookIsPackable())
                .bookIsDeleted(book.isBookIsDeleted())
                .grade(book.getReviews().stream().mapToDouble(Review::getRating).average().orElse(0))
                .publishDate(book.getBookPublishDate())
                .build();
    }
}

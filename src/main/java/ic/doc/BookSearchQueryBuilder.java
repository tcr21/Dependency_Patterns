package ic.doc;

public class BookSearchQueryBuilder {

  private String firstname = null;
  private String surname = null;
  private String title = null;
  private Integer publishedAfter = null;
  private Integer publishedBefore = null;

  public BookSearchQueryBuilder withFirstname(String firstname){
    this.firstname = firstname;
    return this;
  }

  public BookSearchQueryBuilder withSurname(String surname){
    this.surname = surname;
    return this;
  }

  public BookSearchQueryBuilder withTitleContaining(String title){
    this.title = title;
    return this;
  }

  public BookSearchQueryBuilder publishedAfter(Integer publishedAfter){
    this.publishedAfter = publishedAfter;
    return this;
  }

  public BookSearchQueryBuilder publishedBefore(Integer publishedBefore){
    this.publishedBefore = publishedBefore;
    return this;
  }

  public BookSearchQuery createBookSearchQuery(){
    return new BookSearchQuery(firstname, surname, title, publishedAfter, publishedBefore);
  }

}
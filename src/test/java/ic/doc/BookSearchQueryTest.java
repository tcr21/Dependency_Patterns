package ic.doc;

import ic.doc.catalogues.BritishLibraryCatalogue;
import ic.doc.catalogues.Catalogue;
import java.util.Arrays;
import java.util.List;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BookSearchQueryTest {

  private static final List<Book> BOOKS = Arrays.asList(new Book("A Christian Carol", "Charles Dickens", 1766));

  // Use mock object because want to test query without depending on real catalogue
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  Catalogue catalogue = context.mock(Catalogue.class);

  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorSurname() {

    context.checking(new Expectations(){{
      exactly(1).of(catalogue).searchFor("LASTNAME='dickens' "); will(returnValue(BOOKS));
    }});

    List<Book> books = new BookSearchQueryBuilder().withSurname("dickens").createBookSearchQuery().execute(catalogue);

    assertThat(books, is(BOOKS));
  }

  // Replicate above changes to below tests to use Mock objects

  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorFirstname() {

    List<Book> books = new BookSearchQueryBuilder().withFirstname("Jane").createBookSearchQuery().execute(BritishLibraryCatalogue.getInstance());

    assertThat(books.size(), is(2));
    assertTrue(books.get(0).matchesAuthor("Austen"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueByTitle() {

    List<Book> books = new BookSearchQueryBuilder().withTitleContaining("Two Cities").createBookSearchQuery().execute(BritishLibraryCatalogue.getInstance());

    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("dickens"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueBeforeGivenPublicationYear() {

    List<Book> books = new BookSearchQueryBuilder().publishedBefore(1700).createBookSearchQuery().execute(BritishLibraryCatalogue.getInstance());

    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("Shakespeare"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueAfterGivenPublicationYear() {

    List<Book> books = new BookSearchQueryBuilder().publishedAfter(1950).createBookSearchQuery().execute(BritishLibraryCatalogue.getInstance());

    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("Golding"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueWithCombinationOfParameters() {

    List<Book> books = new BookSearchQueryBuilder().withSurname("dickens").publishedBefore(1840).createBookSearchQuery().execute(BritishLibraryCatalogue.getInstance());

    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("charles dickens"));
  }

}
